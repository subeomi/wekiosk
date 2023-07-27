package com.project.wekiosk.member;

import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MemberDTO {

    private String memail;

    private String mpw;

    private String mname;

    private int mgrade;

    private int mstatus;
}
