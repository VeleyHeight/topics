package com.example.demo.service.impl;

import com.example.demo.dto.UsersDTORequest;
import com.example.demo.dto.UsersDTOResponse;
import com.example.demo.mapstruct.UsersMapper;
import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService
//        , UserDetailsService
{

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return usersRepository.findByUsername(username).map(users -> {
//            log.info("User roles for {}: {}", username, users.getRoles());
//            return new User(users.getUsername(),
//                    users.getPassword(), Collections.singleton(users.getRoles()));
//        }).orElseThrow(
//                () -> new UsernameNotFoundException("Failed to find user with username: " + username));
//    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsersDTOResponse> findAll(Pageable pageable) {
        return usersRepository.findAll(pageable).map(usersMapper::toDTO);
    }

    @Override
    @Transactional
    public UsersDTOResponse save(UsersDTORequest users) {
        log.info("Saving users: {}", users);
        if (usersRepository.existsByUsername(users.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        var usersSave = usersMapper.toUsers(users);
        usersSave.setPassword(passwordEncoder.encode(usersSave.getPassword()));
        var savedUser = usersRepository.save(usersSave);
        log.info("Saved user: {}", savedUser);
        return usersMapper.toDTO(savedUser);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        usersRepository.findById(id).ifPresent(usersRepository::delete);
    }


    @Override
    @Transactional(readOnly = true)
    public UsersDTOResponse findById(Integer id) {
        log.info("Finding user by id: {}", id);
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id must not be null");
        }
        var users = usersRepository.findById(id).orElse(null);
        if (users == null) {
            log.info("User with id {} not found", id);
        } else {
            log.info("User with id {} found", id);
        }
        return usersMapper.toDTO(users);
    }

    @Override
    public boolean existsByUsername(String username) {
        return usersRepository.existsByUsername(username);
    }
}
