package ru.sber.serializer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JSONSerializatorFactoryHelper<T> {

    public List<Method> getListOfFields(Class<T> clazz) {
        List<Method> gettersList = new ArrayList<>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (!(method.getName().equals("getClass")) && isGetter(method)) {
                gettersList.add(method);
            }
        }

        return gettersList;
    }

    public static boolean isGetter(Method method) {
        if (method.getReturnType() == void.class || method.getParameterCount() != 0) {
            return false;
        }
        String methodName = method.getName();

        if (method.getName().startsWith("get")) {
            String propertyName = methodName.substring(3);
            if (propertyName.isEmpty()) {
                return false;
            }
            char firstChar = propertyName.charAt(0);
            return Character.isUpperCase(firstChar);
        }
        if (method.getName().startsWith("is")) {
            if (!(method.getReturnType() == boolean.class)) {
                return false;
            }
            String propertyName = methodName.substring(2);
            if (propertyName.isEmpty()) {
                return false;
            }
            char firstChar = propertyName.charAt(0);
            return Character.isUpperCase(firstChar);
        }
        return false;
    }

    public static String getNameField(Method method) {
        String methodName = method.getName();

        String propertyName = "";
        if (methodName.startsWith("get")) {
            propertyName = methodName.substring(3);
        } else if (methodName.startsWith("is")) {
            propertyName = methodName.substring(2);
        }

        return Character.toLowerCase(propertyName.charAt(0)) +
                (propertyName.length() > 1 ? propertyName.substring(1) : "");
    }
}
