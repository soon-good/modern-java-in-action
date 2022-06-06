package io.study.lang.javastudy2022ty1.functional_study.supplier_consumer;

import io.study.lang.javastudy2022ty1.functional_study.supplier_consumer.pojo.StockEarningValuation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class ComparatorTest {

    @Test
    public void TEST_COMPARATORS(){
        List<StockEarningValuation> earningValuations = Arrays.asList(
                newEarningData("TSLA", new BigDecimal("95.10"), new BigDecimal("44.26")),
                newEarningData("TSM", new BigDecimal("21.53"), new BigDecimal("14.81")),
                newEarningData("LULU", new BigDecimal("40.11"), new BigDecimal("27.19")),
                newEarningData("FB", new BigDecimal("14.44"), new BigDecimal("13.64"))
        );

        Consumer<List<StockEarningValuation>> sysoutConsumer =
                l-> l.forEach(System.out::println);

        System.out.println("PER SORT RESULT >> ");
        Comparator<StockEarningValuation> perComparator = (p1, p2) -> p1.getPer().subtract(p2.getPer()).intValue();
        Collections.sort(earningValuations, perComparator);
        sysoutConsumer.accept(earningValuations);
        System.out.println();

        System.out.println("Foward PER SORT RESULT >> ");
        Comparator<StockEarningValuation> forwardPerComparator = (p1, p2) -> p1.getForwardPer().subtract(p2.getForwardPer()).intValue();
        Collections.sort(earningValuations, forwardPerComparator);
        sysoutConsumer.accept(earningValuations);
        System.out.println();

        System.out.println("Ticker SORT RESULT >> ");
        Comparator<StockEarningValuation> tickerComparator = (p1, p2) -> p1.getTicker().compareTo(p2.getTicker());
        Collections.sort(earningValuations, tickerComparator);
        sysoutConsumer.accept(earningValuations);
        System.out.println();
    }

    public StockEarningValuation newEarningData(String ticker, BigDecimal per, BigDecimal forwardPer){
        return StockEarningValuation.builder()
                .ticker(ticker)
                .per(per)
                .forwardPer(forwardPer)
                .build();
    }
}
