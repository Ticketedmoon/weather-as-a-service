package com.skybreak.weather.application.controller;

import com.skybreak.weather.application.service.WeatherAPIService;
import com.skybreak.weather.domain.model.WeatherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherAPIController {

    private final WeatherAPIService weatherAPIService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/api/weather")
    public WeatherDTO getCurrentWeatherByCity(@RequestParam String city) {
        return weatherAPIService.getCurrentWeatherByCity(city);
    }

}
