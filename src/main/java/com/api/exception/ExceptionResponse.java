package com.api.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponse {
    private final Integer status;
    private final String detail;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ExceptionResponse(Integer status, String detail) {
        this.status = status;
        this.detail = detail;
    }
}