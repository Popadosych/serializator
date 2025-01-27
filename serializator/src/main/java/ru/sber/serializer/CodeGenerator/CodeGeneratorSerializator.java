package ru.sber.serializer.CodeGenerator;

import net.openhft.compiler.CompilerUtils;
import ru.sber.serializer.JSONSerializator;
import ru.sber.serializer.JSONSerializatorFactoryHelper;
import ru.sber.serializer.JsonSerializatorFactory;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGeneratorSerializator implements JsonSerializatorFactory {
    private final Map<Type, Object> cache = new HashMap<>();

    @Override
    public <T> JSONSerializator<T> createSerializatorFor(Class<T> clazz) {
        return (JSONSerializator<T>) cache.computeIfAbsent(clazz, (key) -> {
            String className = clazz.getSimpleName() + "JsonSerializer";
            String code = GenerateCode(clazz);
            Class<?> aClass;
            try {
                aClass = CompilerUtils.CACHED_COMPILER.loadFromJava("ru.sber.serializer.CodeGenerator." + className + "." + className, code);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("ClassNotFoundException caught in Code Generator");
            }

            JSONSerializator<T> serializator;
            try {
                serializator = (JSONSerializator<T>) aClass.getConstructor().newInstance();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Not found constructor of " + className);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("InvocationTargetException caught in Code Generator");
            } catch (InstantiationException e) {
                throw new RuntimeException("InstantiationException caught in Code Generator");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("IllegalAccessException caught in Code Generator");
            }

            return serializator;
        });
    }

    private <T> String GenerateCode(Class<T> clazz) {
        String className = clazz.getSimpleName() + "JsonSerializer";
        StringBuilder code = new StringBuilder();

        code.append("package ru.sber.serializer.CodeGenerator.").append(className).append(";\n");
        code.append("import ru.sber.serializer.JSONSerializator;\n");
        code.append("import ru.sber.serializer.CodeGenerator.ToStringHelper;\n");
        code.append("import java.util.Collection;\n");
        code.append("import java.util.Iterator;\n");
        code.append("public class ").append(className).append("<T> implements JSONSerializator<T> {\n");
        code.append("\t@Override");
        code.append("\tpublic String serialize(Object obj) {\n");
        code.append("\tif (obj == null) return \"{\\n}\";");
        code.append("\t\t").append(clazz.getName()).append(" target = (").append(clazz.getName()).append(") obj;\n");
        code.append("\t\tStringBuilder json = new StringBuilder();\n");
        code.append("\t\tjson.append(\"{\\n\");\n");

        JSONSerializatorFactoryHelper<T> helper = new JSONSerializatorFactoryHelper<>();

        List<Method> getters = helper.getListOfFields(clazz);

        for (int i = 0; i < getters.size(); ++i) {
            Method method = getters.get(i);
            String fieldName = JSONSerializatorFactoryHelper.getNameField(method);
            Class<?> typeOfGetter = method.getReturnType();
            code.append("\t\tjson.append(\"\\t\\\"").append(fieldName).append("\\\": \");\n");
            if (typeOfGetter == String.class || (typeOfGetter).isPrimitive() ||
                    Collection.class.isAssignableFrom(typeOfGetter) ||
                    typeOfGetter.isArray()) {
                code.append("{\n"); // new scopes for renaming
                code.append("\t\tObject value = target.").append(method.getName()).append("();\n");
                code.append(ToStringHelper.classToString(typeOfGetter));
                code.append("}\n");
                if (i != getters.size() - 1) {
                    code.append("\t\tjson.append(\",\");\n");
                }
                code.append("\t\tjson.append(\"\\n\");\n");
            }
        }

        code.append("\t\tjson.append(\"}\");\n");
        code.append("\t\treturn json.toString();\n");
        code.append("\t}\n");
        code.append("}\n");

        //System.out.println(code);
        return code.toString();
    }
}
