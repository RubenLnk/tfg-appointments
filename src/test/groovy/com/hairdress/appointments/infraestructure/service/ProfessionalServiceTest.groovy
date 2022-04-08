package com.hairdress.appointments.infraestructure.service

import com.hairdress.appointments.infrastructure.bbdd.models.Professional
import com.hairdress.appointments.infrastructure.bbdd.repositories.ProfessionalRepository
import com.hairdress.appointments.infrastructure.error.exception.AuthorizationException
import com.hairdress.appointments.infrastructure.error.exception.GenericException
import com.hairdress.appointments.infrastructure.error.exception.ModelNotFoundException
import com.hairdress.appointments.infrastructure.error.exception.UserAlreadyExistsException
import com.hairdress.appointments.infrastructure.rest.spring.controller.mapper.ProfessionalMapper
import com.hairdress.appointments.infrastructure.service.impl.ProfessionalServiceImpl
import spock.lang.Specification

class ProfessionalServiceTest extends Specification {

    def repository = Mock(ProfessionalRepository)
    def mapper = Mock(ProfessionalMapper)
    def service = new ProfessionalServiceImpl(repository, mapper)
    def professional = Mock(Professional)

    def "findById returns a professional"() {
        given:
        repository.findById(_ as Long) >> Optional.of(professional)

        when:
        def result = service.findById(1L)

        then:
        result != null
    }

    def "findById returns no professional"() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.findById(1L)

        then:
        thrown(ModelNotFoundException)
    }

    def "findAll returns an empty list"() {
        given:
        repository.findAll() >> []

        when:
        def result = service.findAll()

        then:
        result.isEmpty()
    }

    def "findAll returns a list with one value"() {
        given:
        repository.findAll() >> [new Professional()]

        when:
        def result = service.findAll()

        then:
        result.size() == 1
    }

    def "findAll returns a list with multiple value"() {
        given:
        repository.findAll() >> [new Professional(), new Professional(), new Professional()]

        when:
        def result = service.findAll()

        then:
        result.size() == 3
    }

    def "signUp signs up a new professional"() {
        given:
        def professionalToSignUp = new Professional()
        professionalToSignUp.uid = "UID"
        professionalToSignUp.password = "password"
        repository.findByUid(_ as String) >> Optional.empty()
        repository.save(_ as Professional) >> professional

        when:
        def result = service.signUp(professionalToSignUp)

        then:
        result == professional
    }

    def "signUp does not signs up an existing professional"() {
        given:
        def professionalToSignUp = new Professional()
        professionalToSignUp.uid = "UID"

        repository.findByUid(_ as String) >> Optional.of(professionalToSignUp)

        when:
        service.signUp(professionalToSignUp)

        then:
        thrown(UserAlreadyExistsException)
    }

    def "signUp throws an unexpected exception"() {
        given:
        def professionalToSignUp = Mock(Professional)
        professionalToSignUp.uid >> "UID"
        professionalToSignUp.password >> "password"
        repository.findByUid(_ as String) >> Optional.empty()
        professionalToSignUp.setPassword(_ as String) >> { throw new GenericException("ERROR") }

        when:
        service.signUp(professionalToSignUp)

        then:
        thrown(GenericException)
    }

    def "login does not find uid"() {
        given:
        repository.findByUid(_ as String) >> Optional.empty()

        when:
        service.login("TEST", "TEST")

        then:
        thrown(AuthorizationException)
    }

    def "login password given by user is not correct"() {
        given:
        repository.findByUid(_ as String) >> Optional.of(professional)
        professional.getSalt() >> "salt"
        professional.getPassword() >> "encryptedPassword"

        when:
        service.login("TEST", "PASSWORD")

        then:
        thrown(AuthorizationException)
    }

    def "login authenticate professional successfully"() {
        given:
        repository.findByUid(_ as String) >> Optional.of(professional)
        professional.getSalt() >> "/UTy3bOasug="
        professional.getPassword() >> "mzpI9aLCo2vYzGo/PlTAUjovdO4="

        when:
        service.login("TEST", "ruben")

        then:
        notThrown(AuthorizationException)
    }

    def "changePassword - user does not exists"() {
        given:
        repository.findByUid(_ as String) >> Optional.empty()

        when:
        service.changePassword(_ as String, _ as String, _ as String)

        then:
        thrown(AuthorizationException)
    }

    def "changePassword - old password is not correct"() {
        given:
        repository.findByUid(_ as String) >> Optional.of(professional)
        professional.getSalt() >> "/UTy3bOasug="
        professional.getPassword() >> "mzpI9aLCo2vYzGo/PlTAUjovdO4="

        when:
        service.changePassword("TEST", "incorrect", "correct")

        then:
        thrown(AuthorizationException)
    }

    def "changePassword success"() {
        given:
        repository.findByUid(_ as String) >> Optional.of(professional)
        professional.getSalt() >> "/UTy3bOasug="
        professional.getPassword() >> "mzpI9aLCo2vYzGo/PlTAUjovdO4="

        when:
        service.changePassword("TEST", "ruben", "newPass")

        then:
        1 * repository.save(_ as Professional)
    }

    def "update does not find professional"() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.update(1L, new Professional())

        then:
        thrown(ModelNotFoundException)
    }

    def "update success"() {
        given:
        repository.findById(_ as Long) >> Optional.of(professional)
        mapper.updateProfessionalData(_ as Professional, _ as Professional) >> professional

        when:
        service.update(1L, new Professional())

        then:
        1 * repository.save(_ as Professional)
    }

    def "delete does not find professional"() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.delete(1L)

        then:
        thrown(ModelNotFoundException)
    }

    def "delete success"() {
        given:
        repository.findById(_ as Long) >> Optional.of(professional)

        when:
        service.delete(1L)

        then:
        1 * repository.delete(_ as Professional)
    }
}
