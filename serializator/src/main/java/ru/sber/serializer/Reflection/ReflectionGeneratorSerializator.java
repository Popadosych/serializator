package ru.sber.serializer.Reflection;

import ru.sber.serializer.JSONSerializator;
import ru.sber.serializer.JsonSerializatorFactory;

public class ReflectionGeneratorSerializator implements JsonSerializatorFactory {
    @Override
    public <T> JSONSerializator<T> createSerializatorFor(Class<T> clazz) {
        return new ReflectionSerializer<>(clazz);
    }
}
