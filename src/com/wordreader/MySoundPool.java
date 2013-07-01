package com.wordreader;

import android.media.SoundPool;

public class MySoundPool extends SoundPool{

	public MySoundPool(int maxStreams, int streamType, int srcQuality) {
		super(maxStreams, streamType, srcQuality);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setOnLoadCompleteListener(OnLoadCompleteListener listener) {
		// TODO Auto-generated method stub
		super.setOnLoadCompleteListener(listener);
	}
	
	

}
