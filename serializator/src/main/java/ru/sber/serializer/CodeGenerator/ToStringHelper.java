package ru.sber.serializer.CodeGenerator;

import java.util.Collection;

public class ToStringHelper {
    public static String toStringElem(Object element) {
        if (element == null) return "null";
        if (element instanceof Character || element instanceof String) {
            String str = element.toString();
            str = str.replace("\\", "\\\\")
                    .replace("\"", "\\\"");
            return "\"" + str + "\"";
        }
        return element.toString();
    }

    public static String classToString(Class<?> clazz) {
        StringBuilder ans = new StringBuilder();

        if (clazz.isArray()) {
            String className = clazz.getSimpleName();
            ans.append("\t\tjson.append(\"[\");\n")
                    .append("\t\t").append(className).append(" arr = (").append(className).append(") value;\n")
                    .append("\t\tfor (int i = 0; i < arr.length; i++) {\n")
                    .append("\t\t\tjson.append(ToStringHelper.toStringElem(arr[i]));\n")
                    .append("\t\t\tif (i < arr.length - 1) {\n")
                    .append("\t\t\t\tjson.append(\", \");\n")
                    .append("\t\t\t}\n")
                    .append("\t\t}\n")
                    .append("\t\tjson.append(\"]\");");
        } else if (Collection.class.isAssignableFrom(clazz)) {
            ans.append("\t\tjson.append(\"[\");\n")
                    .append("\t\tCollection<?> collection = (Collection<?>) value;\n")
                    .append("\t\tIterator<?> iterator = collection.iterator();\n")
                    .append("\t\twhile (iterator.hasNext()) {\n")
                    .append("\t\t\tjson.append(ToStringHelper.toStringElem(iterator.next()));\n")
                    .append("\t\t\tif (iterator.hasNext()) {\n")
                    .append("\t\t\t\tjson.append(\", \");\n")
                    .append("\t\t\t}\n")
                    .append("\t\t}\n")
                    .append("\t\tjson.append(\"]\");");
        } else {
            ans.append("\t\tjson.append(ToStringHelper.toStringElem(value));\n");
        }
        return ans.toString();
    }
}
