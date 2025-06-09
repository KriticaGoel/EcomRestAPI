package com.kritica.controller;

import com.kritica.model.Users;
import com.kritica.payload.UsersDTO;
import com.kritica.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers() {

        return new ResponseEntity<>(userService.getAllusers(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<UsersDTO> createNewUser(@RequestBody UsersDTO usersDTO) {
        return new ResponseEntity<>(userService.save(usersDTO), HttpStatus.CREATED);
    }
}
