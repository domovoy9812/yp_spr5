package ru.yandex.practicum.bliushtein.spr5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.service.ImageService;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Value("classpath:image/default_image.png")
    Resource defaultImage;

    private final ImageService imageService;

    public ImageController(@Autowired ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{key}")
    @ResponseBody
    public ResponseEntity<Mono<byte[]>> getImage(@PathVariable("key") Long key) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageService.getImage(key));
    }

    @GetMapping
    public ResponseEntity<Flux<DataBuffer>> getImage() {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(DataBufferUtils.read(defaultImage, new DefaultDataBufferFactory(), 1000));
    }
}
