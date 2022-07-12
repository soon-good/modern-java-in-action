# thenComsumeWhile() - Retrieve all flux elements in StepVerifier

> 참고자료 : [spring webflux - Retrieve all flux elements in StepVerifier - Stack Overflow](https://stackoverflow.com/questions/57221204/retrieve-all-flux-elements-in-stepverifier) 

<br>

개인적으로 사이드프로젝트를 진행 중에 테스트코드를 작성하다가, StepVerifier 로 Flux를 하나씩 열어서 검증해야 했는데, 여기서 Flux가 기다려주지 않고 종료를 하기에 관련 자료를 찾아봤었다.<br>

<br>

thenConsumeWhile() 메서드를 사용하면 되는 것으로 보이는데, 예를 들어 AssertJ로 검증하려고 할 경우는 아래와 같이 작성해주면 된다.

```java
StepVerifier.create(flux)
        .recordWith(ArrayList::new)
        .thenConsumeWhile(x -> true)
        .consumeRecordedWith(elements -> {
            assertThat(elements.isEmpty()).isFalse();
        })
        .verifyComplete();
```

<br>

AssertJ 가 라이브러리로 추가되어 있지 않거나, 굳이 필요하지 않다면, 아래와 같이 작성하면 된다.

> expectRecordedMatches(predicate) 를 사용했다.

```java
StepVerifier.create(flux)
        .recordWith(ArrayList::new)
        .thenConsumeWhile(x -> true)
        .expectRecordedMatches(elements -> !elements.isEmpty())
        .verifyComplete();
```

<br>

