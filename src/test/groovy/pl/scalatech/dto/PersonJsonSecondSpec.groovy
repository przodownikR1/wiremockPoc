package pl.java.scalatech.dto

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.springframework.boot.test.json.JacksonTester

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer

import spock.lang.Specification

class PersonJsonSecondSpec extends Specification {
    JacksonTester<Person> json

    def setup() {
       ObjectMapper objectMapper = new ObjectMapper();
       objectMapper.registerModule(javaTimeModule());
       JacksonTester.initFields(this, objectMapper);
    }

    static JavaTimeModule javaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ISO_DATE_TIME));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(ISO_DATE_TIME));
        return javaTimeModule;
    }

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
}
