package com.logic.wordreader.data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: w
 * Date: 6/29/13
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Word {
    private int id;
    private List<ZiWithPy> ziWithPys;
    private int chapter;
    private int page;
    private int level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ZiWithPy> getZiWithPys() {
        return ziWithPys;
    }

    public void setZiWithPys(List<ZiWithPy> ziWithPys) {
        this.ziWithPys = ziWithPys;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
		String chinese = "";
		for (ZiWithPy zi : getZiWithPys()) {
			chinese += zi.getChinese();
		}
        return chinese; 
    }
}
