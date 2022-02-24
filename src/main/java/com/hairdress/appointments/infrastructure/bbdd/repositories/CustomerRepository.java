package com.hairdress.appointments.infrastructure.bbdd.repositories;

import com.hairdress.appointments.infrastructure.bbdd.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
