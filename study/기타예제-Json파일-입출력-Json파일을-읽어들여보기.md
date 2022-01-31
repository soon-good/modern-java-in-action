# Json 파일을 읽어들여보기

이번 예제는 저번 글에서 사용한[ JsonObject 를 만들어서 Json 파일로 저장 이라는 예제](https://github.com/soon-good/modern-java-in-action/blob/develop/study/%EA%B8%B0%ED%83%80%EC%98%88%EC%A0%9C-Json%ED%8C%8C%EC%9D%BC-%EC%9E%85%EC%B6%9C%EB%A0%A5-JsonObject%EB%A5%BC-%EB%A7%8C%EB%93%A4%EC%96%B4%EC%84%9C-Json%ED%8C%8C%EC%9D%BC%EB%A1%9C-%EC%A0%80%EC%9E%A5.md)에서 만들었던  json 파일을 읽어들여서 프로그램 내에서 읽어들이는 예제다.<br>

<br>w

## 예제

설명 남길까 했는데 귀찮다ㅋㅋ 

```java
@Test
public void JSON파일을_읽어들여서_Map_리스트로변환해보기(){
  try {
    String rootDirectory = Paths.get(ClassLoader.getSystemResource("json").toURI()).toAbsolutePath().toString();
    String targetDir = Paths.get(rootDirectory, "example").toAbsolutePath().toString();
    String targetFile = Paths.get(targetDir, "nasdaq-count-daily.json").toAbsolutePath().toString();

    FileReader fileReader = new FileReader(new File(targetFile));

    Gson gson = new Gson();
    HashMap hashMap = gson.fromJson(fileReader, HashMap.class);

    List<Map<String, String>> todayDataList = (List<Map<String, String>>)hashMap.get("todayDataList");

    todayDataList.forEach(System.out::println);

  } catch (URISyntaxException | FileNotFoundException e) {
    e.printStackTrace();
  }
}
```

