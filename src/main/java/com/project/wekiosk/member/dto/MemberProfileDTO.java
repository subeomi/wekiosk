package com.project.wekiosk.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberProfileDTO {

    private String memail;

    private String mpw;

    private String mname;

    private int mstatus;

    private String fcmtoken;
}
