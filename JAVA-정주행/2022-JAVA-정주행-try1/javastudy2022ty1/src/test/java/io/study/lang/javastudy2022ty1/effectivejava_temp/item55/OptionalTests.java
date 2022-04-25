package io.study.lang.javastudy2022ty1.effectivejava_temp.item55;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OptionalTests {

    @Test
    public void TEST_OR_ELSE(){
        String word1 = "안뇽하세요";
        String optWord1 = Optional.ofNullable(word1).orElse("인사말을 입력해주세요!!!");
        System.out.println(optWord1);

        String word2 = null;
        String optWord2 = Optional.ofNullable(word2).orElse("인사말을 입력해주세용!!!");
        System.out.println(optWord2);
    }

    @Test
    public void TEST_OR_ELSE_GET(){
        String word1 = "안뇽하세요요용!!!";
        String optWord1 = Optional.ofNullable(word1).orElseGet(() -> "반갑습니다!!!");
        System.out.println(optWord1);

        String word2 = null;
        String optWord2 = Optional.ofNullable(word2).orElseGet(() -> "반갑습니당 !!!");
        System.out.println(optWord2);
    }

    @Test
    public void TEST_OR_ELSE_THROW(){
        String word1 = "반갑습니당";
        String optWord1 = Optional.ofNullable(word1).orElseThrow(RuntimeException::new);
        System.out.println(optWord1);

        String word2 = null;
        assertThatThrownBy(()->{
            Optional.ofNullable(word2).orElseThrow(RuntimeException::new);
        }).isInstanceOf(RuntimeException.class);

        assertThatThrownBy(()->{
            Object word3 = Optional.empty().orElseThrow(RuntimeException::new);
            System.out.println(word3);
        }).isInstanceOf(RuntimeException.class);

        assertThatThrownBy(()->{
            Object o = Optional.empty().orElseThrow(() -> new RuntimeException("비어있는 값은 처리할 수 없어요!!!"));
        }).isInstanceOf(RuntimeException.class)
        .hasMessageContaining("처리할 수 없어요!!!");
    }

    @Test
    public void TEST_IF_OPTIONAL_HAS_VALUE_YOU_CAN_USE_OPTIONAL_GET_RIGHT_NOW(){
        Optional<String> notEmpty = Optional.of("TEST");

        if(notEmpty.isEmpty()) return; // 옵셔널에 값이 없으면 리턴해버린다.

        String s = notEmpty.get();  // 이미 값이 있는 경우에만 거치도록 필터링되어 있는 상태다.
        System.out.println(s);
    }

    @Test
    public void OPTIONAL_FILTER(){
        Optional<Integer> odd = Optional.ofNullable(3)
                .filter(num -> num % 2 == 0);

        System.out.println(odd);
        assertThat(odd).isEmpty();

        Optional<Integer> even = Optional.ofNullable(2)
                .filter(num -> num % 2 == 0);
        System.out.println(even);
        assertThat(even).isNotEmpty();
    }

    @Test
    public void OPTIONAL_MAP(){
        String hello1 = "Hello";
        Optional<Integer> optNumber1 = Optional.ofNullable(hello1)
                .map(str -> str.length());
        System.out.println(optNumber1);
        assertThat(optNumber1).isNotEmpty();

        String hello2 = null;
        Optional<Integer> optNumber2 = Optional.ofNullable(hello2)
                .map(str -> str.length());
        System.out.println(optNumber2);
        assertThat(optNumber2).isEmpty();
    }
}