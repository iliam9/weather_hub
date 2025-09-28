package com.i.weather_hub.service;

import com.i.weather_hub.config.WeatherApiConfig;
import com.i.weather_hub.dto.WeatherResponseDto;
import com.i.weather_hub.exception.CityNotFoundException;
import com.i.weather_hub.exception.WeatherServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherApiService {

    private final WeatherApiConfig config;
    private final RestTemplate restTemplate;

    // Добавляем логгер - это как "дневник" где приложение записывает что происходит
    private static final Logger logger = LoggerFactory.getLogger(WeatherApiService.class);

    public WeatherApiService(WeatherApiConfig config) {
        this.config = config;
        this.restTemplate = new RestTemplate();
    }

    public WeatherResponseDto getCurrentWeather(String city) {
        // Если город не указан - используем город по умолчанию
        if (city == null || city.trim().isEmpty()) {
            city = config.getDefaultCity();
        }

        // Записываем в лог что мы начинаем запрос
        logger.info("Запрашиваем погоду для города: {}", city);

        try {
            // Строим URL для запроса
            String url = UriComponentsBuilder.fromHttpUrl(config.getApiUrl() + "/weather")
                    .queryParam("q", city)
                    .queryParam("appid", config.getApiKey())
                    .queryParam("units", "metric")
                    .queryParam("lang", "ru")
                    .toUriString();

            // Логируем URL (но скрываем API ключ для безопасности)
            logger.debug("Вызываем API: {}", url.replace(config.getApiKey(), "***"));

            // Делаем запрос к API
            WeatherResponseDto response = restTemplate.getForObject(url, WeatherResponseDto.class);

            // Проверяем что ответ не пустой
            if (response == null) {
                logger.error("API вернуло пустой ответ для города: {}", city);
                throw new WeatherServiceException("Погодный сервис вернул пустой ответ");
            }

            // Проверяем что в ответе есть основные данные
            if (response.getMain() == null || response.getWeather() == null || response.getWeather().length == 0) {
                logger.warn("Неполные данные от API для города: {}", city);
                // Можно продолжить работу, но записываем предупреждение
            }

            // Записываем успешное завершение
            logger.info("Успешно получили погоду для {}: {}°C",
                    response.getCityName(),
                    Math.round(response.getMain().getTemperature()));

            return response;

        } catch (HttpClientErrorException.NotFound e) {
            // Обрабатываем случай когда город не найден (ошибка 404)
            logger.warn("Город не найден в погодном сервисе: {}", city);
            throw new CityNotFoundException("Город '" + city + "' не найден в погодном сервисе");

        } catch (HttpClientErrorException.Unauthorized e) {
            // Обрабатываем неверный API ключ (ошибка 401)
            logger.error("Неверный API ключ для погодного сервиса");
            throw new WeatherServiceException("Ошибка аутентификации в погодном сервисе");

        } catch (Exception e) {
            // Обрабатываем все остальные ошибки (сеть, таймауты и т.д.)
            logger.error("Ошибка при запросе к погодному API: {}", e.getMessage());
            throw new WeatherServiceException("Погодный сервис временно недоступен", e);
        }
    }
}