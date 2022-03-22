package io.study.lang.javastudy2022ty1.lombok.builder.inheritance;

import org.junit.jupiter.api.Test;

public class InheritanceBuilderTest {

    @Test
    public void 상속시에_빌더패턴에서_오류가_나는지_테스트(){
        Child1 child = Child1.child1Builder()
                .name("마이클조던")
                .email("mj@nba.com")
                .phone("010-8943-3901")
                .build();

        System.out.println(child);
    }
}
