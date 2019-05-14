package pl.scalatech.wiremock.simple

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomjankes.wiremock.WireMockGroovy;
import org.junit.Rule;
import spock.lang.Specification;
import spock.lang.Subject;
class StubbingSpec extends Specification {

    public static final int PORT = 8080

    @Rule
    WireMockRule wireMockRule = new WireMockRule(PORT)
    @Subject
    def wireMockGroovy = WireMockGroovy.builder().withPort(PORT).enableJsonBodyFeature().build()

    def "should correctly stub wire mock get request" () {
        when:
        wireMockGroovy.stub {
            request {
                method "GET"
                url "/some/thing"
            }
            response {
                status 200
                body "Some body"
                headers {
                    "Content-Type" "text/plain"
                }
            }
        }

        then:
        new URL("http://localhost:8080/some/thing").getText() == "Some body"
    }
}