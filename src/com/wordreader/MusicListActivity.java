package com.wordreader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.logic.wordreader.data.Word;
import com.logic.wordreader.data.ZiWithPy;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MusicListActivity extends ListActivity {
	private ImageButton playButton;
	private ImageButton prevButton;
	private ImageButton nextButton;

	private TextView text;

	SoundManager soundManager;
	
	List<Word> words;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// String[] values = new String[] { "中国", "今天" };
		// final ArrayList<String> list = new ArrayList<String>();
		// for (int i = 0; i < values.length; ++i) {
		// list.add(values[i]);
		// }
		if(words==null){
			words = WordAssetParser.getAllWords(this, "hujiao_1_2_with_pinyin.txt");
		}
		System.out.println("======================");
		System.out.println(words);
		final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, words);

		this.setListAdapter(adapter);

		playButton = (ImageButton) findViewById(R.id.play);
		prevButton = (ImageButton) findViewById(R.id.prev);
		nextButton = (ImageButton) findViewById(R.id.next);
		text = (TextView) findViewById(R.id.selectedfile);

		playButton.setOnClickListener(onButtonClick);
		nextButton.setOnClickListener(onButtonClick);
		prevButton.setOnClickListener(onButtonClick);

		soundManager = SoundManager.getInstance();
		soundManager.initSounds(this);
		try {
//			soundManager.loadSounds("cn");
			List<String> files = new ArrayList<String>();
			files.add("zhong1");
			files.add("guo2");
			soundManager.loadSoundsByNames(files);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position, long id) {
		super.onListItemClick(list, view, position, id);

		Word word = (Word)list.getAdapter().getItem(position);
		List<String> keys = new ArrayList<String>();
		for (ZiWithPy zi : word.getZiWithPys()) {
			keys.add(zi.getPinyinNumber());
		}
		Log.i("here", "clicked:"+keys);
		startPlay(list.getAdapter().getItem(position).toString(), keys);
	}

	private void startPlay(String chinese, List<String> keys) {
//		keys= new ArrayList<String>();
//		keys.add("zhong1");
//		keys.add("guo2");

		try {
			String[] files = new String[keys.size()];
			files = keys.toArray(files);
			for(String s: files){
				Log.i("here", "="+s);
			}
			soundManager.setOnLoadCompleteListener(new SoundManager.MyOnLoadCompleteListener(soundManager, files));
			soundManager.loadSoundsByNames(keys);
//			Thread.sleep(1000);
//			soundManager.playMutilSounds(files);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private class StableArrayAdapter extends ArrayAdapter<Word> {

		HashMap<String, String> mIdMap = new HashMap<String, String>();
		LayoutInflater layoutInflater;

		public StableArrayAdapter(Context context, int textViewResourceId, List<Word> objects) {
			super(context, textViewResourceId, objects);
			for (Word word : objects) {
				String chinese = "";
				String py = "";
				for (ZiWithPy zi : word.getZiWithPys()) {
					chinese += zi.getChinese();
					py += zi.getPinyinNumber() + " ";
				}
				mIdMap.put(chinese, py);
			}
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

	private View.OnClickListener onButtonClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
//			startPlay(null);
		}
	};

}