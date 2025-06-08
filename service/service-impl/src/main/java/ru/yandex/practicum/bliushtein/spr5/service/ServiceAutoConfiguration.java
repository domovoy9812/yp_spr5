package ru.yandex.practicum.bliushtein.spr5.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.bliushtein.spr5.service.client.ApiClient;
import ru.yandex.practicum.bliushtein.spr5.service.client.api.DefaultApi;
import ru.yandex.practicum.bliushtein.spr5.service.dto.CartDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.PagedItemsDto;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@AutoConfiguration
public class ServiceAutoConfiguration {
    @Bean
    ApiClient apiClient(WebClient.Builder builder,
                        @Value("${payment-api-url}") String paymentServiceUrl) {
        var apiClient = new ApiClient(builder.build());
        apiClient.setBasePath(paymentServiceUrl);
        return apiClient;
    }

    @Bean
    DefaultApi paymentApi(ApiClient apiClient) {
        return new DefaultApi(apiClient);
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer weatherCacheCustomizer() {
        return builder -> builder.withCacheConfiguration(
                "items",
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.of(1, ChronoUnit.MINUTES))
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new Jackson2JsonRedisSerializer<>(ItemDto.class)
                                )
                        )
        ).withCacheConfiguration(
                "itemsSearchResult",
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.of(1, ChronoUnit.MINUTES))
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new Jackson2JsonRedisSerializer<>(PagedItemsDto.class)
                                )
                        )
        ).withCacheConfiguration(
                "cart",
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.of(1, ChronoUnit.MINUTES))
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new Jackson2JsonRedisSerializer<>(CartDto.class)
                                )
                        )
        );
    }
}
