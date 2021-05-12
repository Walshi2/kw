package kwmusic;

import java.nio.ByteBuffer;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;

public class LavaPlayerAudioProvider {
	
	private final AudioPlayer player;
	private final MutableAudioFrame frame = new MutableAudioFrame();
	
	public LavaPlayerAudioProvider(final AudioPlayer player) {
		
		super();
	}

}
