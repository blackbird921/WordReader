package com.wordreader;

import org.xml.sax.SAXException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

public class SoundManager {
	public static String SOUND_FOLDER= "audio_man";
	public static String SOUND_SUFFIX= "mp3";
	public static int seperateTime = 500;

	private SoundPool mSoundPool;

	private HashMap<String, Integer> mSoundPoolMap;

	private AudioManager mAudioManager;

	private Context mContext;

	private Handler mHandler = new Handler();

	private Vector<Integer> mKillSoundQueue = new Vector<Integer>();

	private long delay = 1000;


	private float rate = 1.0f;

	private String locale;

	static private SoundManager _instance;

	/**
	 * Requests the instance of the Sound Manager and creates it if it does not
	 * exist.
	 * 
	 * @return Returns the single instance of the SoundManager
	 */
	static synchronized public SoundManager getInstance() {
		if (_instance == null)
			_instance = new SoundManager();
		return _instance;
	}

	/** 
     *  
     */
	private SoundManager() {

	}

	/**
	 * Initialises the storage for the sounds
	 * 
	 * @param theContext
	 *            The Application context
	 */

	public void initSounds(Context theContext) {
		mContext = theContext;
		mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mSoundPoolMap = new HashMap<String, Integer>();
		mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
	}

	/**
	 * Add a new Sound to the SoundPool
	 * 
	 * @param key
	 *            - The Sound Index for Retrieval
	 * @param SoundID
	 *            - The Android ID for the Sound asset.
	 */

	public void addSound(String key, int SoundID) {
		mSoundPoolMap.put(key, mSoundPool.load(mContext, SoundID, 1));
	}

	/**
	 * 
	 * @param key
	 *            the key we need to get the sound later
	 * @param afd
	 *            the fie store in the asset
	 */
	@TargetApi(3)
	@SuppressLint("NewApi")
	public void addSound(String key, AssetFileDescriptor afd) {
		mSoundPoolMap.put(key, mSoundPool.load(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength(), 1));
	}

	/**
	 * play the sound loaded to the SoundPool by the key we set
	 * 
	 * @param key
	 *            the key in the map
	 */
	public void playSound(String key) {

		int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		int soundId = mSoundPool.play(mSoundPoolMap.get(key), streamVolume, streamVolume, 1, 0, rate);
		mKillSoundQueue.add(soundId);
		// schedule the current sound to stop after set milliseconds
		mHandler.postDelayed(new Runnable() {
			public void run() {
				if (!mKillSoundQueue.isEmpty()) {
					mSoundPool.stop(mKillSoundQueue.firstElement());
				}
			}
		}, delay);

	}

	/**
	 * 
	 * @param key
	 *            the key in the map
	 */
	public void playLoopedSound(String key) {

		int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		int soundId = mSoundPool.play(mSoundPoolMap.get(key), streamVolume, streamVolume, 1, -1, rate);

		mKillSoundQueue.add(soundId);
		// schedule the current sound to stop after set milliseconds
		mHandler.postDelayed(new Runnable() {
			public void run() {
				if (!mKillSoundQueue.isEmpty()) {
					mSoundPool.stop(mKillSoundQueue.firstElement());
				}
			}
		}, delay);
	}

	/**
	 * play the sounds have loaded in SoundPool
	 * 
	 * @param keys
	 *            the files key stored in the map
	 * @throws InterruptedException
	 */
	public void playMutilSounds(String keys[]) throws InterruptedException {
		int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		System.out.println(mSoundPoolMap);
		for (String key : keys) {
			Log.d("playMutilSounds", key);
			if (mSoundPoolMap.containsKey(key)) {
//				int soundId = mSoundPool.play(mSoundPoolMap.get(key), streamVolume, streamVolume, 1, 0, rate);
				int soundId = mSoundPool.play(mSoundPoolMap.get(key), 1, 1, 1, 0, rate);
				// sleep for a while for SoundPool play
				Thread.sleep(seperateTime);
				mKillSoundQueue.add(soundId);
			}

		}

		// schedule the current sound to stop after set milliseconds
		mHandler.postDelayed(new Runnable() {
			public void run() {
				if (!mKillSoundQueue.isEmpty()) {
					mSoundPool.stop(mKillSoundQueue.firstElement());
				}
			}
		}, delay);

	}

	/**
	 * Loads the various sound assets
	 * 
	 * @param locale
	 *            the load to load audio files
	 */

	public void loadSounds(String locale) throws SAXException, IOException, ParserConfigurationException {
		Log.d("load locale", locale);
		this.locale = locale;
		AssetFileDescriptor afd;

		for(String fileName: mContext.getAssets().list(SOUND_FOLDER)){
			afd = mContext.getAssets().openFd(SOUND_FOLDER+"/"+fileName);
			addSound(fileName.replaceAll("."+SOUND_SUFFIX, ""), afd);
			Log.i("load audio", fileName);
		}

	}
	
	public void loadSoundsByNames(List<String> fileNames) throws SAXException, IOException, ParserConfigurationException {
		AssetFileDescriptor afd;

		unloadAllSoundsIn();
		
		for(String fileName: mContext.getAssets().list(SOUND_FOLDER)){
			String key = fileName.replaceAll("."+SOUND_SUFFIX, "");
			if(fileNames.contains(key)){
				afd = mContext.getAssets().openFd(SOUND_FOLDER + "/" + fileName);
				addSound(key, afd);
				Log.i("load audio", fileName);
			}
		}

	}


	/**
	 * Stop a Sound
	 * 
	 * @param index
	 *            - index of the sound to be stopped
	 */
	public void stopSound(int index) {
		mSoundPool.stop(mSoundPoolMap.get(index));
	}

	/**
	 * Deallocates the resources and Instance of SoundManager
	 */
	public void cleanup() {
		mSoundPool.release();
		mSoundPool = null;
		mSoundPoolMap.clear();
		mAudioManager.unloadSoundEffects();
		_instance = null;

	}

	/**
	 * unload all resource in the sound pool support for user change
	 * VoiceLanguage or Locale or user close the voice function !
	 */

	public void unloadAllSoundsIn() {
		if (mSoundPoolMap.size() > 0) {
			for (String key : mSoundPoolMap.keySet()) {
				mSoundPool.unload(mSoundPoolMap.get(key));
			}
		}
		mKillSoundQueue.clear();
		mSoundPoolMap.clear();
	}

	/**
	 * set the speed of soundPool
	 * 
	 * @param i
	 *            i<0 means slow i= 0 means normal i>0 means fast
	 */
	public void setVoiceSpeed(int i) {
		if (i > 0) {
			rate = 1.2f;
		} else if (i < 0) {
			rate = 0.8f;
		} else {
			rate = 1.0f;
		}

	}

	/**
	 * set the delay after one number's sound have played
	 * 
	 * @param i
	 *            i<0 means short i= 0 means normal i>0 means long
	 */
	public void setVoiceDelay(int i) {
		if (i > 0) {
			seperateTime = 700;
		} else if (i < 0) {
			seperateTime = 400;
		} else {
			seperateTime = 500;
		}

	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	
	public static class MyOnLoadCompleteListener implements OnLoadCompleteListener{
		SoundManager manager;
		String[] keys;
		
		public MyOnLoadCompleteListener(SoundManager manager, String[] keys){
			this.manager = manager;
			this.keys = keys;
		}

		@Override
		public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
			try {
				manager.playMutilSounds(keys);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void setOnLoadCompleteListener(OnLoadCompleteListener listener){
		this.mSoundPool.setOnLoadCompleteListener(listener);
	}
}