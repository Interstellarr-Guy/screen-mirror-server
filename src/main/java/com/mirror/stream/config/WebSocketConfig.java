package com.mirror.stream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.mirror.stream.handler.ScreenSenderHandler;
import com.mirror.stream.handler.ScreenViewerHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ScreenSenderHandler senderHandler;
    private final ScreenViewerHandler viewerHandler;

    public WebSocketConfig(ScreenSenderHandler senderHandler,
                           ScreenViewerHandler viewerHandler) {
        this.senderHandler = senderHandler;
        this.viewerHandler = viewerHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(senderHandler, "/screen/send")
                .setAllowedOrigins("*");

        registry.addHandler(viewerHandler, "/screen/view")
                .setAllowedOrigins("*");
    }
    
    //to reduce size
   
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container =
                new ServletServerContainerFactoryBean();

        container.setMaxTextMessageBufferSize(1024 * 1024);    // 1 MB
        container.setMaxBinaryMessageBufferSize(512 * 1024); // 512 kB
        container.setMaxSessionIdleTimeout(60_000L); 

        return container;
    }
}
