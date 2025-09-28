package com.i.weather_hub.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseDto {

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("main")
    private MainData main;

    @JsonProperty("weather")
    private Weather[] weather;

    // Вложенные классы для структуры JSON
    public static class MainData {
        @JsonProperty("temp")
        private double temperature;
        private int humidity;

        // Геттеры и сеттеры
        public double getTemperature() { return temperature; }
        public void setTemperature(double temperature) { this.temperature = temperature; }
        public int getHumidity() { return humidity; }
        public void setHumidity(int humidity) { this.humidity = humidity; }
    }

    public static class Weather {
        private String main;
        private String description;

        // Геттеры и сеттеры
        public String getMain() { return main; }
        public void setMain(String main) { this.main = main; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    // Геттеры и сеттеры для основного класса
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }
    public MainData getMain() { return main; }
    public void setMain(MainData main) { this.main = main; }
    public Weather[] getWeather() { return weather; }
    public void setWeather(Weather[] weather) { this.weather = weather; }
}