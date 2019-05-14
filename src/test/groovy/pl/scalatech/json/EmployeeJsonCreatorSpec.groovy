package pl.java.scalatech.json
import java.lang.Void as Should
import com.fasterxml.jackson.databind.ObjectMapper

import spock.lang.Specification

class EmployeeJsonCreatorSpec extends Specification {

    
    Should "serialize employee"() {
        given:
            Employee e = new Employee("slawek", "it")
        when:
            String jsonEmployee = toJson(e)
        then:
            println jsonEmployee
        when:
            Employee employeeAgain = toEmployee(jsonEmployee)
        then: 
           employeeAgain == e
    }

    private static Employee toEmployee(String jsonData) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(jsonData, Employee.class);
    }

    private static String toJson(Employee employee) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(employee);
    }
}
