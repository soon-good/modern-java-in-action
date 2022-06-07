# Collector 예제 (1) - Map

#### IntStream 을 Map 으로 변환하기

> 참고 : [Convert IntStream to Map](https://stackoverflow.com/questions/38318181/convert-intstream-to-map)<br>

```java
public class SampleMain{
    public static void main(String [] args){
        Map<Integer, Integer> map = 
            IntStream.range(1,10)
            	.boxed()
            	.collect(Collctors.toMap(
                    Function.identity(), 
                    i -> i*2
                ));
    }
}
```



#### Enum : Integer 타입으로 변환

##### 예제 (1)

**GainLossDto.java**

```java
@ToString
public class GainLossDto {
    @Getter private final GainLossColumn type;
    @Getter private final List<BigDecimal> values;

    @Builder
    public GainLossDto(GainLossColumn type, List<BigDecimal> values){
        this.type = type;
        this.values = values;
    }
}
```

<br>

**GainLossColumn.java**

```java
package io.inspect.koreanstockinspector.tdd.fnguide.finance.gainloss;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum GainLossColumn {
    TotalProfit("TotalProfit", "매출액"),
    OperatingProfit("OperatingProfit", "영업이익"),
    NetIncome("NetIncome", "당기순이익");

    @Getter private final String colEn;
    @Getter private final String colKr;

    GainLossColumn(String colEn, String colKr){
        this.colEn = colEn;
        this.colKr = colKr;
    }

    static Map<String, GainLossColumn> colKrMap = new HashMap<>();

    static {
        for(GainLossColumn col : GainLossColumn.values()){
            colKrMap.put(col.getColKr(), col);
        }
    }

    public static GainLossColumn krTypeOf(String krLabel){
        return colKrMap.get(krLabel);
    }
}

```

```java
@Test
public void TEST(){
    GainLossDto netIncome = GainLossDto.builder()
        .type(GainLossColumn.NetIncome)
        .values(List.of(new BigDecimal("111"), new BigDecimal("222"), new BigDecimal("333")))
        .build();

    GainLossDto totalProfit = GainLossDto.builder()
        .type(GainLossColumn.TotalProfit)
        .values(List.of(new BigDecimal("444"), new BigDecimal("555"), new BigDecimal("666")))
        .build();

    GainLossDto salesProfit = GainLossDto.builder()
        .type(GainLossColumn.OperatingProfit)
        .values(List.of(new BigDecimal("777"), new BigDecimal("888"), new BigDecimal("999")))
        .build();

    List<GainLossDto> values = List.of(netIncome, totalProfit, salesProfit);
    Map<GainLossColumn, Integer> columnIndexMap = IntStream.range(0, values.size())
        .boxed()
        .collect(Collectors.toMap(i -> values.get(i).getType(), Function.identity()));

    System.out.println(columnIndexMap);
}
```

<br>

출력결과<br>

```plain
{OperatingProfit=2, TotalProfit=1, NetIncome=0}
```

<br>

##### 예제 (2)

> 더 단순한 예제로. List\<Menu\> 를 예로 들어서 해보자.













