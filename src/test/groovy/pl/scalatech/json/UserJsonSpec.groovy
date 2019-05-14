package pl.java.scalatech.json

import java.lang.Void as Should

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.json.JacksonTester

import spock.lang.Subject


@Subject(User)
class UserJsonSpec extends JsonSpec {

    @Autowired
    JacksonTester<User> json

    Should "serialize to json"() {
        given: "there is a user"
            User user = User.builder().age(39).salary(new BigDecimal(123)).firstName("slawek").lastName("borowiec").build()
        and: "expect json"
            def expectedUserDataAsJson = new File("src/test/resources/user.json").text
        when: "serialize user"
            def result = json.write(user).json
        then: "generated json should be valid" 
            expectedUserDataAsJson == printPretty(result)
    }

    Should "deserialize to user"() {
        given: "there is a user json"
            def userJson = new File("src/test/resources/user.json").text
        and: "there is a user to compare result"
            User user = User.builder().age(39).salary(new BigDecimal(123)).firstName("slawek").lastName("borowiec").build()
        when: "deserialize json"
            def result = json.parse(userJson).getObject()
        then: "user as object should be valid"
            user == result
    }
}
