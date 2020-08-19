package by.vikhor.bookingservice.repository;

public class QueryConstants {

    public static final String CONFLICT_NOT_EXISTS_QUERY = """
            SELECT CASE WHEN COUNT(b.bookingId) = 0 THEN TRUE ELSE FALSE END FROM Booking b
                WHERE (b.roomId = :roomId OR b.userId = :userId)
                AND (b.start < :end AND b.end > :start)""";


    private QueryConstants() {
    }
}
