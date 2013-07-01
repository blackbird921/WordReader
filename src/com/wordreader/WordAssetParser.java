package com.wordreader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.logic.wordreader.data.Word;
import com.logic.wordreader.data.WordParser;

import android.content.Context;

public class WordAssetParser {
	public static List<Word> getAllWords(Context context, String fileName) {
		List<Word> words = new ArrayList<Word>();
		try {
			InputStream is = context.getAssets().open(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			words = WordParser.getAllWords(br);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return words;
	}
}
