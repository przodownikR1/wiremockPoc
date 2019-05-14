package pl.java.scalatech.json;

import static groovy.json.JsonOutput.prettyPrint
import static groovy.json.JsonOutput.toJson

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.json.JacksonTester
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ContextConfiguration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import spock.lang.Shared
import spock.lang.Specification

@ContextConfiguration(classes= [JsonConfig.class])
@JsonTest
public class JsonSpec extends Specification {

    @Shared
    JsonSlurper jsonSlurper = new JsonSlurper()

    @TestConfiguration
    @SpringBootConfiguration
    static class JsonConfig {
        @Bean
        ObjectMapper objectMapper() {
            ObjectMapper objectMapper = new ObjectMapper()
            objectMapper.registerModule(new JavaTimeModule())
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false)
            return objectMapper
        }
    }

    def prettyJson(String json) {
        parse(json).toString()
    }

    def parse(json) {
        jsonSlurper.parseText(json)
    }

    static String printPrettyEmbedded(String jsonInput) {
        def jsonOutput = toJson(jsonInput)
        prettyPrint(jsonOutput).toString()
    }

    static String printPretty(String json) {
        prettyPrint(json).toString()
    }

    static List<String> getFieldsFromObject(def object){
        object.getProperties().keySet().collect().findAll { !it.equals("class") }.sort()
    }
}