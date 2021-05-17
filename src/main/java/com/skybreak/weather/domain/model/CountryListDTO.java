package com.skybreak.weather.domain.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryListDTO {

    private List<Country> data;

    @Getter
    @Setter
    public static class Country {
        private String country;
    }
}
