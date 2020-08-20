package by.vikhor.bookingservice.repository;

public class QueryConstants {

    private static final String CONFLICT_EXISTS = "( b.start < :end AND b.end > :start )";
    public static final String CONFLICT_NOT_EXISTS_QUERY =
            "SELECT CASE WHEN COUNT(b.bookingId) = 0 THEN TRUE ELSE FALSE END FROM Booking b" +
            "    WHERE (b.roomId = :roomId OR b.userId = :userId)" +
            "    AND " + CONFLICT_EXISTS;
    public static final String SELECT_FREE_IN_RANGE = "SELECT r from Booking b RIGHT JOIN b.room r " +
                                                      "WHERE NOT" + CONFLICT_EXISTS + " OR b = null";


    private QueryConstants() {
    }
}
