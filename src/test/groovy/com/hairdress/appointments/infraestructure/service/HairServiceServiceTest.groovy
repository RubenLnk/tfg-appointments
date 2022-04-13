package com.hairdress.appointments.infraestructure.service

import com.hairdress.appointments.infrastructure.bbdd.models.HairService
import com.hairdress.appointments.infrastructure.bbdd.repositories.HairServiceRepository
import com.hairdress.appointments.infrastructure.error.exception.ModelNotFoundException
import com.hairdress.appointments.infrastructure.rest.spring.controller.mapper.HairServiceMapper
import com.hairdress.appointments.infrastructure.service.impl.HairServiceServiceImpl
import spock.lang.Specification

class HairServiceServiceTest extends Specification {

    def repository = Mock(HairServiceRepository)
    def mapper = Mock(HairServiceMapper)
    def service = new HairServiceServiceImpl(repository, mapper)
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

    def "findAll returns an empty list"() {
        given:
        repository.findAll() >> new ArrayList<HairService>()

        when:
        def result = service.findAll()

        then:
        result.isEmpty()
    }

    def "findAll returns a not empty list"() {
        given:
        def list = new ArrayList<HairService>()
        list.add(hairService)
        repository.findAll() >> list

        when:
        def result = service.findAll()

        then:
        result.size() == 1
    }

    def "findAllActives returns an empty list"() {
        given:
        repository.findByActiveTrue() >> new ArrayList<HairService>()

        when:
        def result = service.findAllActives()

        then:
        result.isEmpty()
    }

    def "findAllActives returns a not empty list"() {
        given:
        def list = new ArrayList<HairService>()
        list.add(hairService)
        repository.findAll() >> list
        hairService.getActive() >> true

        when:
        def result = service.findAll()

        then:
        result.get(0).getActive()
    }

    def "save returns saved entity"() {
        when:
        def result = service.save(hairService)

        then:
        result != null
        1 * repository.save(_ as HairService) >> hairService
    }

    def "update does not find the service"() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.update(1L, hairService)

        then:
        thrown(ModelNotFoundException)
    }

    def "update successful"() {
        given:
        repository.findById(_ as Long) >> Optional.of(hairService)
        mapper.updateHairServiceData(_ as HairService, _ as HairService) >> hairService

        when:
        def result = service.update(1L, hairService)

        then:
        result != null
        1 * repository.save(_ as HairService) >> hairService
    }

    def "cancel does not find service"() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.cancel(1L)

        then:
        thrown(ModelNotFoundException)
    }

    def "cancel successful"() {
        given:
        def newHairService = new HairService()
        repository.findById(_ as Long) >> Optional.of(newHairService)

        when:
        def result = service.cancel(1L)

        then:
        with(result) {
            !it.getActive()
            it.cancellationDate != null
        }
        1 * repository.save(_ as HairService) >> newHairService
    }

    def "activate does not find service"() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.activate(1L)

        then:
        thrown(ModelNotFoundException)
    }

    def "activate successful"() {
        given:
        def newHairService = new HairService()
        repository.findById(_ as Long) >> Optional.of(newHairService)

        when:
        def result = service.activate(1L)

        then:
        with(result) {
            it.getActive()
            it.cancellationDate == null
        }
        1 * repository.save(_ as HairService) >> newHairService
    }
}
