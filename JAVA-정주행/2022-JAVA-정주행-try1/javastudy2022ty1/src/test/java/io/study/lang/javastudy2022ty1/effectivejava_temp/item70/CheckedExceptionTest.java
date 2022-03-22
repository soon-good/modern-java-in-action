package io.study.lang.javastudy2022ty1.effectivejava_temp.item70;

import org.junit.jupiter.api.Test;

public class CheckedExceptionTest {

    class HelloCheckedException extends Exception {
        public HelloCheckedException(String message) {
            System.out.println(message);
        }
    }

    public void helloKorea(String countryCode) throws HelloCheckedException {
        if(!"KR".equals(countryCode)){
            throw new HelloCheckedException("한국어로 인사하세요.");
        }
        System.out.println("안녕하세요~!!");
    }

    @Test
    public void test3(){
        System.out.println("(0)");
        caller2();
        System.out.println("(1)");
    }

    public void caller2(){
        try {
            helloKorea("US");
        } catch (HelloCheckedException e) {
            e.printStackTrace();
        }
        System.out.println("caller2 finish");
    }

    @Test
    public void test2(){
        caller1();
    }

    public void caller1(){
        try {
            helloKorea("KR");
        } catch (HelloCheckedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1(){
//        helloKorea("KR");
    }
}
