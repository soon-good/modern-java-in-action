package io.study.lang.javastudy2022ty1.effectivejava_temp.item60;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class FloatDoubleDontUseTest {

    @Test
    public void 간단한_뺄셈연산에_사용해보기_1(){
        System.out.println(1.03 - 0.42);
    }

    @Test
    public void 간단한_뺄셈연산에_사용해보기_2(){
        System.out.println(1.00 - 9 * 0.10);
    }

    @Test
    public void 간단한_뺄셈연산에_사용해보기_3(){
        double funds = 1.00;
        int itemBought = 0;
        for(double price = 0.10; funds >= price; price += 0.10){
            funds -= price;
            itemBought++;
        }

        System.out.println(itemBought + "개 구입");
        System.out.println("잔돈(달러) : " + funds);
    }

    @Test
    public void 간단한_뺄셈연산에_사용해보기_4(){
        final BigDecimal TEN_CENTS = new BigDecimal(".10");

        int itemBought = 0;
        BigDecimal funds = new BigDecimal("1.00");
        for(BigDecimal price = TEN_CENTS; funds.compareTo(price) >= 0; price = price.add(TEN_CENTS)){
            funds = funds.subtract(price);
            itemBought++;
        }

        System.out.println(itemBought + " 개 구입");
        System.out.println("잔돈(달러) : " + funds);
    }

    @Test
    public void 간단한_뺄셈연산에_사용해보기_5(){
        int itemBought = 0;
        int funds = 100;
        for(int price = 10; funds >= price; price += 10){
            funds -= price;
            itemBought++;
        }
        System.out.println(itemBought + "개 구입");
        System.out.println("잔돈(센트) : " + funds);
    }
}
