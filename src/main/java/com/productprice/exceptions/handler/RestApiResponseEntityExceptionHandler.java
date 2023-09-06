package com.productprice.exceptions.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.productprice.dto.Notification;
import com.productprice.exceptions.BrandNotFoundException;
import com.productprice.exceptions.PriceNotFoundException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class RestApiResponseEntityExceptionHandler {

    //TODO: Implementar excepciones personalizadas en la capa service

    @ExceptionHandler({BrandNotFoundException.class, PriceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse personDataNotFound(BrandNotFoundException exception) {
        return ErrorResponse.builder()
                .notifications(List.of(Notification.builder().category("NOT_FOUND_ENTITY")
                        .code(HttpStatus.NOT_FOUND.value())
                        .description(exception.getMessage()).build()))
                .build();
    }

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    public static class ErrorResponse {

        private List<Notification> notifications;

    }
}
