package ru.yandex.practicum.bliushtein.spr5.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Image;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ImageRepository;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class ImageRepositoryTest extends AbstractJpaTestWithTestcontainers {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    ImageRepository imageRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void test_saveAndGet() {
        byte[] imageData = {1,2,3};
        Image savedImage = imageRepository.save(new Image(imageData));
        Optional<Image> image = imageRepository.findById(savedImage.getId());
        assertTrue(image.isPresent());
        assertArrayEquals(imageData, image.get().getImage());
    }
}
