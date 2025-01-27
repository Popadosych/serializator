package ru.sber.serializer;

public interface JsonSerializatorFactory {
    <T> JSONSerializator<T> createSerializatorFor(Class<T> clazz);
}
