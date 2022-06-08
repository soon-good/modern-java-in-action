package io.study.lang.javastudy2022ty1.functional_study.supplier_consumer;

import io.study.lang.javastudy2022ty1.functional_study.supplier_consumer.pojo.Eps;
import io.study.lang.javastudy2022ty1.functional_study.supplier_consumer.pojo.PeriodType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FlatMapTest {

    public Eps newEps(PeriodType periodType, String time, String ticker, BigDecimal eps){
        return Eps.builder()
                .ticker(ticker)
                .periodType(periodType)
                .time(time)
                .eps(eps)
                .build();
    }

    @Test
    public void TEST_EARNING(){
        List<Eps> msftEpsHistory = Arrays.asList(
                newEps(PeriodType.QUARTERLY, "2022/1Q", "MSFT", BigDecimal.valueOf(2.22)),
                newEps(PeriodType.QUARTERLY, "2021/4Q", "MSFT", BigDecimal.valueOf(2.48)),
                newEps(PeriodType.QUARTERLY, "2021/3Q", "MSFT", BigDecimal.valueOf(2.27)),
                newEps(PeriodType.QUARTERLY, "2021/2Q", "MSFT", BigDecimal.valueOf(2.17))
        );

        Eps msft = Eps.builder()
                .ticker("MSFT")
                .eps(msftEpsHistory.get(0).getEps())
                .epsHistory(msftEpsHistory)
                .build();

        List<Eps> tsmEpsHistory = Arrays.asList(
                newEps(PeriodType.QUARTERLY, "2022/1Q", "TSM", BigDecimal.valueOf(1.39)),
                newEps(PeriodType.QUARTERLY, "2021/4Q", "TSM", BigDecimal.valueOf(1.15)),
                newEps(PeriodType.QUARTERLY, "2021/3Q", "TSM", BigDecimal.valueOf(1.08)),
                newEps(PeriodType.QUARTERLY, "2021/2Q", "TSM", BigDecimal.valueOf(0.93))
        );

        Eps tsm = Eps.builder()
                .ticker("TSM")
                .eps(tsmEpsHistory.get(0).getEps())
                .epsHistory(tsmEpsHistory)
                .build();

        List<Eps> luluEpsHistory = Arrays.asList(
                newEps(PeriodType.QUARTERLY, "2022/1Q", "LULU", BigDecimal.valueOf(1.48)),
                newEps(PeriodType.QUARTERLY, "2021/4Q", "LULU", BigDecimal.valueOf(3.37)),
                newEps(PeriodType.QUARTERLY, "2021/3Q", "LULU", BigDecimal.valueOf(1.62)),
                newEps(PeriodType.QUARTERLY, "2021/2Q", "LULU", BigDecimal.valueOf(1.65))
        );

        Eps lulu = Eps.builder()
                .ticker("LULU")
                .eps(luluEpsHistory.get(0).getEps())
                .epsHistory(luluEpsHistory)
                .build();

        Comparator<Eps> epsComparator = (left, right) -> left.getEps().compareTo(right.getEps());

        List<Eps> allEpsData = Arrays.asList(msft, tsm, lulu)
                .stream()
                .flatMap(eps -> eps.getEpsHistory().stream())
                .sorted(epsComparator.reversed())
                .limit(3)
                .collect(Collectors.toList());

        System.out.println(allEpsData);
    }
}
