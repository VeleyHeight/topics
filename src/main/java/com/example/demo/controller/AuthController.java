package com.example.demo.controller;

//import com.example.demo.JWT.JWTCore;
import com.example.demo.dto.JWTRequest;
import com.example.demo.dto.JWTResponse;
import com.example.demo.dto.UsersDTORequest;
import com.example.demo.service.UsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;

//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//    private final JWTCore jwtCore;
//    private final UsersService usersService;
//    private final AuthenticationManager authenticationManager;
//
//    @PostMapping("/login")
//    ResponseEntity<?> login(@RequestBody JWTRequest authRequest) {
//        Authentication authentication = null;
//        try {
//            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    authRequest.username(),
//                    authRequest.password()
//            ));
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
//        }
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = jwtCore.generateToken(authentication);
//        return ResponseEntity.ok().body(new JWTResponse(token));
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> signup(@RequestBody @Valid UsersDTORequest usersDTORequest) {
//        return ResponseEntity.ok().body(usersService.save(usersDTORequest));
//    }
//}
