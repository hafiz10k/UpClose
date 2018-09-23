package handler;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

	private Clip clip;

	public Audio(String path) {

		try {
			AudioInputStream aud = 
					AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));

			AudioFormat baseFormat = aud.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels() * 2,
					baseFormat.getSampleRate(),
					false);

			AudioInputStream decodeAud = AudioSystem.getAudioInputStream(decodeFormat, aud);
			clip = AudioSystem.getClip();
			clip.open(decodeAud);
		}

		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		if(clip == null) {
			return;
		}
		stop();

		clip.setFramePosition(0);
		clip.start();
	}

	public void stop() {
		if(clip.isRunning()) {
			clip.stop();
		}
	}

	public void close() {
		stop();
		clip.close();
	}

}
