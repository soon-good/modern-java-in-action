package io.study.lang.javastudy2022ty1.grammer.varaibles.auto_type_conversion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegerAutoTypeConversionTest {

    @Test
    public void byte에_byte간의_덧셈을_하면_컴파일에러를_낸다(){
        byte a = 1;
        byte b = 2;
//        byte c = a + b; // 컴파일 에러
//        int c = a + b;
    }

    @Test
    public void int와_int간의_덧셈연산(){
        int x = 10;
        int y = 20;
        int result = x + y;
    }

    @Test
    public void 정수리터럴간의_연산_자동타입변환되지_않는다(){
        byte result = 10 + 20;
    }

    @Test
    public void 정수리터럴간의_연산_더_큰_타입인_long타입이_피연산자로_쓰일경우(){
        int a = 1;
        int b = 2;
        long c = 3L;

        long sum = a + b + c;

        assertThat(a + b + c).isEqualTo(sum);
    }
}
