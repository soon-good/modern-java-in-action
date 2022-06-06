package io.study.lang.javastudy2022ty1.functional_study.supplier_consumer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class ConsumerTest {

    @Test
    public void TEST_CONSUMER_SIMPLE(){
        Consumer<String> printStringConsumer = System.out::println;
        printStringConsumer.accept(UUID.randomUUID().toString());
    }

    @Test
    public void TEST_CONSUMER_SIMPLE1(){
        List<String> uuidList = Arrays.asList(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );

        Consumer<String> printStringConsumer = str -> System.out.println(str);

        printUUIDList(uuidList, printStringConsumer);
    }

    public void printUUIDList(List<String> uuidList, Consumer<String> stringConsumer){
        uuidList.stream().forEach(uuid -> stringConsumer.accept(uuid));
    }

    @Test
    public void TEST_CONSUMER_SIMPLE2(){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Consumer<Integer> numberConsumer = number -> System.out.println(number);
        printAnyTypeList(numbers, numberConsumer);
    }

    public static <T> void printAnyTypeList(List<T> list, Consumer<T> consumer){
        list.stream().forEach(element -> consumer.accept(element));
    }
}
