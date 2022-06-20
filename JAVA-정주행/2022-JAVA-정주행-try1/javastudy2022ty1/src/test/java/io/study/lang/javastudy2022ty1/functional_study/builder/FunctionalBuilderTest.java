package io.study.lang.javastudy2022ty1.functional_study.builder;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

public class FunctionalBuilderTest {

    @Test
    public void LEGACY_BUILDER_PATTERN(){
        Employee1 curry = Employee1.builder(UUID.randomUUID().toString(), "Curry")
                .withSalary(BigDecimal.valueOf(10002003000L))
                .build();

        System.out.println(curry);
    }

    @Test
    public void FUNCTIONAL_BUILDER_PATTERN(){
        Employee2 curry = Employee2.builder(UUID.randomUUID().toString(), "Curry")
                .with(builder -> {
                    builder.salary = BigDecimal.valueOf(9999999999999999L);
                    builder.nbaFinalCount = 4;
                    builder.phoneNumber = "999-9999-9999";
                })
                .build();

        System.out.println(curry);
    }

}
