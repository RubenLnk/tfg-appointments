package com.hairdress.appointments.infraestructure.service

import com.hairdress.appointments.infrastructure.bbdd.models.Customer
import com.hairdress.appointments.infrastructure.bbdd.repositories.CustomerRepository
import com.hairdress.appointments.infrastructure.error.exception.AuthorizationException
import com.hairdress.appointments.infrastructure.error.exception.BadRequestException
import com.hairdress.appointments.infrastructure.error.exception.ModelNotFoundException
import com.hairdress.appointments.infrastructure.error.exception.UserAlreadyExistsException
import com.hairdress.appointments.infrastructure.rest.spring.controller.mapper.CustomerMapper
import com.hairdress.appointments.infrastructure.service.impl.CustomerServiceImpl
import spock.lang.Specification

class CustomerServiceTest extends Specification {

    def repository = Mock(CustomerRepository)
    def mapper = Mock(CustomerMapper)
    def service = new CustomerServiceImpl(repository, mapper)
    def customer = Mock(Customer)

    def "findAll returns no value"() {
        given:
        repository.findAll() >> []

        when:
        def result = service.findAll()

        then:
        result.isEmpty()
    }

    def "findAll return one value"() {
        given:
        repository.findAll() >> [new Customer()]

        when:
        def result = service.findAll()

        then:
        result.size() == 1
    }

    def "findAll return multiple value"() {
        given:
        repository.findAll() >> [new Customer(), new Customer(), new Customer()]

        when:
        def result = service.findAll()

        then:
        result.size() == 3
    }

    def "findById does not find customer"() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.findById(1L)

