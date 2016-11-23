package whu.iss.sric.android;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

public class SoundPlay {

	int streamVolume;

	private SoundPool soundPool;

	private HashMap<Integer, Integer> soundPoolMap;

	public void initSounds(Context context) {
		soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 100);

		soundPoolMap = new HashMap<Integer, Integer>();

		AudioManager mgr = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	}

	public void loadSfx(Context context, int raw, int ID) {
		soundPoolMap.put(ID, soundPool.load(context, raw, 1));
	}

	public void play(int sound, int uLoop) {
		soundPool.play(soundPoolMap.get(sound), streamVolume, streamVolume, 1,
				uLoop, 1f);
	}
}
