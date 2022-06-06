package io.study.lang.javastudy2022ty1.functional_study.supplier_consumer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SupplierTest {

    @Test
    public void TEST_SUPPLIER_SIMPLE(){
        Supplier<String> helloWorldSupplier = ()-> "hello world~~~~~";

        assertThat(helloWorldSupplier.get())
                .isEqualTo("hello world~~~~~");

        Supplier<String> uuidSupplier = () -> UUID.randomUUID().toString();

        IntStream.range(1, 10)
                .forEach(i -> System.out.println(uuidSupplier.get()));
    }

    @Test
    public void TEST_SUPPLIER_USING_FUNCTION(){
        Supplier<String> uuidSupplier = () -> UUID.randomUUID().toString();
        printUUID(10, uuidSupplier);
    }

    public void printUUID(int count, Supplier<String> supplier){
        IntStream.range(1, count+1)
                .forEach(i -> System.out.println(supplier.get()));
    }
}
