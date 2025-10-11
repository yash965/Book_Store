package com.Yash.Book_Store.Controller;

import com.Yash.Book_Store.DTO.AuthResponse;
import com.Yash.Book_Store.Entity.User;
import com.Yash.Book_Store.Service.JwtService;
import com.Yash.Book_Store.Service.User_Service;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Getter
@Setter
class AuthRequest{
    public String username;
    public String password;
}

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    User_Service userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public User registerUser(@RequestBody AuthRequest authRequest)
    {
        return userService.registerUser(authRequest.getUsername(), authRequest.getPassword());
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody AuthRequest authRequest) {
//        try {
//            // Attempt to register the user
//            userService.registerUser(authRequest.getUsername(), authRequest.getPassword());
//            return new ResponseEntity<>("User registered Successfully", HttpStatus.OK);
//
//        } catch (UserAlreadyExistsException e) {
//            // 1. Specific catch for a known error
//            // This gives the frontend a clear 409 Conflict status.
//            return ResponseEntity
//                    .status(HttpStatus.CONFLICT)
//                    .body("Username already exists");
//
//        } catch (Exception e) {
//            // 2. General "catch-all" for any other unexpected error
//            // It's a good practice to log the actual error for your own debugging.
//            System.err.println("An unexpected error occurred during registration: " + e.getMessage());
//
//            // This gives the frontend a generic 500 Internal Server Error status.
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Registration failed due to a server error.");
//        }
//    }

    @PostMapping("/login")
    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest)
    {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if(authentication.isAuthenticated())
        {
            String token = jwtService.generateToken(authRequest.getUsername());
            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(token);
            return authResponse;
        }
        else{
            throw new UsernameNotFoundException("Invalid user request");
        }
    }
}
