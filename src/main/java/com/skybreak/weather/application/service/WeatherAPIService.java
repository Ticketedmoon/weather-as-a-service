package com.skybreak.weather.application.service;

import com.google.gson.JsonObject;
import com.skybreak.weather.application.exception.WeatherAPIException;
import com.skybreak.weather.domain.model.CityListDTO;
import com.skybreak.weather.domain.model.CityListDTO.City;
import com.skybreak.weather.domain.model.CountryDTO;
import com.skybreak.weather.domain.model.WeatherDTO;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherAPIService {

    private static final String APPLICATION_USER_AGENT_NAME = "weather-as-a-service";
    private static final String COUNTRY_API_BASE_URL = "http://battuta.medunes.net/api/country/all/?key=%s";
    private static final String CITY_API_URL = "https://countriesnow.space/api/v0.1/countries/population/cities/filter";

    private static final String WEATHER_API_BASE_URL = "http://api.weatherstack.com";
    private static final String WEATHER_API_CITY_QUERY_URL = WEATHER_API_BASE_URL + "/current?access_key=%s&query=%s";

    @Value("${country-city-api-access-key}")
    private String countryCityApiKey;

    @Value("${weather-api-access-key}")
    private String weatherApikey;

    private final RestTemplate restTemplate;

    public WeatherDTO getCurrentWeatherByCity(String city) {
        try {
            return restTemplate.getForObject(String.format(WEATHER_API_CITY_QUERY_URL, weatherApikey, city), WeatherDTO.class);
        } catch (RestClientException e) {
            throw new WeatherAPIException("Error when making API request - Please verify city name", e);
        }
    }

    public List<CountryDTO> getCountries() {
        try {
            CountryDTO[] countries = restTemplate.getForObject(String.format(COUNTRY_API_BASE_URL, countryCityApiKey), CountryDTO[].class);
            if (countries == null) {
                throw new WeatherAPIException("Country data lookup failed, verify lookup configuration");
            }
            return Arrays.stream(countries).collect(Collectors.toList());
        } catch (RestClientException e) {
            throw new WeatherAPIException("Error when making Countries look-up request", e);
        }
    }

    public List<City> getCitiesByCountry(String countryName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-agent", APPLICATION_USER_AGENT_NAME);

        JsonObject payload = new JsonObject();
        payload.addProperty("order", "asc");
        payload.addProperty("orderBy", "name");
        payload.addProperty("country", countryName);

        try {
            HttpEntity<String> request = new HttpEntity<>(payload.toString(), headers);
            CityListDTO cityList = restTemplate.postForObject(CITY_API_URL, request, CityListDTO.class);
            if (cityList == null) {
                throw new WeatherAPIException("City data lookup failed, verify lookup configuration");
            }
            return cityList.getData();
        } catch (RestClientException e) {
            throw new WeatherAPIException("Error when making Cities look-up request", e);
        }
    }

}
