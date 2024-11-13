package com.ef.interview.controller;

import com.ef.interview.model.ApiResponse;
import com.ef.interview.model.booking.Booking;
import com.ef.interview.model.booking.BookingDTO;
import com.ef.interview.model.trainer.Trainer;
import com.ef.interview.service.TrainerService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<Trainer>> saveUser() {
        try {
            Trainer trainer = trainerService.saveRandomTrainer();
            return ResponseEntity.ok().body(new ApiResponse(true, "Trainer saved successfully", trainer));
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ApiResponse<>(false, "Trainer is not created for " + ex.getMessage(), null));
        }
    }


}
