package com.skybreak.weather.application.service;

import com.google.gson.JsonObject;
import com.skybreak.weather.application.exception.WeatherAPIException;
import com.skybreak.weather.domain.model.CityListDTO;
import com.skybreak.weather.domain.model.CityListDTO.City;
import com.skybreak.weather.domain.model.CountryListDTO;
import com.skybreak.weather.domain.model.CountryListDTO.Country;
import com.skybreak.weather.domain.model.WeatherDTO;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherAPIService {

    private static final String APPLICATION_USER_AGENT_NAME = "weather-as-a-service";
    private static final String COUNTRY_API_BASE_URL = "https://countriesnow.space/api/v0.1/countries";
    private static final String CITY_API_URL = COUNTRY_API_BASE_URL + "/population/cities/filter";

    private static final String WEATHER_API_BASE_URL = "http://api.weatherstack.com";
    private static final String WEATHER_API_CITY_QUERY_URL = WEATHER_API_BASE_URL + "/current?access_key=%s&query=%s";

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

    public List<Country> getCountries() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("User-agent", APPLICATION_USER_AGENT_NAME);
            HttpEntity<HttpHeaders> headersEntity = new HttpEntity<>(headers);

            ResponseEntity<CountryListDTO> countriesResponse = restTemplate.exchange(COUNTRY_API_BASE_URL, HttpMethod.GET, headersEntity, CountryListDTO.class);
            CountryListDTO countries = Optional.ofNullable(countriesResponse.getBody())
                    .orElseThrow(() -> new WeatherAPIException("Country data lookup failed, verify lookup configuration"));
            return countries.getData();
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
            CityListDTO cityList = Optional.ofNullable(restTemplate.postForObject(CITY_API_URL, request, CityListDTO.class))
                    .orElseThrow(() -> new WeatherAPIException("City data lookup failed, verify lookup configuration"));
            return cityList.getData();
        } catch (RestClientException e) {
            throw new WeatherAPIException("Error when making Cities look-up request", e);
        }
    }

}
