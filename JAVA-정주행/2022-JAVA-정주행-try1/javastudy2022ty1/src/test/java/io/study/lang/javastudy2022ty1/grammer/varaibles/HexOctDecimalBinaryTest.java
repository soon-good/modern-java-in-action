package io.study.lang.javastudy2022ty1.grammer.varaibles;

import org.junit.jupiter.api.Test;

public class HexOctDecimalBinaryTest {

    @Test
    public void 테스트_각_진법의_숫자를_int자료형에_저장해서_출력하기(){
        int n1 = 0b1111;    // 2진법
        int n2 = 0222;      // 8진법
        int n3 = 222;       // 10진접
        int n4 = 0x1FC;     // 16진법

        System.out.println("n1 = " + n1);
        System.out.println("n2 = " + n2);
        System.out.println("n3 = " + n3);
        System.out.println("n4 = " + n4);
    }
}
