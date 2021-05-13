package com.skybreak.weather.application.exception.handler;

import com.skybreak.weather.application.exception.WeatherAPIException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class WeatherExceptionHandler {

    private static final String GENERAL_API_EXCEPTION = "Something went wrong when sending API request";

    @ExceptionHandler(value = WeatherAPIException.class)
    public ResponseEntity<Object> handleWeatherApiException(WeatherAPIException e) {
        log.error("Invalid Weather API Exception", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception e) {
        log.error("Invalid General Exception", e);
        return new ResponseEntity<>(GENERAL_API_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
