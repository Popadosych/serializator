package ru.sber.serializer;

import java.util.List;
import java.util.Set;

public class Book {
    private final String title;
    private final List<List<Integer>> array;
    private final char[] author;
    private final int pages;
    private final Set<String> genres;
    private final String[][] tags;
    private final boolean flag;
    private final char c;

    public Book(String title, List<List<Integer>> array, char[] author, int pages, Set<String> genres, String[][] tags, boolean flag, char c) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.genres = genres;
        this.tags = tags;
        this.flag = flag;
        this.c = c;
        this.array = array;
    }

    public char[] getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getPages() {
        return pages;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public String[][] getTags() {
        return tags;
    }

    public boolean isFlag() {
        return flag;
    }

    public char getC() {
        return c;
    }

    public List<List<Integer>> getArray() {
        return array;
    }
}
