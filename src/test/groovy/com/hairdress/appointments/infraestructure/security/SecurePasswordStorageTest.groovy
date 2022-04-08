package com.hairdress.appointments.infraestructure.security

import com.hairdress.appointments.infrastructure.error.exception.SecurePasswordException
import com.hairdress.appointments.infrastructure.security.SecurePasswordStorage
import spock.lang.Specification

class SecurePasswordStorageTest extends Specification {

    def securePasswordStorage = new SecurePasswordStorage();

    def "getNewSalt must return different salts in each call"() {
        when: "We do two calls to get two salts"
        def salt1 = securePasswordStorage.getNewSalt()
        def salt2 = securePasswordStorage.getNewSalt()

        then: "Salts obtained must always be different"
        salt1 != salt2
    }

    def "getEncryptedPassword returns the right password"() {
        given: "A salt and a password"
        def salt = "/UTy3bOasug="
        def password = "ruben"
        def expectedEncryptedPassword = "mzpI9aLCo2vYzGo/PlTAUjovdO4="

        when: "We get the encrypted password"
        def result = securePasswordStorage.getEncryptedPassword(password, salt)

        then: "The password must be the expected one"
        result == expectedEncryptedPassword
    }

    def "getEncryptedPassword must return different encrypted passwords when different salts are used"() {
        given: "Two different salts and one password"
        def password = "ruben"
        def salt1 = "/UTy3bOasug="
        def salt2 = "HupN4bOasugO"

        when: "We get the passwords with both salts"
        def pass1 = securePasswordStorage.getEncryptedPassword(password, salt1)
        def pass2 = securePasswordStorage.getEncryptedPassword(password, salt2)

        then: "The passwords we got must be differents"
        pass1 != pass2
    }
}
