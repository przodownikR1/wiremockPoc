package pl.java.scalatech.dto;

import groovy.json.JsonSlurper
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title
//Spock in Action 
@Title("JSON file processing")
@Subject(groovy.json.JsonSlurper)
class JsonFileTestSpec extends Specification {

    def "test easy read from json file"() {
        given:
            def fileName = "src/test/resources/staff.json"
            def jsonRoot = new JsonSlurper().parse(new File(fileName))
        expect:
            jsonRoot.staff.department.name == "sales"
            jsonRoot.staff.department.employee.size() == 2
    }
}