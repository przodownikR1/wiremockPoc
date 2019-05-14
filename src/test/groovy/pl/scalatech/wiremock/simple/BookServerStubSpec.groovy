package pl.scalatech.wiremock.simple

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.Options
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*

class BookServerStubSpec extends Specification {
    @Shared
    @AutoCleanup("stop")
    WireMockServer wireMockServer = new WireMockServer(Options.DYNAMIC_PORT)

    @Shared
    def url

    def setupSpec() {
        wireMockServer.start()
        configureFor(wireMockServer.port())
        println "sdsd http://localhost:${wireMockServer.port()}"
        url = "http://localhost:${wireMockServer.port()}/library/books/123"
    }

    def ' is invoked once'() {
        given:
        def template = new RestTemplate()
        when:
        stubFor(get(urlPathMatching("/library/books/123"))
                .willReturn(aResponse()
                .withBody('sample test')
                .withStatus(200)
                .withHeader("Content-Type", "text/plain")))

        def result = template.getForObject(url, String.class)
        then:
        noExceptionThrown()
        result == 'sample test'
    }

}