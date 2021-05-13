package com.skybreak.weather.application.service;

import com.skybreak.weather.application.exception.WeatherAPIException;
import com.skybreak.weather.domain.model.WeatherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherAPIService {

    private static final String WEATHER_API_BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String WEATHER_API_CITY_QUERY_URL = WEATHER_API_BASE_URL + "?q=%s&appid=%s";

    @Value("weather-api-key")
    private final String weatherApikey;

    private final RestTemplate restTemplate;

    public WeatherDTO getCurrentWeatherByCity(String city) {
        try {
            return restTemplate.getForObject(String.format(WEATHER_API_CITY_QUERY_URL, city, weatherApikey), WeatherDTO.class);
        } catch (RestClientException e) {
            throw new WeatherAPIException("Error when making API request - Please verify city name", e);
        }
    }

}
