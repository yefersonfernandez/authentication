package com.powerup.api.openapi;

import com.powerup.api.dto.error.CustomError;
import com.powerup.api.dto.request.UserRequestDto;
import com.powerup.api.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.experimental.UtilityClass;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

@UtilityClass
public class UserOpenApi {

    private final String TAG = "User";
    private final String SUCCESS = "Success";
    private final String SUCCESS_CODE = String.valueOf(HttpStatus.OK.value());
    private final String BAD_REQUEST = HttpStatus.BAD_REQUEST.getReasonPhrase();
    private final String BAD_REQUEST_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private final String CONFLICT = HttpStatus.CONFLICT.getReasonPhrase();
    private final String CONFLICT_CODE = String.valueOf(HttpStatus.CONFLICT.value());
    private final String NOT_FOUND = HttpStatus.NOT_FOUND.getReasonPhrase();
    private final String NOT_FOUND_CODE = String.valueOf(HttpStatus.NOT_FOUND.value());

    public Builder saveUser(Builder builder) {
        return builder
                .operationId("saveUser")
                .description("Registers a new user after validating unique email and salary range")
                .tag(TAG)
                .requestBody(requestBodyBuilder()
                        .required(true)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(UserRequestDto.class))))
                .response(responseBuilder().responseCode(SUCCESS_CODE).description("User registered successfully")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(UserResponseDto.class))))
                .response(responseBuilder().responseCode(BAD_REQUEST_CODE).description(BAD_REQUEST)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(CustomError.class))))
                .response(responseBuilder().responseCode(CONFLICT_CODE).description(CONFLICT)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(CustomError.class))));
    }

    public Builder findUserByIdentityDocument(Builder builder) {
        return builder
                .operationId("findUserByIdentityDocument")
                .description("Retrieves a user by identity document")
                .tag(TAG)
                .parameter(parameterBuilder()
                        .name("identityDocument")
                        .description("Identity document of the user")
                        .required(true)
                        .in(io.swagger.v3.oas.annotations.enums.ParameterIn.PATH)
                        .schema(schemaBuilder().implementation(String.class)))
                .response(responseBuilder().responseCode(SUCCESS_CODE).description(SUCCESS)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(UserResponseDto.class))))
                .response(responseBuilder().responseCode(NOT_FOUND_CODE).description(NOT_FOUND)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(CustomError.class))))
                .response(responseBuilder().responseCode(BAD_REQUEST_CODE).description(BAD_REQUEST)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(CustomError.class))));
    }
}