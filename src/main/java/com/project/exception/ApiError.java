package com.project.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> errors;

}