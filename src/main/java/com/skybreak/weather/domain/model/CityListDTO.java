package com.skybreak.weather.domain.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityListDTO {

    private List<City> data;

    @Getter
    @Setter
    public static class City {
        private String city;
    }
}
