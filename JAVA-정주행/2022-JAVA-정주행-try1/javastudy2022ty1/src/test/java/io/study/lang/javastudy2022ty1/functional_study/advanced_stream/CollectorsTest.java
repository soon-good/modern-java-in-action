package io.study.lang.javastudy2022ty1.functional_study.advanced_stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsTest {

    @Test
    @DisplayName("Stream 을 Collectors 를 이용해 List 로 변환")
    public void TEST_COLLECTORS_TO_LIST(){
        // 숫자들이 나열된 스트림을 Integer List 로 변환
        List<Integer> list = Stream.of(99, -1, 5, 3, 2)
                .collect(Collectors.toList());

        System.out.println(list);
    }

    @Test
    @DisplayName("Stream 을 Collectors 를 이용해 Set 으로 변환")
    public void TEST_COLLECTORS_TO_SET(){
        // 숫자들이 나열된 스트림을 Integer Set 으로 변환
        Set<Integer> set = Stream.of(99, -1, 5, 3, 2)
                .collect(Collectors.toSet());

        System.out.println(set);
    }

    @Test
    @DisplayName("Collectors 의 mapping 을 사용해보기")
    public void TEST_COLLECTORS_MAPPING(){
        // Collectors.mapping 을 사용해보기
        List<Integer> mapping_toList1 = Stream.of(99, -1, 5, 3, 2)
                .collect(Collectors.mapping(x -> Math.abs(x), Collectors.toList()));

        System.out.println("list = " + mapping_toList1);

        // Collectors.mapping 을 사용한 것을 Set으로
        Set<Integer> mapping_toSet = Stream.of(99, -1, 5, 1, 2)
                .collect(Collectors.mapping(x -> Math.abs(x), Collectors.toSet()));

        System.out.println("toSet with Collectors.mapping() >> " + mapping_toSet);

        // x -> x 는 Function.identity() 바꾸는 것이 가능하다.
        List<Integer> mapping_toList4 = Stream.of(99, -1, 5, 3, 2)
                .collect(Collectors.mapping(Function.identity(), Collectors.toList()));
    }

    @Test
    @DisplayName("Collectors 의 reducing 을 사용해보기")
    public void TEST_COLLECTORS_REDUCING(){
        Integer sum = Stream.of(99, -1, 3, 5, 1)
                .collect(Collectors.reducing(0, (x, y) -> x + y));
        System.out.println(sum);
        // Collectors.reducing(T, BinaryOperator)
                // BinaryOperator ...
    }
}
