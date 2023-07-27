package com.project.wekiosk.option.domain;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Options {

    private Long pno;
    private String oname;
    private Long oprice;

    private int ord;
}
