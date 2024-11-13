package com.ef.interview.controller;

import com.ef.interview.model.booking.Booking;
import com.ef.interview.model.booking.BookingDTO;
import com.ef.interview.model.trainer.Trainer;
import com.ef.interview.service.TrainerService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }


    @PostMapping
    public ResponseEntity<Trainer> saveUser() {
        return ResponseEntity.ok().body(trainerService.saveRandomTrainer());
    }

    @PostMapping("/bookASession")
    public ResponseEntity<Booking> bookASession(@RequestBody BookingDTO bookingDTO) {
        Booking bookedSession = trainerService.bookASession(bookingDTO);
        if (Objects.nonNull(bookedSession)) {
            return ResponseEntity.ok().body(trainerService.bookASession(bookingDTO));
        } else {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<Booking> getSessionById(@PathVariable Long sessionId) {
        return ResponseEntity.ok().body(trainerService.getSessionById(sessionId));
    }

    @DeleteMapping("/sessions/{sessionId}")
    public ResponseEntity<?> deleteSessionById(@PathVariable Long sessionId) {
        trainerService.deleteSessionById(sessionId);
        return ResponseEntity.ok().body("Session with ID[" + sessionId + "]Deleted");
    }

    @PutMapping("/sessions/{sessionId}")
    public ResponseEntity<Booking> updateSession(@PathVariable Long sessionId, @RequestBody BookingDTO bookingDTO) {
        Booking booking = trainerService.updateSession(sessionId, bookingDTO);
        return ResponseEntity.ok().body(booking);
    }


}
