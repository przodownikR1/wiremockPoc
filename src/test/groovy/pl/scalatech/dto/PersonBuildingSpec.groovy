package pl.java.scalatech.dto

import java.lang.Void as Should
import java.lang.reflect.Modifier

import spock.lang.Specification
import spock.lang.Unroll
class PersonBuildingSpec extends Specification {

    @Unroll
    Should "#getter/#setter works and is public"() {

        given:
        def testSubject = new Person()

        when:
        testSubject.invokeMethod(setter, testObject)

        then:
        testSubject.invokeMethod(getter, null) == testObject
        testSubject.getClass().getMethod(getter).getModifiers() == Modifier.PUBLIC
        testSubject.getClass().getMethod(setter, testType).getModifiers() == Modifier.PUBLIC

        where:
        getter  | setter  | testObject | testType
        "getAge"| "setAge"| 38         | int
    }

}