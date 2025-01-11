package com.example.taskblock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Generic method to send updates via WebSocket
     * 
     * @param object The object to be sent
     * @param destination The WebSocket destination topic
     */
    public void sendUpdate(Object object, String destination) {
        messagingTemplate.convertAndSend(destination, object);
    }

    /**
     * Method to send update to a specific user
     * 
     * @param object The object to be sent
     * @param userId The ID of the user to send the update to
     * @param destination The WebSocket destination queue
     */
    public void sendUserUpdate(Object object, String userId, String destination) {
        messagingTemplate.convertAndSendToUser(
            userId, 
            destination, 
            object
        );
    }
}