package ru.yandex.practicum.bliushtein.spr5.service;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import ru.yandex.practicum.bliushtein.spr5.service.dto.CartDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.PagedItemsDto;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@AutoConfiguration
public class ServiceAutoConfiguration {
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
