package io.study.lang.javastudy2022ty1.functional_study.supplier_consumer;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class BiConsumerTest {

    @Test
    public void TEST_PRINT(){
        List<String> uuidList = List.of(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );

        BiConsumer<String, Long> biConsumer = (s, n) -> {
            StringBuilder builder = new StringBuilder();

            builder.append("data : ").append(s).append(",  ")
                    .append("time(ms) : ").append(n);

            System.out.println(builder.toString());
        };

        print(uuidList, biConsumer);
    }

    public static <T> void print(List<T> data, BiConsumer<T, Long> biConsumer){
        data.forEach(uuid -> {
            biConsumer.accept(uuid, System.nanoTime());
        });
    }
}
