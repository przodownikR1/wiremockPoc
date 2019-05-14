package pl.java.scalatech.dto
import java.lang.Void as Should
import com.fasterxml.jackson.databind.ObjectMapper

import groovy.json.JsonSlurper
import spock.lang.Specification

class PersonJsonThirdSpec extends Specification{
    def objectMapper
    def jsonSlurper

    void setup() {
        objectMapper = new ObjectMapper()
        jsonSlurper = new JsonSlurper()
    }

    Should "serialize person"() {
        given:
            Person person = new Person("slawek",39)
        when:
            def result = objectMapper.writeValueAsString(person)
            println(result)
            def object = jsonSlurper.parseText(result)
        then:
            object.name == "slawek"
            object.age == 39
    }

    Should "serialize json string to object"() {
        setup:
          JsonSlurper jsonSlurper = new JsonSlurper()
        when:
          Map map = jsonSlurper.parseText('{"id":1}')
        then:
          map
          map.id == 1
      }
}
