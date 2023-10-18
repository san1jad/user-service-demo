package com.user.controller;

import com.common.dto.user.UserDTO;
import com.common.exception.HandleNotFoundException;
import com.common.exception.HandledInternalServerException;
import com.common.vo.user.UserVO;
import com.user.entity.User;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        return Optional.ofNullable(userService.createUser(userDTO))
                .map(savedUser -> new ResponseEntity<>("User with name " + savedUser.getUsername() + " saved successfully!!", HttpStatus.CREATED))
                .orElseThrow(() -> new HandledInternalServerException("Something went wrong while save user . Please try after some time ! "));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserDTO updatedUserDTO) {

        return Optional.ofNullable(userService.updateUser(userId, updatedUserDTO))
                .map(savedUser -> new ResponseEntity<>("User: " + savedUser.getUsername() + " updated successfully!!", HttpStatus.OK))
                .orElseThrow(() -> new HandledInternalServerException("Something went wrong while update the user details . Please try after some time ! "));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserVO> getUserById(@PathVariable Long userId) {
        return Optional.ofNullable(userService.getUserById(userId))
                .map(savedUser -> new ResponseEntity<>(savedUser, HttpStatus.OK))
                .orElseThrow(() -> new HandledInternalServerException("User not found for the ID"));
    }

    @GetMapping
    public ResponseEntity<List<UserVO>> getAllUsers() {
        List<UserVO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
