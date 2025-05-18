package com.example.demo.service;

import com.example.demo.dto.TopicsDTO;
import com.example.demo.dto.UsersDTORequest;
import com.example.demo.dto.UsersDTOResponse;
import com.example.demo.filter.TopicsFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsersService {
    Page<UsersDTOResponse> findAll(Pageable pageable);

    UsersDTOResponse save(UsersDTORequest users);

    void delete(Integer id);

    UsersDTOResponse findById(Integer id);
}
