package pl.scalatech.wiremock.simple

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import static com.github.tomakehurst.wiremock.client.WireMock.*
class SampleMockServerSpec extends  Specification{

        @Shared
        WireMockServer wireMockServer


        void setupSpec() {
            wireMockServer = new WireMockServer(wireMockConfig().port(8080))
            wireMockServer.start();
        }

        void setup() {
            WireMock.reset();
        }

        void cleanSpec() {
            wireMockServer.stop();
        }
    @Unroll
    def 'sample rest mock server test'(){
        given:

        RequestPatternBuilder builder = postRequestedFor(urlEqualTo('/api/notifications')).withRequestBody(equalTo(body))

        when:

        stubFor(post(urlEqualTo('/api/notifications')).willReturn(aResponse().withStatus(200)))

        then:

        verify(builder)

        where:
        title   | body
        "title" | "user_credentials=token&notification[title]=title&notification[long_message]=a+long+message"

    }
}
