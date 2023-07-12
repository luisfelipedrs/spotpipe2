package com.pipe.spotpipe2.infra.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExceptionResponse {

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
    private Integer status;
    private String path;
    private List<String> messages = new ArrayList<>();

    public ExceptionResponse(Integer status, String path) {
        this.status = status;
        this.path = path;
    }

    public void addError(String message) {
        messages.add(message);
    }

    public void addError(String field, String message) {
        String errorMessage = String.format("field %s %s", field, message);
        messages.add(errorMessage);
    }

    public void addError(FieldError fieldError) {
        String errorMessage = String.format("field %s %s", fieldError.getField(), fieldError.getDefaultMessage());
        messages.add(errorMessage);
    }
}
