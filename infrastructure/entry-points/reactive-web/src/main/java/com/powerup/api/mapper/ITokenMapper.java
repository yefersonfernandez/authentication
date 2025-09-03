package com.powerup.api.mapper;


import com.powerup.api.dto.request.LoginRequestDto;
import com.powerup.api.dto.response.TokenResponseDto;
import com.powerup.model.token.Token;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ITokenMapper {
    TokenResponseDto toTokenResponseDto(Token token);
}
