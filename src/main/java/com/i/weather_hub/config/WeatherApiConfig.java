package com.i.weather_hub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherApiConfig {

    /*Spring подставит значения из application.properties*/
    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.city}")
    private String defaultCity;

    // Геттеры
    public String getApiKey() { return apiKey; }
    public String getApiUrl() { return apiUrl; }
    public String getDefaultCity() { return defaultCity; }
}
