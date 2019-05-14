package pl.java.scalatech.dto

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import groovy.json.JsonBuilder
import spock.lang.Specification

class WeekdaySpec extends Specification{
    private ObjectMapper mapper
    def jsonParser
    def deserializationCtx

    def setup() {
        mapper = new ObjectMapper()
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        deserializationCtx = mapper.getDeserializationContext()
    }

    def "testing enum values "(){
        given:
            def json = new JsonBuilder(Weekday.MONDAY).toPrettyString()
            jsonParser = mapper.getFactory().createParser(json)
        expect:
            println json
    }

}
