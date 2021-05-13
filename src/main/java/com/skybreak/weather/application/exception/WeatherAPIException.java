package com.skybreak.weather.application.exception;

import lombok.Getter;

@Getter
public class WeatherAPIException extends RuntimeException {

    private final String message;

    public WeatherAPIException(String message, Exception e) {
        super(message, e);
        this.message = message;
    }

}
