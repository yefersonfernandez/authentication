package com.powerup.api.openapi;

import com.powerup.api.dto.error.CustomError;
import com.powerup.api.dto.request.LoginRequestDto;
import com.powerup.api.dto.response.TokenResponseDto;
import lombok.experimental.UtilityClass;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

@UtilityClass
public class LoginOpenApi {

    private final String TAG = "Login";
    private final String SUCCESS_CODE = String.valueOf(HttpStatus.OK.value());
    private final String BAD_REQUEST = HttpStatus.BAD_REQUEST.getReasonPhrase();
    private final String BAD_REQUEST_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private final String UNAUTHORIZED = HttpStatus.UNAUTHORIZED.getReasonPhrase();
    private final String UNAUTHORIZED_CODE = String.valueOf(HttpStatus.UNAUTHORIZED.value());

    public Builder login(Builder builder) {
        return builder
                .operationId("login")
                .description("Authenticates a user and returns an access token")
                .tag(TAG)
                .requestBody(requestBodyBuilder()
                        .required(true)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(LoginRequestDto.class))))
                .response(responseBuilder().responseCode(SUCCESS_CODE)
                        .description("Login successful, access token returned")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(TokenResponseDto.class))))
                .response(responseBuilder().responseCode(BAD_REQUEST_CODE)
                        .description(BAD_REQUEST)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(CustomError.class))))
                .response(responseBuilder().responseCode(UNAUTHORIZED_CODE)
                        .description(UNAUTHORIZED)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(CustomError.class))));
    }
}
