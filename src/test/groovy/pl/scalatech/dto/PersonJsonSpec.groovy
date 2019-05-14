package pl.java.scalatech.dto


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.json.BasicJsonTester
import org.springframework.boot.test.json.JacksonTester
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Subject


@ContextConfiguration(classes= [JsonConfig.class])
@JsonTest
@Subject(Person)
class PersonJsonSpec extends Specification {

    @Autowired
    private JacksonTester<Person> json;

    def "test serialize person"() {
        given:
            Person p = new Person("slawek",39)
        and:
            def personAsJson = "{\"name\":\"slawek\",\"age\":39}"
        expect:
            personAsJson == json.write(p).json
    }

    def "test deserialize person"() {
        given:
            def personAsJson = "{\"name\":\"slawek\",\"age\":39}"
        and:
            Person expectedResult = new Person("slawek",39)
        expect:
            expectedResult == json.parseObject(personAsJson)
    }

    @TestConfiguration
    @SpringBootConfiguration
    static class JsonConfig {
        @Bean
        ObjectMapper objectMapper() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false);
            return objectMapper;
        }
    }
}
