package com.ef.interview.service;


import com.ef.interview.exception.ResourceNotFoundException;
import com.ef.interview.model.user.UserDTO;
import com.ef.interview.model.user.UserData;
import com.ef.interview.repository.UserRepository;
import com.ef.interview.util.ThirdPartyHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ThirdPartyHelper thirdPartyHelper;

    public UserService(UserRepository userRepository, ThirdPartyHelper thirdPartyHelper) {
        this.userRepository = userRepository;
        this.thirdPartyHelper = thirdPartyHelper;
    }

    public UserData saveRandomUserInDB(){
        UserDTO userDTO = thirdPartyHelper.prepareUser();
        return saveUserInDB(userDTO);
    }


    public UserData saveUserInDB(UserDTO userDTO){
        UserData user = new UserData();
        user.setName(userDTO.getName());
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }


    public Optional<UserData> getUserById(Long id){
        UserData user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return Optional.of(user);
    }

}
