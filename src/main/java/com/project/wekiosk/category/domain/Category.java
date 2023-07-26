package com.project.wekiosk.category.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity
public class Category {

    @Id
    private Long cateno;
    private String catename;
    private Long sno;
}
