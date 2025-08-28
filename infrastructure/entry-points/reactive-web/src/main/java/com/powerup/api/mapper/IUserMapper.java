package com.powerup.api.mapper;

import com.powerup.api.dto.request.UserRequestDto;
import com.powerup.api.dto.response.UserResponseDto;
import com.powerup.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IUserMapper {
    UserResponseDto toUserResponseDto(User user);
    User toModel(UserRequestDto userRequestDto);
}
