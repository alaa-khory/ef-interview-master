package com.ef.interview.controller;

import com.ef.interview.exception.ResourceNotFoundException;
import com.ef.interview.model.ApiResponse;
import com.ef.interview.model.booking.Booking;
import com.ef.interview.model.booking.BookingDTO;
import com.ef.interview.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/sessions")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<Booking>> bookASession(@RequestBody BookingDTO bookingDTO) {
        try {
            Booking bookedSession = bookingService.bookASession(bookingDTO);
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Session is Booked successfully", bookedSession));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<ApiResponse<Booking>> getSessionById(@PathVariable Long sessionId) {
        try {
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Session retrieved successfully", bookingService.getSessionById(sessionId)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Session with[" + sessionId + "] is not found", null));
        }
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<ApiResponse<String>> deleteSessionById(@PathVariable Long sessionId) {
        try {
            bookingService.deleteSessionById(sessionId);
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Session with ID[" + sessionId + "]Deleted", "Success"));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, ex.getMessage(), "Failed"));
        }
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<ApiResponse<Booking>> updateSession(@PathVariable Long sessionId, @RequestBody BookingDTO bookingDTO) {
        try {
            Booking booking = bookingService.updateSession(sessionId, bookingDTO);
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Session with ID[" + sessionId + "] is updated", booking));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<String>> cancelSession(@PathVariable Long id) {
        try {
            String message = bookingService.cancelSession(id);
            return ResponseEntity.ok(new ApiResponse<>(true, message, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/reschedule")
    public ResponseEntity<ApiResponse<String>> rescheduleSession(
            @PathVariable Long id,
            @RequestBody Booking newBooking) {
        try {
            String message = bookingService.rescheduleSession(id, newBooking);
            return ResponseEntity.ok(new ApiResponse<>(true, message, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }


}
