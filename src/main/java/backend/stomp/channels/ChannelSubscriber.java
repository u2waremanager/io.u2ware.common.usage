package backend.stomp.channels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import backend.api.channels.ChannelRepository;
import io.u2ware.common.stomp.client.WebsocketStompClientHandler;

@Component("${io.u2ware.common.stomp.client.destination}")
public class ChannelSubscriber implements WebsocketStompClientHandler{

    protected @Autowired ObjectMapper mapper;
    protected @Autowired ChannelRepository channelRepository;

    @Override
    public void handleFrame(StompHeaders headers, JsonNode message) {

        System.err.println("RECEIVED: "+ message);

        // Long timestamp = message.get("timestamp").asLong();
        // String principal = message.get("principal").asText();
        // AttributesMap payload = mapper.convertValue(message.get("payload"), AttributesMap.class);
        

        // Channel channel = new Channel();
        // channel.setTimestamp(timestamp);
        // channel.setPrincipal(principal);
        // channel.setPayload(payload);
        // System.err.println("RECEIVED: "+ channel);

        // channelRepository.save(channel);
    }
}
