package io.study.lang.javastudy2022ty1.effectivejava_temp.item70;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnCheckedExceptionTest {

    class HelloRuntimeException extends RuntimeException{
        public HelloRuntimeException(String message) {
            System.out.println(message);
        }
    }

    public void helloMessage(String countryCode){
        if(!"KR".equals(countryCode)){
            throw new RuntimeException("한국어로 인사해주세요!!");
        }
        System.out.println("안녕하세요");
    }

    @Test
    @DisplayName("단순 throw 된것을 받더라도 Unchecked Exception 은 다음 라인 처리를 수행하는지 확인")
    public void test3(){
        System.out.println("(1)");
        helloMessage("US");
        System.out.println("(2)");
    }

    @Test
    @DisplayName("이번에는 에러원인을 알수 있도록 에러 메시지를 출력해본다.")
    public void test2(){
        System.out.println("0");
        try{
            helloMessage("US");
            System.out.println("(1)");
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("");
        }
        System.out.println("(2)");
    }

    @Test
    public void test1(){
        assertDoesNotThrow(()->helloMessage("KR"));
        assertThrows(RuntimeException.class, ()->helloMessage("US"));
    }
}
