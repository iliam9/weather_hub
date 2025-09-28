package com.i.weather_hub.exception;

// Базовое исключение для всех ошибок связанных с погодным сервисом
public class WeatherServiceException extends RuntimeException {
    public WeatherServiceException(String message) {
        super(message);
    }

    public WeatherServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}