/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.controller;

import com.duong.training.dto.UserDTO;
import com.duong.training.dto.VerificationTokenDTO;
import com.duong.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("user/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Integer idProduct) {
        UserDTO userDTO = userService.findById(idProduct);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("user/username/{username}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable("username") String username) {
        UserDTO userDTO = userService.findByUserName(username);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // update for register
    @PostMapping("user")
    public ResponseEntity<UserDTO> registerNewUser(@RequestBody UserDTO userDTO) {
        if (isEmailExits(userDTO.getEmail()) || isUsernameExist(userDTO.getUserName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            userDTO.setStatus(0);
            UserDTO response = userService.addUser(userDTO);
            return new ResponseEntity<>(response, null, HttpStatus.OK);
        }
    }

    private boolean isUsernameExist(String userName) {
        UserDTO user = userService.findByUserName(userName);
        return (user != null);
    }

    private boolean isEmailExits(String email) {
        UserDTO user = userService.findByEmail(email);
        return (user != null);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable(name = "id") int id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("Delete successfully");
    }

    // bonus for security
    @GetMapping("user/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable("email") String email) {
        UserDTO userDTO = userService.findByEmail(email);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // update for user management
    @GetMapping("users")
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "", required = false) String searchValue,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "5", required = false) Integer size,
            @RequestParam(defaultValue = "userId", required = false) String sortBy) {
        Page<UserDTO> users = userService.getAllUser(searchValue, page, size, sortBy);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("user/generate-verify-token")
    public ResponseEntity<String> generateVerificationToken(@RequestBody UserDTO userDTO) {
        String token = userService.createVerificationToken(userDTO);
        return new ResponseEntity<>(token, null, HttpStatus.OK);
    }

    @GetMapping("user/verify-token/{token}")
    public ResponseEntity<VerificationTokenDTO> getVerificationToken(@PathVariable("token") String token) {
        VerificationTokenDTO verifyToken = userService.getVerificationToken(token);
        return new ResponseEntity<>(verifyToken, null, HttpStatus.OK);
    }

    @PostMapping("user/active")
    public ResponseEntity<UserDTO> activeRegisteredUser(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.activeUser(userDTO);
        return new ResponseEntity<>(user, null, HttpStatus.OK);
    }

    @PostMapping("user/changePassword")
    public ResponseEntity<UserDTO> updateNewPassword(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.saveChangePassword(userDTO);
        return new ResponseEntity<>(user, null, HttpStatus.OK);
    }

    @PostMapping("user/generate-pass-reset-token")
    public ResponseEntity<String> generatePasswordResetToken(@RequestBody UserDTO userDTO) {
        String token = userService.createPasswordResetToken(userDTO);
        return ResponseEntity.ok(token);
    }

    @GetMapping("user/pass-reset-token/{token}")
    public ResponseEntity<VerificationTokenDTO> getPasswordResetToken(@PathVariable("token") String token) {
        VerificationTokenDTO verifyToken = userService.getPasswordResetToken(token);
        return ResponseEntity.ok(verifyToken);
    }

    @PostMapping("user/toggle-block")
    public ResponseEntity<UserDTO> updateUserBlockedStatus(
            @RequestParam Integer id, @RequestParam Integer status) {
        UserDTO user = userService.updateUserBlockedStatus(id, status);
        return ResponseEntity.ok(user);
    }

    @PutMapping("user/update")
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO userDTO) {
        UserDTO result = userService.updateUser(userDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("user/count")
    public ResponseEntity<Long> getNumberOfUserIsUnblocked() {
        Long userNum = userService.countUserUnblocked();
        return new ResponseEntity<>(userNum, HttpStatus.OK);
    }

    @GetMapping("users/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAllCustomer();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("users/save_all")
    public ResponseEntity<Boolean> saveAllUsers(@RequestBody List<UserDTO> users) {
        Boolean result = userService.saveAllUsers(users);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
