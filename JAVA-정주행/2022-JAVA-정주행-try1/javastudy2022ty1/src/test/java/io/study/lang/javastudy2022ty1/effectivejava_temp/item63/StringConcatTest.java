package io.study.lang.javastudy2022ty1.effectivejava_temp.item63;

import org.junit.jupiter.api.Test;

public class StringConcatTest {

    public static final String TEST = "################################################################################";

    public int numItems(){
        return 100;
    }

    public String lineForItem(){
        return TEST;
    }

    @Test
    public void 테스트1_일반문자열_붙이기_연산(){
        String result = "";
        for(int i=0; i<numItems(); i++){
            result += lineForItem();
        }
    }


}
