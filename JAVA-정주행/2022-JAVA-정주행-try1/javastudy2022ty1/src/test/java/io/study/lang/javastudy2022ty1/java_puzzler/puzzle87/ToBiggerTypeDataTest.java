package io.study.lang.javastudy2022ty1.java_puzzler.puzzle87;

import org.junit.jupiter.api.Test;

public class ToBiggerTypeDataTest {

    // 참고) Effective Java / 아이템 10 - equals 는 일반 규약을 지켜 재정의하라
    // 반사성(reflexivity), 대칭성(symmetry), 추이성(transitivity), 일관성(consistency), null-아님
    //  자바 퍼즐러에서는 transitivity 를 '이행성' 이라고 번역하고,
    //  Effective Java 에서는 transitivity 를 '추이성' 이라고 번역하고 있다..
    // transitivity 는 한국인에게 흔히 삼단논법이라고 알려진 개념이다.
    @Test
    public void 테스트_더_큰_타입으로의_자료형변환_transitivity_위배(){
        // transitivity 는 한국사람들에게는 삼단논법이라고 흔히 알려진 개념이다.

        // int 또는 long 을 float 로 변환하는 경우 숫자 손실이 발생한다.
        // long 을 double 로 변환하는 경우 숫자 손실이 발생한다.

        // 이런 이유로 a == b == c 가 되는지 체크할때
        // 각 타입 중 하나가 큰 타입일 경우 에러가 날 수도 있고,
        // 손실된 값도 같은 값으로 취급해버리는 오류가 발생하기도 한다.
        long a      = Long.MAX_VALUE;
        double b    = (double) Long.MAX_VALUE;
        long c      = Long.MAX_VALUE-1;

        System.out.println("a == b ? " + (a == b));
        System.out.println("b == c ? " + (b == c));
        System.out.println("a == c ? " + (a == c));

        // float, double 자료형으로 자료형을 변환할 때는 정확도 손실에 유의해야 한다.
        // 더 큰 타입으로 변환시 데이터가 손실되는데, 이런 버그는 매우 치명적이면서 찾기 힘든 버그를 유발한다.
        // 단순하게 정리하면, 혼합 자료형 연산을 할때는 항상 주의해야 한다.
    }
}
