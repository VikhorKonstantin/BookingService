package by.vikhor.bookingservice.entity;

import by.vikhor.bookingservice.config.BookingServiceAppConfig;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    @ManyToOne
    private Room room;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "room_id")
    @NotNull
    private Long roomId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ManyToOne
    private User user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "user_id")
    @NotNull
    private Long userId;

    @JsonFormat(pattern = BookingServiceAppConfig.DATE_TIME_FORMAT)
    @FutureOrPresent
    private LocalDateTime start;

    @JsonFormat(pattern = BookingServiceAppConfig.DATE_TIME_FORMAT)
    @FutureOrPresent
    private LocalDateTime end;

    private String eventDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(bookingId, booking.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }
}
