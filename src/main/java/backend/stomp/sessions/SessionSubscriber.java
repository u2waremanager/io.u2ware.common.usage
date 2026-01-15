package backend.stomp.sessions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import backend.api.sessions.SessionRepository;
import backend.domain.Session;
import io.u2ware.common.stomp.client.WebsocketStompClientHandler;

@Component
public class SessionSubscriber implements WebsocketStompClientHandler{

    protected @Autowired ObjectMapper mapper;
    protected @Autowired SessionRepository sessionRepository;

    @Override
    public void handleFrame(StompHeaders headers, JsonNode message) {

        System.err.println("RECEIVED: "+ message);

        Long timestamp = message.get("timestamp").asLong();
        String principal = message.get("principal").asText();
        String state = message.get("payload").get("state").asText();


        Session e = new Session();
        e.setPrincipal(principal);
        e.setTimestamp(timestamp);
        e.setState(state);
        sessionRepository.save(e);
    }

    @Override
    public boolean isEventHandler() {
        return true;
    }
}
