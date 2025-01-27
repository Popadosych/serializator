package ru.sber.serializer;

import org.junit.jupiter.api.*;
import ru.sber.serializer.CodeGenerator.CodeGeneratorSerializator;
import ru.sber.serializer.Reflection.ReflectionGeneratorSerializator;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TestSerializers {

    private Book book;
    JsonSerializatorFactory factory;
    private SimpleBook simpleBook;

    @BeforeEach
    public void setUp() {
        book = new Book("Effective Java", List.of(List.of(1, 2, 3), List.of(4, 5)), "Joshua Bloch".toCharArray(), 416,
                new TreeSet<>(Set.of("Programming", "Java")), new String[][]{{"Best Practices", "Java"}, {"aboba"}}, true, 'a');


        simpleBook = new SimpleBook("Effective Java", List.of(1, 2, 3), "Joshua Bloch".toCharArray(), 416,
                new TreeSet<>(Set.of("Programming", "Java")), new String[]{"Best Practices", "Java"}, true, 'a');

    }


    @Test
    public void testReflectionSer() {
        String correctAnswer = "{\n" +
                "\t\"array\": [[1, 2, 3], [4, 5]],\n" +
                "\t\"flag\": true,\n" +
                "\t\"tags\": [[\"Best Practices\", \"Java\"], [\"aboba\"]],\n" +
                "\t\"author\": [\"J\", \"o\", \"s\", \"h\", \"u\", \"a\", \" \", \"B\", \"l\", \"o\", \"c\", \"h\"],\n" +
                "\t\"title\": \"Effective Java\",\n" +
                "\t\"pages\": 416,\n" +
                "\t\"genres\": [\"Java\", \"Programming\"],\n" +
                "\t\"c\": \"a\"\n" +
                "}";

        factory = new ReflectionGeneratorSerializator();
        JSONSerializator<Book> refSer = factory.createSerializatorFor(Book.class);
        Assertions.assertEquals(correctAnswer, refSer.serialize(book));
    }


    @Test
    public void testCodeGeneratorSer() {
        String correctAnswer = "{\n" +
                "\t\"array\": [1, 2, 3],\n" +
                "\t\"flag\": true,\n" +
                "\t\"tags\": [\"Best Practices\", \"Java\"],\n" +
                "\t\"author\": [\"J\", \"o\", \"s\", \"h\", \"u\", \"a\", \" \", \"B\", \"l\", \"o\", \"c\", \"h\"],\n" +
                "\t\"title\": \"Effective Java\",\n" +
                "\t\"pages\": 416,\n" +
                "\t\"genres\": [\"Java\", \"Programming\"],\n" +
                "\t\"c\": \"a\"\n" +
                "}";
        factory = new CodeGeneratorSerializator();
        JSONSerializator<SimpleBook> codeGenSer = factory.createSerializatorFor(SimpleBook.class);
        String res = codeGenSer.serialize(simpleBook);
        Assertions.assertEquals(correctAnswer, res);
        //System.out.println(res);

        JSONSerializator<SimpleBook> newCodeGenSer = factory.createSerializatorFor(SimpleBook.class); //For Cached
        Assertions.assertEquals(correctAnswer, newCodeGenSer.serialize(simpleBook));
    }


}
