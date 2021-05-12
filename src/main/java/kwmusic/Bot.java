package kwmusic;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class Bot {
	
	private static final Map<String, Command> commands = new HashMap<>();
	
	static {
		commands.put("ping", event -> event.getMessage()
				.getChannel().block().createMessage("Pong!").block());
	}
	
	public static void main(String[] args) {
		final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
		playerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
		AudioSourceManagers.registerRemoteSources(playerManager);
		
		final AudioPlayer player= playerManager.createPlayer();
		
		AudioProvider provider = new LavaPlayerAudioProvider(player);
		
		final GatewayDiscordClient client = DiscordClientBuilder.create(args[0]).build()
		.login()
		.block();
		client.getEventDispatcher().on(MessageCreateEvent.class)
		.subscribe(event -> {
			final String content = event.getMessage().getContent();
			
			for(final Map.Entry<String, Command> entry : commands.entrySet()) {
				if (content.startsWith('!' + entry.getKey())) {
					entry.getValue().execute(event);
				}
			}
		});
		client.onDisconnect().block();
		
	}

}
