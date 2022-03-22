package io.study.lang.javastudy2022ty1.grammer.varaibles.auto_type_conversion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FloatDoubleTypeConversionTest {

    @Test
    public void 피연산자_중_하나가_double_타입일경우(){
        // 3.14f    : float 타입
        // 1.2      : double 타입
        double result = 3.14f * 1.2;
        System.out.println(result);
    }

    @Test
    public void int타입으로_강제타입변환해야_할_경우(){
        int intVal = 10;
        double doubleVal = 3.14;
        int result = intVal * (int)doubleVal;
        System.out.println(result);
    }

    @Test
    public void 실수리터럴간의_연산은_double에_저장하는것이_기본옵션(){
        double result = 3.14 * 10;
        System.out.println(result);
    }

    @Test
    public void 실수리터럴간의_연산을_float에_저장할_경우_컴파일에러다(){
//        float result = 3.14 * 10; // 컴파일 에러
    }

    @Test
    public void 실수리터럴간의_연산을_float에_저장하고_싶다면_각_리터럴을_float로_지정해준다(){
        float result = 3.14f * 10f;
        System.out.println(result);
    }

    @Test
    public void 정수와_정수간의_나눗셈은_실수로_저장되지않고_소숫점은_버려진다(){
        int first = 1;
        int second = 2;
        int result = first / second;

        assertThat(result).isEqualTo(0);
    }

    @Test
    public void 정수와_정수간의_나눗셈을_실수로_저장하는_방법_1(){
        int first = 1;
        int second = 2;
        double result = (double) first / second;

        assertThat(result).isEqualTo(0.5);
    }

    @Test
    public void 정수와_정수간의_나눗셈을_실수로_저장하는_방법_2(){
        int first = 1;
        int second = 2;
        double result = first / (double) second;

        assertThat(result).isEqualTo(0.5);
    }

    @Test
    public void 정수와_정수간의_나눗셈을_실수로_저장하는_방법_3(){
        int first = 1;
        int second = 2;
        double result = (double) first / (double) second;

        assertThat(result).isEqualTo(0.5);
    }

}
