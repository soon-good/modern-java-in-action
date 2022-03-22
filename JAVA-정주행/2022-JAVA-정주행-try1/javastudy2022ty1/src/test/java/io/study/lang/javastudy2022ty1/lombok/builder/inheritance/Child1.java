package io.study.lang.javastudy2022ty1.lombok.builder.inheritance;

import lombok.*;

@Getter @Setter @ToString
public class Child1 extends Parent1{
    private String phone;

    @Builder(builderMethodName = "child1Builder")
    public Child1(String name, String email, String phone){
        super(name, email);
        this.phone = phone;
    }
}
