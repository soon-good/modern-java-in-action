package io.study.lang.javastudy2022ty1.functional_study.advanced_stream;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupingByTest {

    @Test
    public void SIMPLE_GROUPING_BY(){
        Map<Integer, List<Integer>> groupByEven = Stream.of(101, 200, 303, 400, 505)
                .collect(Collectors.groupingBy(x -> x % 2));

        System.out.println(groupByEven);
    }

    @Test
    public void TEST_COLLECTORS_TYPE2(){
        Map<Integer, Set<Integer>> result =
                Stream.of(101, 200, 303, 400, 505, 101, 200, 303)
                .collect(Collectors.groupingBy(x -> x % 2, Collectors.toSet()));

        System.out.println(result);
    }
}
