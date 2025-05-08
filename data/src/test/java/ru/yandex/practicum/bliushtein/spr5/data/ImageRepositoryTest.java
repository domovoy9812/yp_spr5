package ru.yandex.practicum.bliushtein.spr5.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Image;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ImageRepository;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class ImageRepositoryTest extends AbstractJpaTestWithTestcontainers {

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
