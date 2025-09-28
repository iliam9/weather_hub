package com.i.weather_hub.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Говорим Spring: "Этот класс обрабатывает ВСЕ исключения во ВСЕХ контроллерах"
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Логгер для этого класса
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Говорим: "Этот метод обрабатывает исключения CityNotFoundException"
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCityNotFound(CityNotFoundException e) {
        // Записываем предупреждение в лог
        logger.warn("Обрабатываем CityNotFoundException: {}", e.getMessage());

        // Создаем красивый JSON ответ для клиента
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", true);
        errorResponse.put("errorCode", "CITY_NOT_FOUND");
        errorResponse.put("message", e.getMessage());
        errorResponse.put("timestamp", System.currentTimeMillis());

        // Возвращаем HTTP статус 404 (Not Found) и наш JSON
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Обрабатываем все остальные WeatherServiceException
    @ExceptionHandler(WeatherServiceException.class)
    public ResponseEntity<Map<String, Object>> handleWeatherServiceError(WeatherServiceException e) {
        // Записываем ошибку в лог
        logger.error("Обрабатываем WeatherServiceException: {}", e.getMessage());

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", true);
        errorResponse.put("errorCode", "WEATHER_SERVICE_ERROR");
        errorResponse.put("message", e.getMessage());
        errorResponse.put("timestamp", System.currentTimeMillis());

        // Возвращаем HTTP статус 503 (Service Unavailable)
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }

    // Обрабатываем ВСЕ остальные непредвиденные исключения
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception e) {
        // Записываем критическую ошибку (с полным stack trace)
        logger.error("Непредвиденная ошибка: {}", e.getMessage(), e);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", true);
        errorResponse.put("errorCode", "INTERNAL_SERVER_ERROR");
        errorResponse.put("message", "Внутренняя ошибка сервера");
        errorResponse.put("timestamp", System.currentTimeMillis());

        // Возвращаем HTTP статус 500 (Internal Server Error)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}