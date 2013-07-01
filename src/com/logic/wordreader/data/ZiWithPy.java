package com.logic.wordreader.data;

/**
 * Created by IntelliJ IDEA.
 * User: w
 * Date: 6/29/13
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZiWithPy {
    private String chinese;
    private String pinyinUtf8="";
    private String pinyinNumber="";

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getPinyinUtf8() {
        return pinyinUtf8;
    }

    public void setPinyinUtf8(String pinyinUtf8) {
        this.pinyinUtf8 = pinyinUtf8;
    }

    public String getPinyinNumber() {
        return pinyinNumber;
    }

    public void setPinyinNumber(String pinyinNumber) {
        this.pinyinNumber = pinyinNumber;
    }

    @Override
    public String toString() {
        return "ZiWithPy{" +
                "chinese='" + chinese + '\'' +
                ", pinyinUtf8='" + pinyinUtf8 + '\'' +
                ", pinyinNumber='" + pinyinNumber + '\'' +
                '}';
    }
}
