package com.i.weather_hub.controller;

import com.i.weather_hub.dto.WeatherResponseDto;
import com.i.weather_hub.service.WeatherApiService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherApiService weatherService;

    public WeatherController(WeatherApiService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/now")
    public Map<String, Object> getCurrentWeather(@RequestParam(required = false) String city) {
        WeatherResponseDto weatherData = weatherService.getCurrentWeather(city);

        Map<String, Object> response = new HashMap<>();
        response.put("temperature", Math.round(weatherData.getMain().getTemperature()) + "°C");
        response.put("condition", weatherData.getWeather()[0].getDescription());
        response.put("city", weatherData.getCityName());
        response.put("humidity", weatherData.getMain().getHumidity() + "%");

        return response;
    }
}