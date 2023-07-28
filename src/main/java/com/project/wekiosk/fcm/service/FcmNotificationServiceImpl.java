package com.project.wekiosk.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.project.wekiosk.fcm.dto.FcmNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FcmNotificationServiceImpl implements FcmNotificationService{

    private final FirebaseMessaging firebaseMessaging;


    @Override
    public void sendLoginInfo(String email) {

        String token = "ddmXT26deaYyKOVAJmi9b1:APA91bH2r4u1AS0MfM6AGogO-pGL-4MbVjYPQccE55RP5dwf3Tc7QjiVU5QW84p0JimdSo5uTYrwJn-N02jwXDduSKK5FE4gfecTlJ749-xH35J_uuSaF3QOhVSITAXSmigp4TDoOmMF";
        Message message = Message.builder()
                .putData("title", "로그인 알림")
                .putData("body", email + "님, 안녕하세요?")
                .putData("image", "http://localhost:3000/img/no3.jpg")
                .setToken(token)
                .build();

        send(message);
    }

    public void send(Message message) {
        FirebaseMessaging.getInstance().sendAsync(message);
    }
}
