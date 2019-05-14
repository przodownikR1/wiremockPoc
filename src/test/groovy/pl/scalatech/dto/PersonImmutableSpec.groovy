package pl.java.scalatech.dto
import java.lang.Void as Should

import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import spock.lang.IgnoreRest
import spock.lang.Specification

class PersonImmutableSpec extends Specification{

    Should "testHashCodeAndEquals"() {
        when:
        EqualsVerifier.forClass(PersonImmutable.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify()

        then:
        noExceptionThrown()
    }
}
