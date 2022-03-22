package io.study.lang.javastudy2022ty1.grammer.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StringClassTest {

    @Test
    @DisplayName("String 은 불변이다.")
    public void test1(){
        String HELLO = "HELLO";
        String hello = HELLO.toLowerCase();

        System.out.println(HELLO);
        System.out.println(hello);
    }

    @Test
    @DisplayName("StringBuilder 사용해보기")
    public void test2(){
        StringBuilder builder = new StringBuilder();

        builder.append("오늘 회사에 이벤트가 생겨서").append("\n")
                .append("트래픽 모니터링을 하게 되었다.").append("\n")
                .append("이럴 거면 진작에 일정공유를 해주지 좀.").append("\n");

        String message = builder.toString();
        System.out.println(message);
    }

    @Test
    @DisplayName("+ 연산자 사용해보기")
    public void test3(){
        String msg1 = "오늘 회사에 이벤트가 생겨서\n";
        String msg2 = "트래픽 모니터링을 하게 되었다.\n";
        String msg3 = "이럴 거면 진작에 일정공유를 해주지 좀.\n";

        String msg = msg1 + msg2 + msg3;

        System.out.println(msg);
    }

    @Test
    @DisplayName("concat 메서드 사용")
    public void test4(){
        String msg1 = "오늘 회사에 이벤트가 생겨서\n";
        String msg2 = "트래픽 모니터링을 하게 되었다.\n";
        String msg3 = "이럴 거면 진작에 일정공유를 해주지 좀.\n";

        String msg = msg1.concat(msg2).concat(msg3);

        System.out.println(msg);
    }

    @Test
    @DisplayName("StringBuilder 로 단순문자열 append를 100만건 해보기")
    public void test5(){
        StringBuilder builder = new StringBuilder();
        long start = System.nanoTime();
        for(int i=0; i<1000_000; i++){
            builder.append("a");
        }
        long diff = System.nanoTime() - start;
        System.out.println("걸린 시간 : " + diff + " us. " + (diff/1000000) + " ms.");
    }

    @Test
    @DisplayName("StringBuffer 로 단순문자열 append를 100만건 해보기")
    public void test5_1(){
        StringBuffer buffer = new StringBuffer();
        long start = System.nanoTime();
        for(int i=0; i<1000_000; i++){
            buffer.append("a");
        }
        long diff = System.nanoTime() - start;
        System.out.println("걸린 시간 : " + diff + " us. " + (diff/1000000) + " ms.");
    }

    @Test
    @DisplayName("문자열을 + 연산자로 100만건 append 해보기")
    public void test6(){
        String msg = "";
        long start = System.nanoTime();
        for(int i=0; i<1000_000; i++){
            msg = msg + "a";
        }
        long diff = System.nanoTime() - start;
        System.out.println("걸린 시간 : " + diff + " us. " + (diff/1000000) + " ms.");
    }

    @Test
    @DisplayName("문자열을 concat() 메서드로 100만건 append 해보기")
    public void test7(){
        String msg = "";
        long start = System.nanoTime();
        for(int i=0; i<1000_000; i++){
            msg = msg.concat("a");
        }
        long diff = System.nanoTime() - start;
        System.out.println("걸린 시간 : " + diff + " us. " + (diff/1000000) + " ms.");
    }
}
