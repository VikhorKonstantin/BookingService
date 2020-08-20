package by.vikhor.bookingservice.repository;

import by.vikhor.bookingservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(QueryConstants.SELECT_FREE_IN_RANGE)
    Set<Room> findFreeInRange(@Param("start") LocalDateTime from, @Param("end") LocalDateTime to);
}
