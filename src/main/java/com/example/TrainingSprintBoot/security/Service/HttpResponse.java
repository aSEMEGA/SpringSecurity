package com.example.TrainingSprintBoot.security.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class HttpResponse {

    private Date date;
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;
}
