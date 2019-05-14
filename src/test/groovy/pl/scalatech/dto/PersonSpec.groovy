package pl.java.scalatech.dto
import java.lang.Void as Should
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import nl.jqno.equalsverifier.EqualsVerifier

class PersonSpec extends Specification {

    @Shared
    String clientFirstName = 'slawek'

    @Shared
    int clientAge = 38

    @Subject
    @Shared
    Person client = new Person(clientFirstName, clientAge)

    Should "simple DTO test"() {
        given:
        Person p = new Person("slawek", 39)
        and:
        Person expectPerson = new Person()
        expectPerson.name = "slawek"
        expectPerson.age = 39
        expect:
        expectPerson == p
    }

    Should 'client have correct first name and age'() {
        expect:
        with(client) {
            name == clientFirstName
            age == clientAge
        }
    }

    Should 'have correct first name and age - verify all'() {
        expect:
        verifyAll {
            client.name == clientFirstName
            client.age == clientAge
        }
    }
}
