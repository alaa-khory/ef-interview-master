package com.ef.interview.controller;


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
    public ResponseEntity<UserData> saveUser(){
        return ResponseEntity.ok().body(userService.saveRandomUserInDB());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserData> getUserById(@PathVariable Long id){
        Optional<UserData> optionalUser = userService.getUserById(id);
        if(optionalUser.isPresent()){
            return ResponseEntity.ok().body(optionalUser.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
