package pl.java.scalatech.json

import com.fasterxml.jackson.databind.ObjectMapper
import java.lang.Void as Should
import spock.lang.Specification

class EmployeeToStaticFactorySpec extends  Specification {
    
    Should "serialize employee"() {
        given:
            EmployeeStaticFactory e = EmployeeStaticFactory.createEmployee("slawek","it")
        when:
            String jsonEmployee = toJson(e)
        then:
            println jsonEmployee
        when:
            EmployeeStaticFactory employeeAgain = toEmployee(jsonEmployee)
        then:
           employeeAgain == e
    }
    private static EmployeeStaticFactory toEmployee(String jsonEmployee) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(jsonEmployee, EmployeeStaticFactory.class);
    }

    private static String toJson(EmployeeStaticFactory employee) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(employee);
    }

}
