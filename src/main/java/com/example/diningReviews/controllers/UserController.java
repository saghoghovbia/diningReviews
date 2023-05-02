package com.example.diningReviews.controllers;

import com.example.diningReviews.models.User;
import com.example.diningReviews.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping()
    public Iterable<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    @GetMapping("/{displayName}")
    public User getUserByDisplayName(@PathVariable String displayName){
        validateDisplayName(displayName);

        Optional<User> option = this.userRepository.findUserByDisplayName(displayName);
        if(option.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        User foundUser = option.get();

        return foundUser;
    }

    @PostMapping()
    public User createNewUser(@RequestBody User user){
        validateUser(user);

        User newUser = this.userRepository.save(user);

        return newUser;
    }
/*
    @PutMapping("/{displayName}")
    public User update(@PathVariable String displayName, @RequestBody User updatedUser){ // TODO: Look into autoconsructor and see how the requestbody requires the consturctor to be built/used
        validateDisplayName(displayName); //This might do nothing...the fact that I made it in here means that I must have a displayName
                                            // TODO: Test endpoints with than without this unnecessary code

        Optional<User> option = this.userRepository.findUserByDisplayName(displayName);

        if(option.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        User existingUser = option.get();

        updateUserInfo(updatedUser, existingUser);

        this.userRepository.save(existingUser);

        return existingUser;

    }

    private void updateUserInfo(User updatedUser, User existingUser){ //if you give me something in this field, we'll change it
        if(ObjectUtils.isEmpty(updatedUser.getDisplayName())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if(!ObjectUtils.isEmpty(updatedUser.getCity())){
            existingUser.setCity(updatedUser.getCity());
        }

        if(!ObjectUtils.isEmpty(updatedUser.getState())){
            existingUser.setState(updatedUser.getState());
        }

        if(!ObjectUtils.isEmpty(updatedUser.getZipCode())){
            existingUser.setZipCode(updatedUser.getZipCode());
        }

        if(!ObjectUtils.isEmpty(updatedUser.getPeanutAllergy())){
            existingUser.setPeanutAllergy(updatedUser.getPeanutAllergy());
        }

        if(!ObjectUtils.isEmpty(updatedUser.getEggAllergy())){
            existingUser.setEggAllergy(updatedUser.getEggAllergy());
        }

        if(!ObjectUtils.isEmpty(updatedUser.getDairyAllergy())){
            existingUser.setDairyAllergy(updatedUser.getDairyAllergy());
        }

    }

 */

    private void validateUser(User user){ //If the String passed already exists, throw error
        validateDisplayName(user.getDisplayName());

        Optional<User> existingUser = this.userRepository.findUserByDisplayName(user.getDisplayName());
        if(existingUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    private void validateDisplayName(String displayName){ //Makes sure the user passes the display name as an argument
        if(ObjectUtils.isEmpty(displayName)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
