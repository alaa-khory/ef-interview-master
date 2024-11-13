package com.ef.interview.controller;


import com.ef.interview.model.ApiResponse;
import com.ef.interview.model.user.UserDTO;
import com.ef.interview.model.user.UserData;
import com.ef.interview.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserData>> saveUser(){
        try {
            UserData userData = userService.saveRandomUserInDB();
            return ResponseEntity.ok().body(new ApiResponse<>(true, "User saved successfully", userData));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ApiResponse<>(false, "User is not created for " + ex.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserData>> getUserById(@PathVariable Long id){
        Optional<UserData> optionalUser = userService.getUserById(id);
        try{
            return ResponseEntity.ok().body(new ApiResponse<>(true, "User saved successfully", optionalUser.get()));
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "User saved successfully", optionalUser.get()));
        }
    }

}
