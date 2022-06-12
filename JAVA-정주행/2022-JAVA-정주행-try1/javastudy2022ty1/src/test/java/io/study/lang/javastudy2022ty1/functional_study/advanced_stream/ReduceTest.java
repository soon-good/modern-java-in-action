package io.study.lang.javastudy2022ty1.functional_study.advanced_stream;

import io.study.lang.javastudy2022ty1.functional_study.pojo.Eps;
import io.study.lang.javastudy2022ty1.functional_study.pojo.PeriodType;
import io.study.lang.javastudy2022ty1.functional_study.pojo.StockEarningValuation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ReduceTest {
    @Test
    public void TEST1_1번_리듀스_메서드_합구하기(){
        int result = Stream.of(100, 200, 300, 400, 500)
                .reduce((x,y)-> x + y)
                .get();

        assertThat(result).isEqualTo(1500);
        System.out.println(result);
    }

    @Test
    public void TEST1_1번_리듀스_메서드_최대값구하기(){
        int result = Stream.of(100, 200, 300, 400, 500)
                .reduce((x,y) -> x>y ? x : y)
                .get();

        assertThat(result).isEqualTo(500);
    }

    @Test
    public void TEST2_2번_리듀스_메서드_모든요소의_합구하기(){
        int result = Stream.of(100, 200, 300, 400, 500)
                .reduce(0, (x,y) -> x+y);

        assertThat(result).isEqualTo(1500);
    }

    @Test
    public void TEST3_3번_리듀스_메서드_모든요소의_합구하기(){
        int result = Stream.of("100", "200", "300", "400", "500")
                .reduce(0, (x,str)-> x + Integer.parseInt(str), (x,y)->x+y);

        assertThat(result).isEqualTo(1500);
    }

    @Test
    public void TEST_SAMPLE(){
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

        List<Eps> list = Arrays.asList(msft, tsm, lulu);

        BigDecimal epsTotal = list.stream()
                .flatMap(eps -> eps.getEpsHistory().stream())
                .map(eps -> eps.getEps())
                .reduce(BigDecimal.ZERO, (x, y) -> x.add(y));

        System.out.println("epsTotal = " + epsTotal);

        BigDecimal epsMax = list.stream()
                .flatMap(eps -> eps.getEpsHistory().stream())
                .map(eps->eps.getEps())
                .reduce(BigDecimal.ZERO, (x,y) -> x.compareTo(y) > 0 ? x : y);

        System.out.println("epsMax = " + epsMax);

        Optional<Eps> epsMax2 = list.stream()
                .flatMap(eps -> eps.getEpsHistory().stream())
                .reduce((eps1, eps2) -> eps1.getEps().compareTo(eps2.getEps()) > 0 ? eps1 : eps2);

        System.out.println("epsMax2 = " + epsMax2.get());
    }

    public Eps newEps(PeriodType periodType, String time, String ticker, BigDecimal eps){
        return Eps.builder()
                .ticker(ticker)
                .periodType(periodType)
                .time(time)
                .eps(eps)
                .build();
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
