package com.hairdress.appointments.infraestructure.service

import com.hairdress.appointments.infrastructure.bbdd.models.Appointment
import com.hairdress.appointments.infrastructure.bbdd.models.Customer
import com.hairdress.appointments.infrastructure.bbdd.models.HairService
import com.hairdress.appointments.infrastructure.bbdd.models.Professional
import com.hairdress.appointments.infrastructure.bbdd.repositories.AppointmentRepository
import com.hairdress.appointments.infrastructure.error.exception.BadRequestException
import com.hairdress.appointments.infrastructure.error.exception.ModelNotFoundException
import com.hairdress.appointments.infrastructure.rest.spring.controller.mapper.AppointmentMapper
import com.hairdress.appointments.infrastructure.service.CustomerService
import com.hairdress.appointments.infrastructure.service.HairServiceService
import com.hairdress.appointments.infrastructure.service.ProfessionalService
import com.hairdress.appointments.infrastructure.service.impl.AppointmentServiceImpl
import spock.lang.Specification

import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class AppointmentServiceTest extends Specification {

    def repository = Mock(AppointmentRepository)
    def mapper = Mock(AppointmentMapper)
    def customerService = Mock(CustomerService)
    def professionalService = Mock(ProfessionalService)
    def hairServiceService = Mock(HairServiceService)
    def service = new AppointmentServiceImpl(repository, mapper, customerService,
            professionalService, hairServiceService)
    def appointment = Mock(Appointment)

    def "findAll returns a list of appointments"() {
        given:
        def appointment1 = Mock(Appointment)
        def appointment2 = Mock(Appointment)
        def appointment3 = Mock(Appointment)
        def list = new ArrayList<Appointment>()
        list.add(appointment1)
        list.add(appointment2)
        list.add(appointment3)

        repository.findAll() >> list

        when:
        def result = service.findAll()

        then:
        result.size() == 3
    }

    def "findAll returns an empty list"() {
        given:
        repository.findAll() >> new ArrayList<Appointment>()

        when:
        def result = service.findAll()

        then:
        result.isEmpty()
    }

    def "findById returns an appointment"() {
        given:
        repository.findById(_ as Long) >> Optional.of(appointment)

        when:
        def result = service.findById(1L)

        then:
        result != null
    }

    def "findById does not find an appointment"() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.findById(1L)

        then:
        thrown(ModelNotFoundException)
    }

    def "findAllAppointmentsInADay returns a list of appointments"() {
        given:
        def day = LocalDateTime.now()
        def initDate = day.truncatedTo(ChronoUnit.DAYS)
        def endDate = initDate.plusDays(1)

        def appointment1 = Mock(Appointment)
        def appointment2 = Mock(Appointment)
        def appointment3 = Mock(Appointment)
        def list = new ArrayList<Appointment>()
        list.add(appointment1)
        list.add(appointment2)
        list.add(appointment3)

        when:
        def result = service.findAllAppointmentsInADay(day)

        then:
        result.size() == 3
        1 * repository.findByAppointmentInitDateBetween(Timestamp.valueOf(initDate),
                Timestamp.valueOf(endDate)) >> list
    }

    def "findAllAppointmentsInADay returns an empty list"() {
        given:
        def day = LocalDateTime.now()
        def initDate = day.truncatedTo(ChronoUnit.DAYS)
        def endDate = initDate.plusDays(1)

        when:
        def result = service.findAllAppointmentsInADay(day)

        then:
        result.isEmpty()
        1 * repository.findByAppointmentInitDateBetween(Timestamp.valueOf(initDate),
                Timestamp.valueOf(endDate)) >> new ArrayList<Appointment>()
    }

    def "save finds a conflicting date and returns an exception"() {
        given:
        def list = new ArrayList<Appointment>()
        list.add(appointment)

        appointment.getAppointmentInitDate() >> new Timestamp(System.currentTimeMillis())
        appointment.getAppointmentEndDate() >> new Timestamp(System.currentTimeMillis())

        repository.findAppointmentsWithConflictingDates(_ as Timestamp, _ as Timestamp) >> list

        when:
        service.save(appointment, new ArrayList<Long>())

        then:
        thrown(BadRequestException)
    }

    def "save does not find a customer and returns an exception"() {
        given:
        appointment.getAppointmentInitDate() >> new Timestamp(System.currentTimeMillis())
        appointment.getAppointmentEndDate() >> new Timestamp(System.currentTimeMillis())

        repository.findAppointmentsWithConflictingDates(_ as Timestamp, _ as Timestamp) >>
                new ArrayList<Appointment>()

        def customer = Mock(Customer)
        appointment.getCustomer() >> customer
        customer.getId() >> 1L
        customerService.findById(_ as Long) >> { throw new ModelNotFoundException("TEST") }

        when:
        service.save(appointment, new ArrayList<Long>())

        then:
        thrown(ModelNotFoundException)
    }

    def "save does not find a professional and returns an exception"() {
        given:
        appointment.getAppointmentInitDate() >> new Timestamp(System.currentTimeMillis())
        appointment.getAppointmentEndDate() >> new Timestamp(System.currentTimeMillis())

        repository.findAppointmentsWithConflictingDates(_ as Timestamp, _ as Timestamp) >>
                new ArrayList<Appointment>()

        def customer = Mock(Customer)
        appointment.getCustomer() >> customer
        customer.getId() >> 1L
        customerService.findById(_ as Long) >> customer

        def professional = Mock(Professional)
        appointment.getCreatorProfessional() >> professional
        professional.getId() >> 1L
        professionalService.findById(_ as Long) >> { throw new ModelNotFoundException("TEST") }

        when:
        service.save(appointment, new ArrayList<Long>())

        then:
        thrown(ModelNotFoundException)
    }

    def "save does not find a hairService and returns an exception"() {
        given:
        appointment.getAppointmentInitDate() >> new Timestamp(System.currentTimeMillis())
        appointment.getAppointmentEndDate() >> new Timestamp(System.currentTimeMillis())

        repository.findAppointmentsWithConflictingDates(_ as Timestamp, _ as Timestamp) >>
                new ArrayList<Appointment>()

        def customer = Mock(Customer)
        appointment.getCustomer() >> customer
        customer.getId() >> 1L
        customerService.findById(_ as Long) >> customer

        def professional = Mock(Professional)
        appointment.getCreatorProfessional() >> professional
        professional.getId() >> 1L
        professionalService.findById(_ as Long) >> professional

        def serviceIds = new ArrayList<Long>()
        serviceIds.add(1L)

        hairServiceService.findById(_ as Long) >> { throw new ModelNotFoundException("TEST") }

        when:
        service.save(appointment, serviceIds)

        then:
        thrown(ModelNotFoundException)
    }

    def "save successful"() {
        given:
        appointment.getAppointmentInitDate() >> new Timestamp(System.currentTimeMillis())
        appointment.getAppointmentEndDate() >> new Timestamp(System.currentTimeMillis())

        repository.findAppointmentsWithConflictingDates(_ as Timestamp, _ as Timestamp) >>
                new ArrayList<Appointment>()

        def customer = Mock(Customer)
        appointment.getCustomer() >> customer
        customer.getId() >> 1L
        customerService.findById(_ as Long) >> customer

        def professional = Mock(Professional)
        appointment.getCreatorProfessional() >> professional
        professional.getId() >> 1L
        professionalService.findById(_ as Long) >> professional

        def serviceIds = new ArrayList<Long>()
        serviceIds.add(1L)

        def hairService = Mock(HairService)
        hairServiceService.findById(_ as Long) >> hairService

        when:
        service.save(appointment, serviceIds)

        then:
        1 * repository.save(_ as Appointment)
    }

    def "delete does not find an appointment and returns an exception"() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.delete(1L)

        then:
        thrown(ModelNotFoundException)
    }

    def "delete successful"() {
        given:
        repository.findById(_ as Long) >> Optional.of(appointment)

        when:
        service.delete(1L)

        then:
        1 * repository.delete(_ as Appointment)
    }

    def "update does not find appointment and returns an exception"() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.update(1L, appointment, new ArrayList<Long>())

        then:
        thrown(ModelNotFoundException)
    }

    def "update successful"() {
        given:
        repository.findById(_ as Long) >> Optional.of(appointment)
        mapper.updateAppointmentData(_ as Appointment, _ as Appointment) >> appointment

        when:
        service.update(1L, appointment, new ArrayList<Long>())

        then:
        1 * repository.save(_ as Appointment)
    }
}
