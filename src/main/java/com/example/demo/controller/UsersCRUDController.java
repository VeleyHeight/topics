package com.example.demo.controller;

import com.example.demo.dto.UsersDTORequest;
import com.example.demo.dto.UsersDTOResponse;
import com.example.demo.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UsersCRUDController {
    private final UsersService usersService;

    @GetMapping
    public ResponseEntity<PagedModel<UsersDTOResponse>> getAllUsers(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok().body(new PagedModel<>(usersService.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTOResponse> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok().body(usersService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UsersDTOResponse> createUser(@Valid @RequestBody UsersDTORequest usersDTORequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.save(usersDTORequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@Valid @PathVariable Integer id) {
        usersService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
