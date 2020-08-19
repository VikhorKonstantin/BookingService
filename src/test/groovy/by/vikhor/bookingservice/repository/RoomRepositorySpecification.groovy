package by.vikhor.bookingservice.repository

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@DataJpaTest
@TestPropertySource(locations = "classpath:/test.properties")
class RoomRepositorySpecification extends Specification {
    def "FindFreeInRange"() {
        when:
            def a = 1
        then:
            a == 1
    }
}
