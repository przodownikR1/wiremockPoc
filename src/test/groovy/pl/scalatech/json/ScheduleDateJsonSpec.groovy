package pl.java.scalatech.json;
import java.lang.Void as Should
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import spock.lang.Subject


@Subject(ScheduleDate)
class ScheduleDateJsonSpec extends JsonSpec {

    @Autowired
    JacksonTester<ScheduleDate> json

    Should "serialize to json"() {
        given: "there is a scheduleDate"
            ScheduleDate scheduleDate = new ScheduleDate("195")
        and: "expect json"
            def expectedScheduleDataAsJson = new File("src/test/resources/scheduleData.json").text
        when: "serialize scheduleDate"
            def result = json.write(scheduleDate).json
        then: "generated json should be valid" 
            expectedScheduleDataAsJson == printPretty(result)
    }

    Should "deserialize to scheduleData"() {
        given: "there is a schuduleDate json"
            def scheduleDataJson = new File("src/test/resources/scheduleData.json").text
        and: "there is a scheduleDate to compare result"
            ScheduleDate expectedScheduleDate = new ScheduleDate("195")
        when: "deserialize json"
            def result = json.parse(scheduleDataJson).getObject()
        then: "schedule as object should be valid"
            result.schedule == expectedScheduleDate.getSchedule()
    }
}