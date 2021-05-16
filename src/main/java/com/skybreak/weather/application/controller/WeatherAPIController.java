package com.skybreak.weather.application.controller;

import com.skybreak.weather.application.service.WeatherAPIService;
import com.skybreak.weather.domain.model.CityListDTO.City;
import com.skybreak.weather.domain.model.CountryDTO;
import com.skybreak.weather.domain.model.WeatherDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WeatherAPIController {

    private final WeatherAPIService weatherAPIService;

    @GetMapping("/weather")
    public WeatherDTO getCurrentWeatherByCity(@RequestParam String city) {
        return weatherAPIService.getCurrentWeatherByCity(city);
    }

    @GetMapping("/countries")
    public List<CountryDTO> getCountries() {
        return weatherAPIService.getCountries();
    }

    @GetMapping("/cities")
    public List<City> getCitiesByCountry(@RequestParam String country) {
        return weatherAPIService.getCitiesByCountry(country);
    }
}
