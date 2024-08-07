package likelion.eight.common.config.web;

import likelion.eight.domain.chat.websocket.CustomHandshakeInterceptor;
import likelion.eight.domain.token.service.TokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 반드시 수정해야함.
    private final TokenService tokenService;

    public WebSocketConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("http://localhost:8080")
                .withSockJS()
                .setInterceptors(new CustomHandshakeInterceptor(tokenService));
    }
}