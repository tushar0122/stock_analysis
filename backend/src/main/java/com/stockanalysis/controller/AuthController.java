package com.stockanalysis.controller;

import com.stockanalysis.config.JwtTokenProvider;
import com.stockanalysis.dto.LoginRequest;
import com.stockanalysis.dto.LoginResponse;
import com.stockanalysis.dto.UserDTO;
import com.stockanalysis.repository.UserRepository;
import com.stockanalysis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtTokenProvider.generateToken(request.getUsername());
        UserDTO user = userService.findByUsername(request.getUsername());

        return LoginResponse.builder()
                .token(token)
                .user(user)
                .build();
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody LoginRequest request) {
        return userService.register(request.getUsername(), request.getUsername() + "@example.com", request.getPassword());
    }

    @GetMapping("/verify")
    public UserDTO verify() {
        String username = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getName();
        return userService.findByUsername(username);
    }
}
