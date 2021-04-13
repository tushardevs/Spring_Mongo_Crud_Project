package com.devp.springmongocrud.controller;

import com.devp.springmongocrud.exception.ResourceNotFound;
import com.devp.springmongocrud.model.User;
import com.devp.springmongocrud.repository.UserRepository;
import com.devp.springmongocrud.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/user/all")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFound{

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/user/create")
    public User createUser(@Valid @RequestBody User user){
        user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        return userRepository.save(user);
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
                                           @Valid @RequestBody User userDetails)
    throws ResourceNotFound{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found for this id: " + userId));

        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());

        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{id}")
    public Map<String,Boolean> deleteUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFound{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found for this id: " + userId));
        userRepository.delete(user);
        Map<String, Boolean> result= new HashMap<>();
        result.put("Deleted ", true);
        return result;
    }
}
