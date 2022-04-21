package com.iesfranciscodelosrios.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationService {
    @Autowired
    FirebaseMessaging firebaseMessaging;

    public String sendNotification(Map<String,String> orderId,String title, String body, String token) throws FirebaseMessagingException {
        Notification notification = Notification.builder().setTitle(title).setBody(body).build();
        Message message = Message.builder().setToken(token).putAllData(orderId).setNotification(notification).build();
        //TODO loguear el resultado de la operación
        return firebaseMessaging.send(message);
    }
}
