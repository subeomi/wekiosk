package com.project.wekiosk.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;

    private String sname;

    private String saddress;

    private String scontact;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
