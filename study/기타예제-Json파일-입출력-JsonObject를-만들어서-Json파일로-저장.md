# JsonObject 를 만들어서 Json 파일로 저장

라이브러리로는 어떤것을 쓸까 하다가 이것 저것 귀찮게 생각하기 싫어서 `com.google.gson` 라이브러리를 사용했다. 다른 라이브러리로도 대부분 잘 동작한다. 과정을 요약해보면 이렇다.

- JsonObject 를 만든다
- 만든 JsonObject 를 stringify
- File I/O 를 통해 파일로 저장

<br>

## 예제

자세한 내용은 나중에 정리하거나, 귀찮으면 스킵해야지.

<br>

```java
@Test
public void 매우_간단한_JSON데이터_파일저장_테스트() throws IOException {

  // 샘플 데이터
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

  JsonObject jsonObject = new JsonObject();
  JsonArray jsonArray = new JsonArray();

  // 저장할 데이터는 sortedMap 이다.
  // Array 에 담아서 저장하자.
  sortedMap.entrySet()
    .stream()
    .forEachOrdered( map -> {
      Long count = map.getValue();
      JsonObject item = new JsonObject();
      item.addProperty("ticker", map.getKey());
      item.addProperty("count", count);

      jsonArray.add(item);
    });

  jsonObject.add("todayDataList", jsonArray);

  FileWriter fileWriter = null;

  try {
    Path rootPath = Paths.get(ClassLoader.getSystemResource("json").toURI())
      .toAbsolutePath();

    Path targetPath = Paths.get(rootPath.toString(), "example").toAbsolutePath();

    if(Files.exists(targetPath)){
      String targetFilePath = Paths.get(targetPath.toString(), "nasdaq-count-daily.json").toAbsolutePath().toString();

      fileWriter = new FileWriter(targetFilePath);
      fileWriter.write(jsonObject.toString());
      fileWriter.flush();
      fileWriter.close();
    }
  } catch (URISyntaxException e) {
    e.printStackTrace();
    fileWriter.close();
  }
}
```

<br>

## 파일 저장 결과

`json/example/nasdaq-count-daily.json` 

```java
{"todayDataList":[{"ticker":"NVDA","count":3},{"ticker":"MSFT","count":1},{"ticker":"AAPL","count":1}]}
```

<br>

