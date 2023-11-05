package com.gitcolab.controllers;


import com.gitcolab.dto.inhouse.request.UpdateUserProfileRequest;
import com.gitcolab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable("username") String userName){
        return ResponseEntity.ok(userService.getUserByUsername(userName));
    }

    @PutMapping("/user-profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
        return userService.updateUserProfile(updateUserProfileRequest);
    }

}