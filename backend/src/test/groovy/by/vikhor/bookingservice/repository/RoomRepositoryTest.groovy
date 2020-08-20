package by.vikhor.bookingservice.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.TestPropertySource
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime

@DataJpaTest
@TestPropertySource(locations = "classpath:/test.properties")
class RoomRepositoryTest extends Specification {

    @Autowired
    private RoomRepository roomRepository
    @Autowired
    private BookingRepository bookingRepository
    @Autowired
    private UserRepository userRepository

    @Shared
    boolean setupHasRun = false

    def setup() {
        if (!setupHasRun) {
            TestDataProvider.insertTestData(roomRepository, userRepository, bookingRepository)
            setupHasRun = true
        }
    }

    @Rollback(false)
    def "should return Room 3"() {
        given: "Room 3 free in the given range. Room 1 and Room 2 not"
            def from = LocalDateTime.parse("2020-12-12T20:11:12")
            def to = LocalDateTime.parse("2020-12-12T20:15:12")
        when:
            def freeRooms = roomRepository.findFreeInRange(from, to)
        then:
            freeRooms.size() == 1 &&
                    freeRooms.first().type == "Room 3"
    }
}
