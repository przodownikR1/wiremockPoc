package pl.scalatech.wiremock.simple

import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomjankes.wiremock.WireMockGroovy
import org.junit.Rule
import spock.lang.Specification
import groovyx.net.http.RESTClient

class StubApiSpec extends Specification {

        @Rule
        WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort().dynamicHttpsPort())


        def "Find all users using a WireMock stub server"() {

            given: "A stubbed GET "

            def wireMockStub = new WireMockGroovy(wireMockRule.port())

            and:

            wireMockStub.stub {
                request {
                    method "GET"
                    url "/some/users"
                }
                response {
                    status 200
                    bodyFileName 'users.json'
                    headers { 'Content-Type' 'application/json' }
                }
            }


            when: "client is called"
            def response = new RESTClient("http://localhost:${wireMockRule.port()}").get(path: '/some/users')

            then: 'status is OK'
            200 == response.status

            and: "we expect 2 users"
            2 == response.data.size

            and: 'Check second firstName only'

            'Jane' == response.data[1].firstName

            and: 'Check entire response'
            [[id: 1, firstName: 'Joe', lastName: 'Doe'], [id: 2, firstName: 'Jane', lastName: 'Doe']] == response.data

            and: "the mock to be invoked exactly once"
            1L == wireMockStub.count {
                method "GET"
                url "/some/users"
            }
        }

    }