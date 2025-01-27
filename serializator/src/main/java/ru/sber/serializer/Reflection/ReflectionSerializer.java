package ru.sber.serializer.Reflection;

import ru.sber.serializer.JSONSerializator;
import ru.sber.serializer.JSONSerializatorFactoryHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectionSerializer<T> implements JSONSerializator<T> {
    private final List<Method> listOfFields;

    public ReflectionSerializer(Class<T> clazz) {
        JSONSerializatorFactoryHelper<T> helper = new JSONSerializatorFactoryHelper<>();
        this.listOfFields = helper.getListOfFields(clazz);
    }

    @Override
    public String serialize(T obj) {
        if (obj == null) return "{\n}";

        StringBuilder json = new StringBuilder();
        json.append("{\n");

        for (int i = 0; i < listOfFields.size(); i++) {
            Method method = listOfFields.get(i);
            String fieldName = JSONSerializatorFactoryHelper.getNameField(method);

            json.append("\t\"").append(fieldName).append("\": ");
            Object value;
            try {
                value = method.invoke(obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("IllegalAccessException caught in reflection serializer ", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("InvocationTargetException caught in reflection serializer ", e);
            }

            json.append(ToStringForJSON.toStringObj(value));

            if (i < listOfFields.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("}");
        return json.toString();
    }
}
