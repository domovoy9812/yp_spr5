package ru.yandex.practicum.bliushtein.spr5.service.mapper;

public interface Mapper<Entity, Dto> {
    Dto toDto(Entity entity);
}