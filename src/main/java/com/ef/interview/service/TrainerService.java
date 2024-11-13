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


    public TrainerService(TrainerRepository trainerRepository, ThirdPartyHelper thirdPartyHelper) {
        this.trainerRepository = trainerRepository;
        this.thirdPartyHelper = thirdPartyHelper;
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
}
