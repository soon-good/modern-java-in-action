package io.study.lang.javastudy2022ty1.functional_study.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@ToString
public class Eps {
    private final PeriodType periodType;
    private final String time;
    private final String ticker;
    private final BigDecimal eps;
    private final List<Eps> epsHistory;

    @Builder
    public Eps(PeriodType periodType, String time, String ticker, BigDecimal eps, List<Eps> epsHistory){
        this.periodType = periodType;
        this.time = time;
        this.ticker = ticker;
        this.eps = eps;
        this.epsHistory = epsHistory;
    }
}
