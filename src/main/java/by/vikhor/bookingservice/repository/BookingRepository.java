package by.vikhor.bookingservice.repository;

import by.vikhor.bookingservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(QueryConstants.CONFLICT_NOT_EXISTS_QUERY)
    boolean isConflictNotExists(@Param("roomId") Long roomId, @Param("userId") Long userId,
                             @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
