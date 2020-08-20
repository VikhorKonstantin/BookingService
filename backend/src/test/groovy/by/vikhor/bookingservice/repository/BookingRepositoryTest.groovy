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
class BookingRepositoryTest extends Specification {
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
    def "should return false because of time conflict on Room 1"() {
        given: "Room 1 has already been booked in range from 2020-12-12T20:12:12 to 2020-12-12T20:20:12"
            def from = LocalDateTime.parse("2020-12-12T20:11:12")
            def to = LocalDateTime.parse("2020-12-12T20:15:12")
        when:
            def isNoConflicts = bookingRepository.isConflictNotExists(1, 3, from, to)
        then: "have a conflict"
            !isNoConflicts
    }

    @Rollback(false)
    def "should return false because of time conflict on user 1"() {
        given: "User 1 already has a booking in range from 2020-12-12T20:12:12 to 2020-12-12T20:20:12"
            def from = LocalDateTime.parse("2020-12-12T20:11:12")
            def to = LocalDateTime.parse("2020-12-12T20:15:12")
        when:
            println(bookingRepository.findAll())
            def isNoConflicts = bookingRepository.isConflictNotExists(3, 1, from, to)
        then: "have a conflict"
            !isNoConflicts
    }

    @Rollback(false)
    def "should return true"() {
        given: "Room 1 has already been booked in range from 2020-12-12T20:12:12 to 2020-12-12T20:20:12"
            def from = LocalDateTime.parse("2020-12-12T20:11:12")
            def to = LocalDateTime.parse("2020-12-12T20:15:12")
        when:
            def isNoConflicts = bookingRepository.isConflictNotExists(3, 3, from, to)
        then: "have no conflicts"
            isNoConflicts
    }
}
