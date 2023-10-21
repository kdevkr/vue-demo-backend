package kr.kdev.backend.websocket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Component
public class WebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;

    @EventListener(SessionSubscribeEvent.class)
    public void handle(SessionSubscribeEvent event) {
        Message<byte[]> message = event.getMessage();
        MessageHeaders headers = message.getHeaders();
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        if (sessionId != null) {
            String username = sessionId;
            Principal user = event.getUser();
            if (user != null) {
                username = user.getName();
            }
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            headerAccessor.setSessionId(sessionId);

            Map<String, Object> payload = new HashMap<>();
            payload.put("message", "Hello! %s :)".formatted(username));
            messagingTemplate.convertAndSendToUser(username, "/topic/hello", payload, headerAccessor.getMessageHeaders());
        }
    }

    @EventListener(SessionUnsubscribeEvent.class)
    public void handle(SessionUnsubscribeEvent event) {
        Message<byte[]> message = event.getMessage();
        MessageHeaders headers = message.getHeaders();
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        if (sessionId != null) {
            String username = sessionId;
            Principal user = event.getUser();
            if (user != null) {
                username = user.getName();
            }
            log.info("Bye... :( {}.", username);
        }
    }
}
