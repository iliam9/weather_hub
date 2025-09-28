package com.i.weather_hub.service;

import com.i.weather_hub.config.WeatherApiConfig;
import com.i.weather_hub.dto.WeatherResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherApiService {

    private final WeatherApiConfig config;
    private final RestTemplate restTemplate;

    public WeatherApiService(WeatherApiConfig config) {
        this.config = config;
        this.restTemplate = new RestTemplate();
    }

    public WeatherResponseDto getCurrentWeather(String city) {
        if (city == null || city.trim().isEmpty()) {
            city = config.getDefaultCity();
        }

        String url = UriComponentsBuilder.fromHttpUrl(config.getApiUrl() + "/weather")
                .queryParam("q", city)
                .queryParam("appid", config.getApiKey())
                .queryParam("units", "metric") // Градусы Цельсия
                .queryParam("lang", "ru")      // Русский язык
                .toUriString();

        return restTemplate.getForObject(url, WeatherResponseDto.class);
    }
}