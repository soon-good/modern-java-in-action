package io.study.lang.javastudy2022ty1.effectivejava_temp.item72;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WellKnownExceptionsTest {
    @Test
    public void test(){
        Map<String, String> map = new HashMap<>();

        map.put("1", "100");
        map.put("2", "200");
        map.put("3", "300");

        map.entrySet()
                .stream()
                .forEach(e -> map.remove(e.getKey()));
    }
}
