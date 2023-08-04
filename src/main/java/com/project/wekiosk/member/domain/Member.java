package com.project.wekiosk.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    @Id
    private String memail;

    private String mpw;

    private String mname;

    private int mgrade;

    // 회원의 상태. -1 = 계정 정지,
    //             0 = 활성화,
    //             1 = 회원 탈퇴(비활성화)
    private int mstatus;

    private String fcmtoken;

    public void changeMpw(String mpw) {
        this.mpw = mpw;
    }

    public void updateFcmToken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }

    public void delAccount() {
        this.mstatus = 1;
    }

}
