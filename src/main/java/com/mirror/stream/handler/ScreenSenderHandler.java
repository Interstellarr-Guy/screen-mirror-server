package com.mirror.stream.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

@Component
public class ScreenSenderHandler extends BinaryWebSocketHandler {

    private final FrameBroadcaster broadcaster;

    public ScreenSenderHandler(FrameBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        

        System.out.println("Frame received from sender, size="
                + message.getPayloadLength());
    	
    	broadcaster.broadcast(message);
    }
    // debug method
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("SENDER connected: " + session.getId());
        
        
    }

}
