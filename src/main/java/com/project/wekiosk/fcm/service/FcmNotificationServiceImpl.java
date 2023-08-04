package com.project.wekiosk.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.project.wekiosk.fcm.dto.FcmNotificationDTO;
import com.project.wekiosk.member.domain.Member;
import com.project.wekiosk.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FcmNotificationServiceImpl implements FcmNotificationService{

    private final FirebaseMessaging firebaseMessaging;
    private final MemberRepository memberRepository;


    @Override
    public void sendLoginInfo(String email) {

        Optional<Member> result = memberRepository.findById(email);
        Member member = result.orElseThrow();

        String token = member.getFcmtoken();
        Message message = Message.builder()
                .putData("title", "로그인 알림")
                .putData("body", email + "님, 안녕하세요?")
                .putData("image", "https://192.168.0.29:3000/img/no3.jpg")
                .setToken(token)
                .build();

        send(message);
    }

    public void send(Message message) {
        FirebaseMessaging.getInstance().sendAsync(message);
    }
}