        then:
        thrown(ModelNotFoundException)
    }

    def "findById finds a customer"() {
        given:
        repository.findById(_ as Long) >> Optional.of(customer)

        when:
        def result = service.findById(1L)

        then:
        result != null
    }

    def "findByPhone does not find customer"() {
        given:
        repository.findByPhone(_ as String) >> Optional.empty()

        when:
        service.findByPhone("12345678")

        then:
        thrown(ModelNotFoundException)
    }

    def "findByPhone finds a customer"() {
        given:
        repository.findByPhone(_ as String) >> Optional.of(customer)

        when:
        def result = service.findByPhone("12345678")

        then:
        result != null
    }

    def "isFirstConnection does not find customer"() {
        given:
        repository.findByEmail(_ as String) >> Optional.empty()

        when:
        service.isFirstConnection("TEST")

        then:
        thrown(AuthorizationException)
    }

    def "isFirstConnection finds a customer, but its not registered"() {
        given:
        repository.findByEmail(_ as String) >> Optional.of(customer)
        customer.getRegistered() >> false

        when:
        def result = service.isFirstConnection("TEST")

        then:
        !result
    }

    def "isFirstConnection finds a customer, but its not his first connection"() {
        given:
        repository.findByEmail(_ as String) >> Optional.of(customer)
        customer.getRegistered() >> true
        customer.getFirstConnection() >> false

        when:
        def result = service.isFirstConnection("TEST")

        then:
        !result
    }

    def "isFirstConnection success"() {
        given:
        repository.findByEmail(_ as String) >> Optional.of(customer)
        customer.getRegistered() >> true
        customer.getFirstConnection() >> true

        when:
        def result = service.isFirstConnection("TEST")

        then:
        result
    }

    def "signUp fails with existing phone"() {
        given:
        customer.getPhone() >> "12345678"
        repository.findByPhone(_ as String) >> Optional.of(customer)

        when:
        service.signUp(customer)

        then:
        thrown(UserAlreadyExistsException)
    }

    def "signUp fails with existing email"() {
        given:
        customer.getPhone() >> "12345678"
        customer.getEmail() >> "email@test.com"
        repository.findByPhone(_ as String) >> Optional.empty()
        repository.findByEmail(_ as String) >> Optional.of(customer)

        when:
        service.signUp(customer)

        then:
        thrown(UserAlreadyExistsException)
    }

    def "signUp success"() {
        given:
        customer.getPhone() >> "12345678"
        customer.getEmail() >> "email@test.com"
        repository.findByPhone(_ as String) >> Optional.empty()
        repository.findByEmail(_ as String) >> Optional.empty()

        when:
        def result = service.signUp(customer)

        then:
        result != null
        1 * repository.save(_ as Customer) >> customer
    }

    def "registerEmail does not find customer"() {
        given:
        repository.findByPhone(_ as String) >> Optional.empty()

        when:
        service.registerEmail("phone", "email")

        then:
        thrown(ModelNotFoundException)
    }

    def "registerEmail - customer already has an email"() {
        given:
        repository.findByPhone(_ as String) >> Optional.of(customer)
        customer.getEmail() >> "email"

        when:
        service.registerEmail("phone", "email")

        then:
        thrown(BadRequestException)
    }

    def "registerEmail successful"() {
        given:
        repository.findByPhone(_ as String) >> Optional.of(customer)
        customer.getEmail() >> null

        when:
        service.registerEmail("phone", "email")

        then:
        1 * repository.save(_ as Customer) >> customer
    }

    def "firstConnection does not find email"() {
        given:
        repository.findByEmail(_ as String) >> Optional.empty()

        when:
        service.firstConnection("email", "password")

        then:
        thrown(AuthorizationException)
    }

    def "firstConnection - customer is not registered"() {
        given:
        repository.findByEmail(_ as String) >> Optional.of(customer)
        customer.getRegistered() >> false
        customer.getEmail() >> "email@test.com"

        when:
        service.firstConnection("email", "password")

        then:
        thrown(BadRequestException)
    }

    def "firstConnection - it is not the first connection of customer"() {
        given:
        repository.findByEmail(_ as String) >> Optional.of(customer)
        customer.getRegistered() >> true
        customer.getFirstConnection() >> false
        customer.getEmail() >> "email@test.com"

        when:
        service.firstConnection("email", "password")

        then:
        thrown(BadRequestException)
    }

    def "firstConnection success"() {
        given:
        def customer = new Customer()
        customer.setRegistered(true)
        customer.setFirstConnection(true)
        repository.findByEmail(_ as String) >> Optional.of(customer)
        repository.save(_ as Customer) >> customer

        when:
        service.firstConnection("email", "password")

        then:
        with(customer) {
            it.getSalt() != null
            it.getPassword() != null
            !it.getFirstConnection()
        }
    }

    def "update does not find customer"() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.update(1L, new Customer())

        then:
        thrown(ModelNotFoundException)
    }

    def "update successful"() {
        given:
        repository.findById(_ as Long) >> Optional.of(customer)
        mapper.updateCustomerData(_ as Customer, _ as Customer) >> customer

        when:
        service.update(1L, customer)

        then:
        1 * repository.save(_ as Customer) >> customer
    }

    def "delete does not find customer"() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.delete(1L)

        then:
        thrown(ModelNotFoundException)
    }

    def "delete successful"() {
        given:
        repository.findById(_ as Long) >> Optional.of(customer)

        when:
        service.delete(1L)

        then:
        1 * repository.delete(_ as Customer)
    }

    def "changePassword does not find customer"() {
        given:
        repository.findByEmailAndRegisteredTrueAndFirstConnectionFalse(_ as String) >>
                Optional.empty()

        when:
        service.changePassword("email", "oldPass", "newPass")

        then:
        thrown(AuthorizationException)
    }

    def "changePassword - old password is not correct"() {
        given:
        repository.findByEmailAndRegisteredTrueAndFirstConnectionFalse(_ as String) >>
                Optional.of(customer)
        customer.getSalt() >> "/UTy3bOasug="
        customer.getPassword() >> "mzpI9aLCo2vYzGo/PlTAUjovdO4="

        when:
        service.changePassword("email", "oldPass", "newPass")

        then:
        thrown(AuthorizationException)
    }

    def "changePassword success"() {
        given:
        repository.findByEmailAndRegisteredTrueAndFirstConnectionFalse(_ as String) >>
                Optional.of(customer)
        customer.getSalt() >> "/UTy3bOasug="
        customer.getPassword() >> "mzpI9aLCo2vYzGo/PlTAUjovdO4="

        when:
        service.changePassword("email", "ruben", "newPass")

        then:
        1 * repository.save(_ as Customer)
    }

    def "login does not find customer"() {
        given:
        repository.findByEmailAndRegisteredTrueAndFirstConnectionFalse(_ as String) >>
                Optional.empty()

        when:
        service.login("email", "password")

        then:
        thrown(AuthorizationException)
    }

    def "login - password is not correct"() {
        given:
        repository.findByEmailAndRegisteredTrueAndFirstConnectionFalse(_ as String) >>
                Optional.of(customer)
        customer.getSalt() >> "/UTy3bOasug="
        customer.getPassword() >> "mzpI9aLCo2vYzGo/PlTAUjovdO4="

        when:
        service.login("email", "incorrect")

        then:
        thrown(AuthorizationException)
    }

    def "login success"() {
        given:
        repository.findByEmailAndRegisteredTrueAndFirstConnectionFalse(_ as String) >>
                Optional.of(customer)
        customer.getSalt() >> "/UTy3bOasug="
        customer.getPassword() >> "mzpI9aLCo2vYzGo/PlTAUjovdO4="

        when:
        service.login("email", "ruben")

        then:
        notThrown(AuthorizationException)
    }
}
