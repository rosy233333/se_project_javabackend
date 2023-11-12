package com.segroup.seproject_backend.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.segroup.seproject_backend.controller.ModelTrainController;
import com.segroup.seproject_backend.data_item.TrainStartF2JWebItem;
import com.segroup.seproject_backend.data_item.WebSocketItem;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import java.io.IOException;

// 训练模型时的websocket服务端，用于和前端连接
@ServerEndpoint("/manage/train")
@Component
public class TrainModelF2JEndpoint {

    // 当前的连接
    private Session currentSession = null;

    public ModelTrainController controller;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        if(currentSession == null) {
            currentSession = session;
            System.out.println("WebSocket：和前端连接成功。");
        }
        else {
            throw new IOException("WebSocket错误：前端同时进行了多个连接");
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        if(currentSession.equals(session)) {
            System.out.println("WebSocket：和前端连接关闭");
            currentSession = null;
        }
        else {
            throw new IOException("WebSocket错误：前端关闭了其它连接");
        }
    }

    @OnMessage
    public void onMsg(String text) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        WebSocketItem<Object> object = mapper.readValue(text, new TypeReference<>() {});
        if(object.getType().equals("start")) {
            TrainStartF2JWebItem startItem = mapper.readValue(text, new TypeReference<WebSocketItem<TrainStartF2JWebItem>>(){}).getObject();
            controller.onFStart(startItem);
        }
        else if (object.getType().equals("stop")) {
            controller.onFStop();
        }
        else {
            System.out.println("WebSocket错误：前端发送的消息格式出错");
        }

    }

    public void send(String text) throws IOException {
        if(currentSession == null) {
            System.out.println("WebSocket错误：前端连接已关闭");
            return;
        }
        currentSession.getBasicRemote().sendText(text);
    }

}
