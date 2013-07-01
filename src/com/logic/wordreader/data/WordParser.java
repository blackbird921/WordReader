package com.logic.wordreader.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordParser {
    public static List<Word> getByChapterId(int chapter, List<Word> myWords) {
        List<Word> result = new ArrayList<Word>();
        for (Word word : myWords) {
            if (word.getChapter() == chapter) {
                result.add(word);
            }
        }
        return result;
    }

    public static List<Word> getByPageId(int page, List<Word> myWords) {
        List<Word> result = new ArrayList<Word>();
        for (Word word : myWords) {
            if (word.getPage() == page) {
                result.add(word);
            }
        }
        return result;
    }

    public static List<Word> getAllWords(String fileName){
        BufferedReader fileBuffer = null;
        try {
            fileBuffer = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return getAllWords(fileBuffer);
    }

    public static List<Word> getAllWords(BufferedReader fileBuffer){
        List<Word> result = new ArrayList<Word>();
        try {
            String dataRow = fileBuffer.readLine(); // Read first line.
            while (dataRow != null) {
                //            System.out.println(dataRow);
                if (!dataRow.startsWith("chinese")) {
                    Word word = getOneWord(dataRow);
                    System.out.println(word);
                    result.add(word);
                }
                dataRow = fileBuffer.readLine(); // Read first line.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    private static Word getOneWord(String dataStr) {
        Word word = new Word();
        String[] columns = dataStr.split(",");
        word.setZiWithPys(getZis(columns[0], columns[1], columns[2]));
        word.setChapter(Integer.valueOf(columns[6]));
        word.setPage(Integer.valueOf(columns[7]));
        word.setLevel(Integer.valueOf(columns[8]));
        return word;
    }

    private static List<ZiWithPy> getZis(String chinese, String pinyinUtf8, String pinyinNumber) {
        String[] pyNumbers = pinyinNumber.split(" ");
        String[] pyUtf = pinyinUtf8.split(" ");

        List<ZiWithPy> zis = new ArrayList<ZiWithPy>();
        for (int i = 0; i < pyNumbers.length; i++) {
            ZiWithPy zi = new ZiWithPy();
            zi.setChinese(chinese.substring(i, i+1));
            zi.setPinyinUtf8(pyUtf[i]);
            zi.setPinyinNumber(pyNumbers[i]);
            zis.add(zi);
        }
        return zis;
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(getOneWord("哪里,nǎ lǐ,na3 li3,沪教一年级第二学期,1,2,0,0,0"));
        getAllWords("D:\\prj-mobile\\WordReaderServer\\mobile_resources\\hujiao_1_2_with_pinyin.txt");
    }
}
