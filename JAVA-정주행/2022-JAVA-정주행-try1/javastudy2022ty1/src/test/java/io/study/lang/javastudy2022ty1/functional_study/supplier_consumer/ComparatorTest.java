package io.study.lang.javastudy2022ty1.functional_study.supplier_consumer;

import io.study.lang.javastudy2022ty1.functional_study.supplier_consumer.pojo.Eps;
import io.study.lang.javastudy2022ty1.functional_study.supplier_consumer.pojo.PeriodType;
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
            newEarningData("TSLA", BigDecimal.valueOf(95.10), BigDecimal.valueOf(44.26), BigDecimal.valueOf(7.40)),
            newEarningData("TSM", BigDecimal.valueOf(21.53), BigDecimal.valueOf(14.81), BigDecimal.valueOf(4.36)),
            newEarningData("LULU", BigDecimal.valueOf(40.11), BigDecimal.valueOf(27.19), BigDecimal.valueOf(7.50)),
            newEarningData("FB", BigDecimal.valueOf(14.44), BigDecimal.valueOf(13.64), BigDecimal.valueOf(13.21))
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

    public StockEarningValuation newEarningData(String ticker, BigDecimal per, BigDecimal forwardPer, BigDecimal eps){
        return StockEarningValuation.builder()
                .ticker(ticker)
                .per(per)
                .forwardPer(forwardPer)
                .eps(eps)
                .build();
    }
}
