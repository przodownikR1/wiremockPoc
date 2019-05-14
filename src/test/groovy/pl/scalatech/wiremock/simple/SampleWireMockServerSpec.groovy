package pl.scalatech.wiremock.simple

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.Options
import groovyx.net.http.RESTClient
import org.junit.Rule
import org.springframework.web.client.RestTemplate
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

class SampleWireMockServerSpec extends Specification {


    def "record"() {
        given:
        WireMockServer server = new WireMockServer(9080)
        server.start()
        def template = new RestTemplate()

        and:
        configureFor("localhost", 9080)
        stubFor(get(urlEqualTo("/some/thing"))
                .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("Hello world")))
        when:
        String response = template.getForObject("http://localhost:9080/some/thing",String.class)

        then:
        response == 'Hello world'

        cleanup:
        server.stop()
    }

}