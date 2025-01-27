package ru.sber.serializer;

public interface JSONSerializator<T> {
    public String serialize(T obj);
}

