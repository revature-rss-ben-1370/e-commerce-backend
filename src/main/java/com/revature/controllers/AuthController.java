package com.revature.controllers;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.ResetRequestPassword;
import com.revature.dtos.ResetRequestEmail;
import com.revature.exceptions.ExpiredRequestException;
import com.revature.models.User;
import com.revature.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://2205java-sre-p3.s3-website-us-east-1.amazonaws.com/", allowCredentials = "true")
public class AuthController{

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    /**
     * A route to request a password reset email be sent
     *
     * @param requestDTO A DTO ResetRequest that contains the email of the account to password reset
     * @return true - if the password reset request was sent. Do not use in the front end (for testing)
     */
    @PostMapping("/reset")
    public void passwordResetRequest(@RequestBody ResetRequestEmail requestDTO){
        authService.forgotPassword(requestDTO.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpSession session){
        Optional<User> optional = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());

        if (!optional.isPresent()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        session.setAttribute("user", optional.get());

        return ResponseEntity.status(HttpStatus.OK).body(optional.get());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session){
        session.removeAttribute("user");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest){
        User created = new User(0,
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                ""
        );

        if(authService.findByEmail(created.getEmail()).isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(created);

        created = authService.register(created);
        if (created.getId() > 0) return ResponseEntity.status(HttpStatus.CREATED).body(created);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(created);
    }
  
    @PatchMapping("/users/{requestId}")
    public User responseEntity(@RequestBody ResetRequestPassword password, @PathVariable("requestId") int id) throws ExpiredRequestException {
        return authService.resetPassword(password.getPassword(),id);
    }
}
