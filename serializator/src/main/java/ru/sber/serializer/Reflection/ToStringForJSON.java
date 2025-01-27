package ru.sber.serializer.Reflection;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

public class ToStringForJSON {
    public static String toStringObj(Object value) {
        if (value instanceof Collection) {
            return toStringCollection((Collection<?>) value);
        } else if (value.getClass().isArray())  {
            return toStringArray(value);
        }
        return toStringElem(value);
    }

    public static String toStringElem(Object element) {
        if (element == null) return "null";
        if (element instanceof String || element instanceof Character) {
            String str = element.toString();
            str = str.replace("\\", "\\\\")
                    .replace("\"", "\\\"");
            return "\"" + str + "\"";
        }
        return element.toString();
    }

    public static String toStringCollection(Collection<?> collection) {
        Iterator<?> iterator = collection.iterator();
        StringBuilder json = new StringBuilder();
        json.append("[");
        while (iterator.hasNext()) {
            json.append(toStringObj(iterator.next()));
            if (iterator.hasNext()) {
                json.append(", ");
            }
        }
        json.append("]");
        return json.toString();
    }

    public static String toStringArray(Object array) {
        int length = Array.getLength(array);
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < length; i++) {
            json.append(toStringObj(Array.get(array, i)));
            if (i < length - 1) {
                json.append(", ");
            }
        }
        json.append("]");
        return json.toString();
    }
}
