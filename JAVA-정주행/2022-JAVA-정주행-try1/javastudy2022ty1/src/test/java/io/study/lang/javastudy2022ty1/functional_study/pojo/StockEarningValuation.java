package io.study.lang.javastudy2022ty1.functional_study.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class StockEarningValuation {
    private final String ticker;
    private final BigDecimal per;
    private final BigDecimal forwardPer;
    private final BigDecimal eps;

    @Builder
    public StockEarningValuation(
        String ticker, BigDecimal per, BigDecimal forwardPer, BigDecimal eps
    ){
        this.ticker = ticker;
        this.per = per;
        this.forwardPer = forwardPer;
        this.eps = eps;
    }
}
