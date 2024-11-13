package com.ef.interview.model.booking;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingDTO {
    private Long userId;
    private Long trainerId;
    private LocalDateTime bookingDate;
}
