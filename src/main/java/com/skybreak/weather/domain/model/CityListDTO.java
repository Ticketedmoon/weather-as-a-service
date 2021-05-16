package com.skybreak.weather.domain.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class CityListDTO {

    private List<City> data;

    @Getter
    public static class City {
        private String city;

        public void setCity(String city) {
            this.city = StringUtils.capitalize(city.toLowerCase());
        }
    }
}
