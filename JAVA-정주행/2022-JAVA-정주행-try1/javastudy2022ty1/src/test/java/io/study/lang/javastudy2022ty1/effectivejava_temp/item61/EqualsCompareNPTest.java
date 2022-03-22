package io.study.lang.javastudy2022ty1.effectivejava_temp.item61;

import org.junit.jupiter.api.Test;

public class EqualsCompareNPTest {

    @Test
    public void 초기화하지않을_경우는_컴파일에러_발생(){
        Integer i;

//        if(i == 2022) { // 컴파일 에러를 낸다. 초기화 되지 않은 값과 비교를 못하고, 초기화 되지 않은 값을 언박싱할 수 없기 때문이다.
//            System.out.println("믿을 수 없군");
//        }
    }

    @Test
    public void NullPointerException_예외_발생(){
        Integer i = null;

        if(i == 2022) {
            System.out.println("믿을 수 없군");
        }
    }

    @Test
    public void 수행속도가_느린_예제(){
        Long sum = 0L;
        for(long i = 0; i <= Integer.MAX_VALUE; i++){
            sum += i;
        }
        System.out.println(sum);
    }
}
