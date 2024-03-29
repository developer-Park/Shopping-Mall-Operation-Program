package com.example.secondteamproject.chatting;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("헤깔림방지"+session.getHandshakeHeaders());
        System.out.println(message);
        String payload = message.getPayload();

        System.out.println("payload: " + payload);

        for (WebSocketSession sess : list) {
            sess.sendMessage(message);
        }
    }

    // Client가 접속 시 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        list.add(session);
        System.out.println(list);
        System.out.println(session.getHandshakeHeaders()+"이건뭐지");
        System.out.println(session.getPrincipal()+ "프린시플찾기");
        System.out.println(session.getAttributes()+"어트리뷰트");

        System.out.println(session + " 클라이언트 접속");
    }

    // Client가 접속 해제 시 호출되는 메서드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        System.out.println(session + " 클라이언트 접속 해제");
        list.remove(session);
    }
}