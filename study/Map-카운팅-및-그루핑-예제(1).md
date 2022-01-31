# Map 카운팅 및 그루핑 예제 (1) 

## Reference

- [javaprogramto.com - Java 8 Stream Group By Counting](https://www.javaprogramto.com/2021/06/java-8-stream-group-by-count.html)
- [mkyong.com - Stream Collectors grouping - By examples](https://mkyong.com/java8/java-8-collectors-groupingby-and-mapping-example/)

<br>

## SUMMARY

`Collectors.groupingBy()`

- `Collectors.counting()` , `Collectors.identify()` 를 인자로 받는다.

`Function.identity()`

- 인자로 넘어온 것을 받아서 그대로 반환

`Collectors.counting()`

- 1씩 증가하는 카운팅 함수

<br>

## `List<String>` 카운팅

```java
public class SomeTest{
  
  @Test
  public void testSomething1(){
    List<String> tickers = new ArrayList<>();
    tickers.add("AAPL");
    tickers.add("ICLN");
    tickers.add("MSFT");
    tickers.add("NVDA");
    tickers.add("TSLA");
    
    Map<String, Long> result = tickers.stream()
      .collect(Collectors.groupingBy(Function.identy(), Collectors.counting()));
    
    System.out.println("Group By Count = " + result);
  }
}
```

<br>

## `List<Map<String, String>>` 카운팅

아주 간단한 몸풀이 용도의 카운팅 예제다. 각 주식 데이터의 출현빈도를 체크하는 카운팅 예제다.

```java
@Test
public void 매우_간단한_카운팅_테스트_1(){
  Map<String,String> data1 = new HashMap<>();
  data1.put("ticker", "NVDA");

  Map<String,String> data2 = new HashMap<>();
  data2.put("ticker", "NVDA");

  Map<String,String> data3 = new HashMap<>();
  data3.put("ticker", "NVDA");

  Map<String,String> data4 = new HashMap<>();
  data4.put("ticker", "MSFT");

  Map<String,String> data5 = new HashMap<>();
  data5.put("ticker", "AAPL");

  List<Map<String, String>> dataList = Arrays.asList(data1, data2, data3, data4, data5);

  Map<String, Long> tickerCountMap = dataList.stream()
    .map(map -> {
      return map.get("ticker");
    })
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

  System.out.println("tickerCountMap = > " + tickerCountMap);
}
```

<br>

출력결과

```plain
tickerCountMap = > {MSFT=1, NVDA=3, AAPL=1}
```

<br>

## 카운팅 한 것을 sorting

이번엔 출현 빈도를 역순으로 정렬해서 출력하는 것을 해보자.

```java
@Test
public void 매우_간단한_카운팅_테스트_2(){
  Map<String,String> data1 = new HashMap<>();
  data1.put("ticker", "NVDA");

  Map<String,String> data2 = new HashMap<>();
  data2.put("ticker", "NVDA");

  Map<String,String> data3 = new HashMap<>();
  data3.put("ticker", "NVDA");

  Map<String,String> data4 = new HashMap<>();
  data4.put("ticker", "MSFT");

  Map<String,String> data5 = new HashMap<>();
  data5.put("ticker", "AAPL");

  List<Map<String, String>> dataList = Arrays.asList(data1, data2, data3, data4, data5);

  // 1) 카운팅
  Map<String, Long> tickerCountMap = dataList.stream()
    .map(map -> {
      return map.get("ticker");
    })
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

  System.out.println("tickerCountMap = > " + tickerCountMap);

  // 2) 카운팅 결과를 sorting
  Map<String, Long> sortedMap = new LinkedHashMap<>();

  tickerCountMap.entrySet().stream()
    .sorted(
    Map.Entry.<String, Long>comparingByValue()
    .reversed()
  )
    .forEachOrdered(
    e -> sortedMap.put(e.getKey(), e.getValue())
  );

  System.out.println(sortedMap);
}
```

<br>

출력결과

```plain
tickerCountMap = > {MSFT=1, NVDA=3, AAPL=1}
{NVDA=3, MSFT=1, AAPL=1}
```









