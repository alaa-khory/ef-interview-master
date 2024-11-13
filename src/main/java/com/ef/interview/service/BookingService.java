package com.ef.interview.service;

import com.ef.interview.exception.ResourceNotFoundException;
import com.ef.interview.model.booking.Booking;
import com.ef.interview.model.booking.BookingDTO;
import com.ef.interview.model.trainer.Trainer;
import com.ef.interview.model.user.UserData;
import com.ef.interview.repository.BookingRepository;
import com.ef.interview.repository.TrainerRepository;
import com.ef.interview.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class BookingService {


    private final BookingRepository bookingRepository;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, TrainerRepository trainerRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
    }

    public Booking bookASession(BookingDTO bookingDTO){
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        if (bookingDTO.getBookingDate().isBefore(now)) {
            throw new IllegalArgumentException("Booking cannot be made in the past.");
        }

        /**
         * Here we should check the following:
         * 1- look for the trainerId and userId if Not found in DB throw exception
         * 2- add is available for each user and trainer entity and check whether one of them is not available throw exception
        **/

        Booking booking = Booking.builder()
                .userId(bookingDTO.getUserId())
                .trainerId(bookingDTO.getTrainerId())
                .bookingDate(bookingDTO.getBookingDate())
                .build();
        return bookingRepository.save(booking);
    }

    public Booking getSessionById(Long id){
        return bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Session not found"));
    }

    public void deleteSessionById(Long id){
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        bookingRepository.deleteById(id);
    }

    public Booking updateSession(Long id, BookingDTO newBookingDTO){
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        if (newBookingDTO.getBookingDate() != null && newBookingDTO.getBookingDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot update session to a past date.");
        }

        if (newBookingDTO.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(newBookingDTO.getTrainerId())
                    .orElseThrow(() -> new IllegalArgumentException("Trainer with ID " + id + " not found."));
            booking.setTrainerId(newBookingDTO.getTrainerId());
        }

        if (newBookingDTO.getUserId() != null) {
            UserData user = userRepository.findById(newBookingDTO.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found."));
            booking.setUserId(newBookingDTO.getUserId());
        }

        if (newBookingDTO.getBookingDate() != null) {
            booking.setBookingDate(newBookingDTO.getBookingDate());
        }

        booking.setBookingDate(newBookingDTO.getBookingDate());
        booking.setUserId(newBookingDTO.getUserId());
        booking.setTrainerId(newBookingDTO.getTrainerId());
        return bookingRepository.save(booking);
    }


    public String cancelSession(Long sessionId) {
        Booking booking = bookingRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session with ID " + sessionId + " not found."));

        LocalDateTime now = LocalDateTime.now();
        long hoursUntilBooking = ChronoUnit.HOURS.between(now, booking.getBookingDate());

        // Determine cancellation policy
        bookingRepository.deleteById(sessionId); // Free cancellation

        // Free cancellation
        if (hoursUntilBooking >= 24) {
            return "Booking canceled free of charge.";
        } else { // Late cancellation
            return "Booking canceled with a charge to the customer.";
        }
    }

    public String rescheduleSession(Long sessionId, Booking newBooking) {
        Booking booking = bookingRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session with ID " + sessionId + " not found."));

        LocalDateTime now = LocalDateTime.now();
        long hoursUntilBooking = ChronoUnit.HOURS.between(now, booking.getBookingDate());

        // Update booking date
        booking.setBookingDate(newBooking.getBookingDate());
        bookingRepository.save(booking);

        // Determine rescheduling policy
        if (hoursUntilBooking >= 24) {
            return "Booking rescheduled free of charge.";
        } else {
            return "Booking rescheduled with a charge to the customer.";
        }
    }

}
