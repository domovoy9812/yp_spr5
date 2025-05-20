package ru.yandex.practicum.bliushtein.spr5.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.bliushtein.spr5.service.ImageService;

import java.io.IOException;

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
    public void getImage(HttpServletResponse response,
                         @PathVariable("key") Long key) throws IOException {
        response.setHeader("content-type", "image/jpg");
        if (key == null) {
            return;
        }
        try (var output = response.getOutputStream()) {
            byte[] image = imageService.getImage(key).block();
            output.write(image);
        }
    }

    @GetMapping
    public void getImage(HttpServletResponse response) throws IOException {
        response.setHeader("content-type", "image/jpg");
        try (var output = response.getOutputStream()) {
            defaultImage.getInputStream().transferTo(output);
        }
    }
}
