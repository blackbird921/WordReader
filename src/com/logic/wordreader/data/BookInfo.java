package com.logic.wordreader.data;

/**
 * Created by IntelliJ IDEA.
 * User: w
 * Date: 6/29/13
 * Time: 3:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class BookInfo {
    private String bookKey = "";
    private int grade;
    private int gradeTerm;

    public String getBookKey() {
        return bookKey;
    }

    public void setBookKey(String bookKey) {
        this.bookKey = bookKey;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGradeTerm() {
        return gradeTerm;
    }

    public void setGradeTerm(int gradeTerm) {
        this.gradeTerm = gradeTerm;
    }
}
