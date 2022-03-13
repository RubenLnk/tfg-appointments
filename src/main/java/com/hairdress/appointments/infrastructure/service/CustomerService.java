package com.hairdress.appointments.infrastructure.service;

import com.hairdress.appointments.infrastructure.bbdd.models.Customer;
import java.util.List;

public interface CustomerService {

    List<Customer> findAll();

    Customer findById(Long id);

    Customer findByPhone(String phone);

    boolean isFirstConnection(String email);

    Customer signUp(Customer customerToSignUp);

    Customer registerEmail(String phone, String email);

    void firstConnection(String email, String password);

    Customer update(Long id, Customer customerToUpdate);

    void delete(Long id);

    void changePassword(String email, String oldPassword, String newPassword);

    void login(String email, String password);
}
