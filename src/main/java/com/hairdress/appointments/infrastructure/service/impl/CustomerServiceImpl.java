package com.hairdress.appointments.infrastructure.service.impl;

import com.hairdress.appointments.infrastructure.bbdd.models.Customer;
import com.hairdress.appointments.infrastructure.bbdd.repositories.CustomerRepository;
import com.hairdress.appointments.infrastructure.error.exception.AuthorizationException;
import com.hairdress.appointments.infrastructure.error.exception.BadRequestException;
import com.hairdress.appointments.infrastructure.error.exception.GenericException;
import com.hairdress.appointments.infrastructure.error.exception.ModelNotFoundException;
import com.hairdress.appointments.infrastructure.error.exception.UserAlreadyExistsException;
import com.hairdress.appointments.infrastructure.rest.spring.controller.mapper.CustomerMapper;
import com.hairdress.appointments.infrastructure.security.SecurePasswordStorage;
import com.hairdress.appointments.infrastructure.service.CustomerService;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        Optional<Customer> opt = repository.findById(id);

        if (opt.isEmpty()) {
            log.error("No se pudo encontrar en la BD el cliente con id: {}", id);
            throw new ModelNotFoundException("No se pudo encontrar el cliente con id: " + id);
        }

        return opt.get();
    }

    @Override
    public Customer findByPhone(String phone) {
        Optional<Customer> opt = repository.findByPhone(phone);

        if (opt.isEmpty()) {
            log.error("No se pudo encontrar en la BD el cliente con teléfono: {}", phone);
            throw new ModelNotFoundException("No se pudo encontrar el cliente con el teléfono: " + phone);
        }

        return opt.get();
    }

    @Override
    public boolean isFirstConnection(String email) {
        Optional<Customer> opt = repository.findByEmail(email);

        if (opt.isEmpty()) {
            log.error("No existe un cliente con correo {}", email);
            throw new AuthorizationException("El email introducido no es correcto");
        }

        Customer customer = opt.get();

        return customer.getRegistered() && customer.getFirstConnection();
    }

    @Override
    @Transactional
    public Customer signUp(Customer customerToSignUp) {

        checkIfCustomerExists(customerToSignUp);

        return repository.save(customerToSignUp);
    }

    private void checkIfCustomerExists(Customer customerToSignUp) {
        Optional<Customer> opt = repository.findByPhone(customerToSignUp.getPhone());

        if (opt.isPresent()) {
            log.error("El cliente con teléfono {} ya existe", customerToSignUp.getPhone());
            throw new UserAlreadyExistsException("El teléfono proporcionado ya está en uso");
        }

        if (customerToSignUp.getEmail() != null) {
            Optional<Customer> optEmail = repository.findByEmail(customerToSignUp.getEmail());

            if (optEmail.isPresent()) {
                log.error("El cliente con email {} ya existe", customerToSignUp.getEmail());
                throw new UserAlreadyExistsException("El email proporcionado ya está en uso");
            }
        }
    }

    @Override
    @Transactional
    public Customer registerEmail(String phone, String email) {
        Optional<Customer> opt = repository.findByPhone(phone);

        if (opt.isEmpty()) {
            log.error("No existe un cliente sin registrar con teléfono {}", phone);
            throw new ModelNotFoundException("No existe un cliente sin registrar con el teléfono: " + phone);
        }

        Customer customer = opt.get();
        if (customer.getEmail() != null) {
            log.error("El cliente con teléfono {} ya tiene un correo asignado: {}",
                phone, customer.getEmail());
            throw new BadRequestException("El cliente que busca ya tiene un correo asignado");
        }

        customer.setEmail(email);
        customer.setRegistered(true);
        customer.setFirstConnection(true);

        return repository.save(customer);
    }

    @Override
    @Transactional
    public void firstConnection(String email, String password) {
        Optional<Customer> opt = repository.findByEmail(email);

        if (opt.isEmpty()) {
            log.error("No existe un cliente con correo {}", email);
            throw new AuthorizationException("El email introducido no es correcto");
        }

        Customer customer = opt.get();

        if (customer.getRegistered() && customer.getFirstConnection()) {
            // Creamos un salt nuevo y ciframos la nueva contraseña, y guardamos el cliente
            generateNewPassword(password, customer);

            customer.setFirstConnection(false);

            repository.save(customer);
        } else {
            if (!customer.getRegistered()) {
                log.error("El cliente con correo {} no esta dado de alta", customer.getEmail());
                throw new BadRequestException("El correo introducido no está dado de alta");
            } else if (!customer.getFirstConnection()) {
                log.error("El cliente con correo {} ya ha realizado su primera conexión",
                    customer.getEmail());
                throw new BadRequestException("El correo introducido ya ha realizado su primera conexión");
            }
        }
    }

    private void generateNewPassword(String password, Customer customer) {
        try {
            String newSalt = SecurePasswordStorage.getNewSalt();
            String newEncryptedPassword = SecurePasswordStorage.getEncryptedPassword(
                password, newSalt);

            customer.setSalt(newSalt);
            customer.setPassword(newEncryptedPassword);
        } catch (Exception e) {
            log.error("Se ha producido un error al generar la nueva contraseña del cliente", e);
            throw new GenericException("Se ha producido un error inesperado al cambiar la contraseña",
                e.getCause());
        }
    }

    @Override
    @Transactional
    public Customer update(Long id, Customer customerToUpdate) {

        Optional<Customer> opt = repository.findById(id);

        if (opt.isEmpty()) {
            log.error("No se pudo encontrar en la BD el cliente con id: {}", id);
            throw new ModelNotFoundException("No se pudo encontrar el cliente con id: " + id);
        }

        var customerToSave = mapper.updateCustomerData(customerToUpdate, opt.get());

        return repository.save(customerToSave);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Customer> opt = repository.findById(id);

        if (opt.isEmpty()) {
            log.error("No se pudo encontrar en la BD el cliente con id: {}", id);
            throw new ModelNotFoundException("No se pudo encontrar el cliente con id: " + id);
        }

        repository.delete(opt.get());
    }

    @Override
    @Transactional
    public void changePassword(String email, String oldPassword, String newPassword) {
        Optional<Customer> opt = repository.findByEmailAndRegisteredTrueAndFirstConnectionFalse(email);

        if (opt.isEmpty()) {
            log.error("No existe un cliente con correo {}", email);
            throw new AuthorizationException("El email introducido no es correcto");
        }

        Customer customer = opt.get();

        String oldEncryptedPassword;

        // Obtenemos la contraseña antigua cifrada
        try {
            oldEncryptedPassword = SecurePasswordStorage.getEncryptedPassword(
                oldPassword, customer.getSalt());
        } catch (Exception e) {
            log.error("Se ha producido un error al comprobrar la contraseña antigua del cliente", e);
            throw new GenericException("Se ha producido un error inesperado al cambiar la contraseña",
                e.getCause());
        }

        // Si la contraseña que ha introducido el usuario no es la misma, devolvemos una excepcion
        if (!oldEncryptedPassword.equals(customer.getPassword())) {
            log.error("La contraseña introducida no coincide con la contraseña del cliente");
            throw new AuthorizationException("La contraseña actual no corresponde con la contraseña"
                + " del cliente");
        }

        // Creamos un salt nuevo y ciframos la nueva contraseña, y guardamos el cliente
        generateNewPassword(newPassword, customer);

        repository.save(customer);
    }

    @Override
    public void login(String email, String password) {
        Optional<Customer> opt = repository.findByEmailAndRegisteredTrueAndFirstConnectionFalse(email);

        if (opt.isEmpty()) {
            log.error("No existe un cliente con correo {}", email);
            throw new AuthorizationException("El email introducido no es correcto");
        }

        Customer customer = opt.get();

        String encryptedPassword;

        try {
            encryptedPassword = SecurePasswordStorage.getEncryptedPassword(
                password, customer.getSalt());
        } catch (Exception e) {
            log.error("Se ha producido un error al comprobrar la contraseña del cliente", e);
            throw new GenericException("Se ha producido un error inesperado al autenticar al cliente",
                e.getCause());
        }

        if (!encryptedPassword.equals(customer.getPassword())) {
            throw new AuthorizationException("El usuario o la contraseña proporcionados no son correctos");
        }
    }
}
