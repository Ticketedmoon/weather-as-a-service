package com.skybreak.weather.domain.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDTO {

    private WeatherDetails current;

    @Getter
    @Setter
    private static class WeatherDetails {
        private String observation_time;
        private Integer temperature;
        private List<String> weather_icons;
        private List<String> weather_descriptions;
        private Integer wind_speed;
        private Integer wind_degree;
        private String wind_dir;
        private Integer pressure;
        private Integer precip;
        private Integer humidity;
        private Integer cloudcover;
        private Integer uv_index;
        private Integer visibility;
        private String is_day;
    }
}