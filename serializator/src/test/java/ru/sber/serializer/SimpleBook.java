package ru.sber.serializer;


import java.util.List;
import java.util.Set;

public class SimpleBook {
    private final String title;
    private final List<Integer> array;
    private final char[] author;
    private final int pages;
    private final Set<String> genres;
    private final String[] tags;
    private final boolean flag;
    private final char c;

    public SimpleBook(String title, List<Integer> array, char[] author, int pages, Set<String> genres, String[] tags, boolean flag, char c) {
        this.title = title;
        this.array = array;
        this.author = author;
        this.pages = pages;
        this.genres = genres;
        this.tags = tags;
        this.flag = flag;
        this.c = c;
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getArray() {
        return array;
    }

    public char[] getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public String[] getTags() {
        return tags;
    }

    public boolean isFlag() {
        return flag;
    }

    public char getC() {
        return c;
    }
}
