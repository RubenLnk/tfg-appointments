package com.hairdress.appointments.infrastructure.bbdd.repositories;

import com.hairdress.appointments.infrastructure.bbdd.models.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Optional<Customer> findByEmail(String email);

  Optional<Customer> findByEmailAndRegisteredTrueAndFirstConnectionTrue(String email);

  Optional<Customer> findByEmailAndRegisteredTrueAndFirstConnectionFalse(String email);

  Optional<Customer> findByPhone(String phone);

}
