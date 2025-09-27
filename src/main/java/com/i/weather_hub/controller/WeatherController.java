package com.i.weather_hub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/weather")
public class WeatherController {
    @GetMapping("/now")
    public Map<String, String> getCurrentWeather() {
        Map<String, String> weatherData = new HashMap<>();
        weatherData.put("temperature", "+15°C");
        weatherData.put("condition", "Cloudy");
        weatherData.put("location", "Moscow");
        return  weatherData;
    }
}
