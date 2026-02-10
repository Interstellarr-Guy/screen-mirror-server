package com.mirror.stream.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

@Component
public class ScreenViewerHandler extends BinaryWebSocketHandler {

    private final FrameBroadcaster broadcaster;

    public ScreenViewerHandler(FrameBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        broadcaster.addViewer(session);
        System.out.println("Viewer connected: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        broadcaster.removeViewer(session);
        System.out.println("Viewer disconnected: " + session.getId());
    }
    
 // IMPORTANT: viewers DO NOT send binary messages
    @Override
    protected void handleBinaryMessage(
            WebSocketSession session,
            BinaryMessage message) {
        // ignore
    }
}
