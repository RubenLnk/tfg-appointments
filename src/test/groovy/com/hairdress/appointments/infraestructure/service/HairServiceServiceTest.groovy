package com.hairdress.appointments.infraestructure.service

import com.hairdress.appointments.infrastructure.bbdd.models.HairService
import com.hairdress.appointments.infrastructure.bbdd.repositories.HairServiceRepository
import com.hairdress.appointments.infrastructure.error.exception.ModelNotFoundException
import com.hairdress.appointments.infrastructure.service.impl.HairServiceServiceImpl
import spock.lang.Specification

class HairServiceServiceTest extends Specification {

    def repository = Mock(HairServiceRepository)
    def service = new HairServiceServiceImpl(repository)
    def hairService = Mock(HairService)

    def "findById returns a service"() {
        given:
        repository.findById(_ as Long) >> Optional.of(hairService)

        when:
        def result = service.findById(1L)

        then:
        result != null
    }

    def "findById does not find a service"() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.findById(1L)

        then:
        thrown(ModelNotFoundException)
    }
}
