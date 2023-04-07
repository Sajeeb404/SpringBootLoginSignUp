package com.example.HomeToHomeSpring.controller;


import com.example.HomeToHomeSpring.dto.JwtResponse;
import com.example.HomeToHomeSpring.dto.LoginRequest;
import com.example.HomeToHomeSpring.dto.MessageResponse;
import com.example.HomeToHomeSpring.dto.SignupRequest;
import com.example.HomeToHomeSpring.model.Role;
import com.example.HomeToHomeSpring.model.User;
import com.example.HomeToHomeSpring.repository.RoleDao;
import com.example.HomeToHomeSpring.repository.UserDao;
import com.example.HomeToHomeSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LoginController {


    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;


    @Autowired
    UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @GetMapping({"/getUser"})
    public Iterable<User> getAll() {
        return userDao.findAll();
    }


    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userDao.save(user);
    }


    @PostMapping({"/createNewRole"})
    public Role createNewRole(@RequestBody Role role) {
        return roleDao.save(role);
    }


    @PostMapping({"/signin"})
    public ResponseEntity<?> createJwtToken22(@RequestBody LoginRequest loginRequest) throws Exception {
        System.out.println("Test Action");
        User user = userDao.findByUserNameAndPassword(loginRequest.getUsername(), loginRequest.getPassword()).get();
        return ResponseEntity.ok(new JwtResponse(user));
    }


    @PostMapping({"/signup"})
    public ResponseEntity<?> registerNewUserNew(@RequestBody SignupRequest signupRequest) {
        User user = new User();
        user.setUserName(signupRequest.getUsername());
        user.setUserFullName(signupRequest.getUserfullname());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        if (userDao.existsByUserName(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userDao.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setPassword(user.getPassword());
        userDao.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }




}
