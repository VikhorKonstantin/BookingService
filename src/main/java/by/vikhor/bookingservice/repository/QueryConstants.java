package by.vikhor.bookingservice.repository;

public class QueryConstants {

    public static final String ROOM_CONFLICT_QUERY = """
            SELECT CASE WHEN EXISTS(
                SELECT bookingId FROM Booking WHERE room.roomId = :roomId AND ( start < :end AND end > :start ) 
            ) THEN TRUE ELSE FALSE END FROM Booking
            """;

    private QueryConstants() {
    }
}
