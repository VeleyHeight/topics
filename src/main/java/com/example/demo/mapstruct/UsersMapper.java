package com.example.demo.mapstruct;

import com.example.demo.dto.UsersDTORequest;
import com.example.demo.dto.UsersDTOResponse;
import com.example.demo.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.sql.Timestamp;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {Timestamp.class})
public interface UsersMapper {

    UsersDTOResponse toDTO(Users users);

    Users toUsers(UsersDTORequest usersDTORequest);

}
