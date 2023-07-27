package com.project.wekiosk.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "member")
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qno;

    private String qtitle;

    private String qcontent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}
