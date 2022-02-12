package com.example.computerstore.controllers;


import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {
//    @Autowired
//    UserService userService;

//    @Autowired
//    JwtService jwtService;

//    @PostMapping("/login")
//    public ResponseEntity<JwtResponse>

    @GetMapping("/hello")
    public String hello(){
        return "asdacx";
    }
}
