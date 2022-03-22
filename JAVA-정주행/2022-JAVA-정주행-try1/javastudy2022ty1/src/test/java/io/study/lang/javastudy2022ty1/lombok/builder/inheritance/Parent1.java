package io.study.lang.javastudy2022ty1.lombok.builder.inheritance;

import lombok.Builder;
import lombok.Data;

@Data
public class Parent1 {
    private String name;
    private String email;

    @Builder
    public Parent1(String name, String email){
        this.name = name;
        this.email = email;
    }
}
