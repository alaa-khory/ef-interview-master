package com.ef.interview.service;

import com.ef.interview.exception.ResourceNotFoundException;
import com.ef.interview.model.booking.Booking;
import com.ef.interview.model.booking.BookingDTO;
import com.ef.interview.repository.BookingRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {


    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking bookASession(BookingDTO bookingDTO){
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
        booking.setBookingDate(newBookingDTO.getBookingDate());
        booking.setUserId(newBookingDTO.getUserId());
        booking.setTrainerId(newBookingDTO.getTrainerId());
        return bookingRepository.save(booking);
    }

}
