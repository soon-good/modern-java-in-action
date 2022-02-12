package io.study.lang.javastudy2022ty1.java_puzzler.puzzle86;

import org.junit.jupiter.api.Test;

public class BracketTest {

    @Test
    public void 일반적인경우의_음수연산자와_괄호연산_혼합__정상적이다(){
        int i = 23;
        int j = -(23);

        System.out.println("23 = " + i);
        System.out.println("-(23) = " + j);
    }

    @Test
    public void 자료형의_최대값에_음수연산자와_괄호연산_혼합__비정상이다(){
//        int i_bracket_neg = -(2147483648);  // 컴파일 에러
        int i_neg = -2147483648;            // 에러가 아니다.

//        long l_bracket_neg = -(9223372036854775808L);   // 컴파일 에러
        long l_neg = -9223372036854775808L;             // 에러가 아니다.
    }
}
