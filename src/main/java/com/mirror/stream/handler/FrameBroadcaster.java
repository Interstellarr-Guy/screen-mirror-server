package com.mirror.stream.handler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.stereotype.Component;

@Component
public class FrameBroadcaster {

    private final Set<WebSocketSession> viewers = ConcurrentHashMap.newKeySet();

    public void addViewer(WebSocketSession session) {
        viewers.add(session);
        System.out.println("Viewer connected: " + session.getId());
    }

    public void removeViewer(WebSocketSession session) {
        viewers.remove(session);
        System.out.println("Viewer disconnected: " + session.getId());
    }

     //Prev working ver
//    public void broadcast(BinaryMessage message) throws Exception {
//        for (WebSocketSession viewer : viewers) {
//            if (viewer.isOpen()) {
//               // viewer.sendMessage(message);
//            	viewer.sendMessage(
//            		    new BinaryMessage(message.getPayload().slice())
//            		);
//            	//To avoid memory pressure
//            	if (!viewer.isOpen()) return;
//            	if (viewer.getBinaryMessageSizeLimit() < message.getPayloadLength()) return;
//            }
//        }
//    }
    public void broadcast(BinaryMessage message) {
        for (WebSocketSession viewer : viewers) {
            try {
                if (!viewer.isOpen()) {
                    removeViewer(viewer);
                    continue;
                }

                // Drop frame if viewer buffer is too small
                if (viewer.getBinaryMessageSizeLimit() < message.getPayloadLength()) {
                    continue; // skip this viewer only
                }

                // Send a sliced payload to avoid shared buffer issues
                viewer.sendMessage(
                    new BinaryMessage(message.getPayload().slice())
                );

            } catch (Exception e) {
                // Any error → remove viewer, keep server alive
                removeViewer(viewer);
            }
        }
    }

    
}
