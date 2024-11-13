package com.ef.interview.service;

import com.ef.interview.model.booking.Booking;
import com.ef.interview.model.booking.BookingDTO;
import com.ef.interview.model.trainer.Trainer;
import com.ef.interview.model.trainer.TrainerDTO;
import com.ef.interview.model.user.UserDTO;
import com.ef.interview.repository.TrainerRepository;
import com.ef.interview.util.ThirdPartyHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final ThirdPartyHelper thirdPartyHelper;

    private final BookingService bookingService;

    public TrainerService(TrainerRepository trainerRepository, ThirdPartyHelper thirdPartyHelper, BookingService bookingService) {
        this.trainerRepository = trainerRepository;
        this.thirdPartyHelper = thirdPartyHelper;
        this.bookingService = bookingService;
    }


    public Trainer saveRandomTrainer(){
        UserDTO userDTO = thirdPartyHelper.prepareUser();
        TrainerDTO trainerDTO = TrainerDTO.builder()
                .name(userDTO.getName())
                .address(userDTO.getAddress())
                .email(userDTO.getEmail())
                .build();
        return saveTrainerInDB(trainerDTO);
    }

    public Trainer saveTrainerInDB(TrainerDTO trainerDTO){
        Trainer trainer = new Trainer();
        trainer.setName(trainerDTO.getName());
        trainer.setAddress(trainerDTO.getAddress());
        trainer.setEmail(trainerDTO.getEmail());
        return trainerRepository.save(trainer);
    }

    public Booking bookASession(BookingDTO bookingDTO){
        Date currentDate = new Date();

        if(bookingDTO.getBookingDate().before(currentDate)){
            return null;
        }

        return bookingService.bookASession(bookingDTO);
    }

    public Booking getSessionById(Long id) {
        return bookingService.getSessionById(id);
    }

    public void deleteSessionById(Long id){
        bookingService.deleteSessionById(id);
    }

    public Booking updateSession(Long id, BookingDTO newBookingDTO){
        return bookingService.updateSession(id,newBookingDTO);
    }
}
