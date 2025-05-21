package ru.yandex.practicum.bliushtein.spr5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.controller.request.ChangeAmountInCartRequest;
import ru.yandex.practicum.bliushtein.spr5.controller.request.CreateItemRequest;
import ru.yandex.practicum.bliushtein.spr5.service.CartService;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;

import java.io.IOException;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;
    private final CartService cartService;
    private final Resource defaultImage;

    public ItemController(@Autowired ItemService itemService,
                          @Autowired CartService cartService,
                          @Value("classpath:image/default_image.png") Resource defaultImage) {
        this.itemService = itemService;
        this.cartService = cartService;
        this.defaultImage = defaultImage;
    }

    @GetMapping("/{id}")
    public Mono<Rendering> showItem(@PathVariable("id") Long id) {
        Rendering rendering = Rendering.view("item")
                        .modelAttribute("item", itemService.findItemById(id))
                .build();
        return Mono.just(rendering);
    }

    @PostMapping(value = "/{id}/changeAmountInCart")
    public Mono<Rendering> changeAmountInCart(ServerHttpRequest request,
                                              @PathVariable("id") Long id,
                                              ChangeAmountInCartRequest body) {
        Mono<Void> mono = switch (body.getAction()) {
            case "plus" -> cartService.increaseAmountInCart(id);
            case "minus" -> cartService.decreaseAmountInCart(id);
            case "delete" -> cartService.removeFromCart(id);
            default -> Mono.error(new ShopException("Incorrect action value %s".formatted(body.getAction())));
        };
        return mono.then(Mono.just(Rendering.redirectTo(request.getHeaders().getFirst("Referer")).build()));
    }

    @GetMapping("/new")
    public Mono<String> showNewItemPage() {
        return Mono.just("new-item");
    }

    @PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Mono<Rendering> createItem(CreateItemRequest body) {
        return filePartToBinary(body.getImage())
                .flatMap(file -> itemService.createItem(body.getName(), body.getDescription(), body.getPrice(), file))
                .map(item -> Rendering.redirectTo("/item/" + item.id()).build());
    }

    private Mono<byte[]> filePartToBinary(FilePart filePart) {
        return DataBufferUtils.join(filePart.content())
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return bytes;
                });
    }

    //To create test data quickly
    @GetMapping("/generate")
    public Mono<Rendering> generateItems() {
        return Flux.fromStream(IntStream.range(1, 20).boxed())
        .concatMap(this::createItem).then(Mono.just(Rendering.redirectTo("/main").build()));
    }

    private Mono<ItemDto> createItem(Integer i) {
        try {
            return itemService.createItem("name %d".formatted(i), "description %d".formatted(i), 30 - i,
                    defaultImage.getContentAsByteArray());
        } catch (IOException e) {
            return Mono.error(e);
        }
    }
}
