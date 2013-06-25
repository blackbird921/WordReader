package com.varma.samples.musicplayer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MusicListActivity extends ListActivity {
	private ImageButton playButton;
	private ImageButton prevButton;
	private ImageButton nextButton;

	private TextView text;

	SoundManager soundManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		String[] values = new String[] { "中国", "今天" };

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);

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
			soundManager.loadSounds("cn");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position, long id) {
		super.onListItemClick(list, view, position, id);

		startPlay(list.getAdapter().getItem(position).toString());
	}

	private void startPlay(String file) {
		if (file != null) {
			Log.i("Selected: ", file);
			text.setText(file);
		}
		List<String> keys = new ArrayList<String>();
		if (file.equalsIgnoreCase("中国")) {
			keys.add("zhong1");
			keys.add("guo2");
		} else if (file.equalsIgnoreCase("今天")) {
			keys.add("jin1");
			keys.add("tian1");
		}

		try {
			String[] files = new String[keys.size()];
			files = keys.toArray(files);
			soundManager.playMutilSounds(files);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

	private View.OnClickListener onButtonClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			startPlay(null);
		}
	};

}