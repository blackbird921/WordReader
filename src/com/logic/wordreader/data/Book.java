package com.logic.wordreader.data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: w
 * Date: 6/29/13
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Book {
    private BookInfo bookInfo;
    private List<Word> words;

    public BookInfo getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
