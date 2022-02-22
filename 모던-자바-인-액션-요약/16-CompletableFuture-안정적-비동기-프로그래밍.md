# Chapter 16. CompletableFuture - 안정적 비동기 프로그래밍

자바 8, 자바 9 에서는 `CompletableFuture` 의 리액티브 프로그래밍 패러다임 두가지 API를 제공한다. 자바8에서 제공해주는 `Future` 의 구현인 `CompletableFuture` 는 비동기 프로그래밍에 매우 많은 도움을 준다.<br>

이 외에도 자바 9 에서 추가된 내용 역시 이곳에서 다루지만, 시간이 허용하는 한도 내에서 정리하고, 정리를 마무리 짓지 못한 자바 9 이후의 내용은 2022년도 가을 이후부터 정리를 시작하게 되지 않을까 싶다.<br>

<br>

> 이 글을 추가할지 결정하는데에 조금 고민을 했다. 개인적인 공간이지만, 별로 좀 이 깃헙 리포지터리에  방문 좀 안해줬으면 하는 사람이 자주 훔쳐보는 것 같다는 기분이 들때가 많다. 굉장히 성가시고 스트레스 받는 일이긴 한데, 이번 문서는 내가 하고 싶어서 정리한거고, 내가 봐야 해서 정리해놓은 문서여서 그냥 이곳에 올리기로 했다. 그냥 좀 인간의 기본권을 이렇게 침해하나 하고 노이로제급의 트라우마가 걸리는 것 같다. <br>
>
> `남의 깃헙 보고 공부할 시간에 니가 좀 공부하면 안되냐? 어떻게 된게 혼자선 아무것도 못하냐?` 하고 물어보고 싶은데, 더 심한 말도 하고 싶은데 그렇게 해봐야 나만 평판이 이상해지는게 아닌가 싶어서 여기까지 적어둬야겠다. 정말 짜증나고 불결한 사람들이다. <br>

<br>

## 참고자료

- [모던자바 인 액션](http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9791162242025&orderClick=LAG&Kc=)
- [자바 병렬 프로그래밍](http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9788960770485&orderClick=LAG&Kc=)
- [자바 트러블 슈팅](http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9791188621828&orderClick=LAG&Kc=)
  - 요즘 읽어보는 책. 스레드 처리에 대해서도 잘 다루고 있고 스레드 덤프 등등 여러가지로 유용한 이야기가 많다.

- [7가지 동시성 모델](http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9788968482984&orderClick=LAG&Kc=#book_info)
  - 모두 읽어보지는 않았지만, 조만간 읽어보게 되지 않을까 싶다.
  - 그때 쯤 되면, 이 리포지터리에 정리를 시작하게 될 듯하다.


<br>

## 참고) 동기 API 와 비동기 API 

`동기 API`

- 메서드를 호출한 이후 메서드가 계산이 완료될 때 까지 기다리고, 메서드의 리턴이 완료된 후 다음 라인을 수행하는 방식
- 동기 API 를 사용하는 상황을 `블록 호출(blocking call)`이라고 부르기도 한다.

`비동기 API`

- 메서드의 리턴을 곧바로 하게끔 하고, 끝내지 못한 작업은 다른 스레드에 할당한다.
- 다른 스레드에 할당된 나머지 계산 결과는 콜백 메서드를 호출해서 '계산결과가 끝날 때 까지 기다림'메서드를 추가로 호출하면서 전달된다.
- 이와 같은 비동기 API를 사용하는 상황을 `비블록 호출(non-blocking call)` 이라고 부른다.
- 주로 I/O 시스템 프로그래밍에서 이와 같은 방식으로 동작을 수행한다. 계산동작을 수행하는 동안 비동기적으로 디스크 접근을 수행한다. 예를 들면, 계산 동작을 수행하는 동안 비동기적으로 디스크 접근을 수행하는 경우를 예로 들 수 있다. 그리고 더 이상 수행할 동작이 없으면 디스크 블록이 메모리로 로딩될 때 까지 기다린다.

<br>

## 16.1 단순한 구조의 `Future` 프로그램

비동기 계산을 모델링 할때 Future 를 사용하는 것을 고려해보는 것이 좋다. Future 는 계산이 끝났을 때 결과에 접근할 수 있는 참조를 제공한다. 시간이 걸릴 수 있는 작업은 Future 내부로 설정하면 호출자 스레드가 결과를 기다리는 동안 다른 유용한 작업을 실행할 수 있다.<br>

비교적 호출하는 메서드나, 로직이 적은 경우에 이런 단순한 Future 를 적용할 수 있다. 책에서는 다른 설명들을 많이 하지만, 그 외에는 그리 크게 정리할 만한 내용이 없었다. 아래는 Future를 단순한 구조로 사용한 프로그램이다. 트래픽이 많은 구조에서 비교적 처리해야 할 작업이 많은 구조에서는 그대로 가져다 사용하기 힘든 구조다.<br>

<br>

```java
ExecutorService executor = Executors.newCahcedThreadPool();

Future<Double> future = executor.submit(new Callable<Double>(){
    public Double call(){
        return doSomeLongComputation(); // 시간이 오래 걸리는 작업은 다른 스레드에서 비동기적으로 실행한다.
    }
});

doSomethingElse(); // 비동기 작업을 수행하는 동안 다른 작업을 한다.

try{
    Double result = future.get(1, TimeUnit.SECONDS);
}
catch(ExecutionException ee){
    // 계산 중 예외 발생
}
catch(InterruptedException ie){
    // 현재 스레드에서 대기 중 인터럽트 발생
}
catch(TimeoutException te){
    // Future 가 완료되기 전에 타임아웃 발생
}
```

<br>

### `CompletableFuture` 가 제공하는 유용한 기능들

`Future` 와 `CompletableFuture` 의 관계는 `Collection` 과 `Stream` 의 관계에 비유할 수 있다. `Future`가 제공하는 기능에 더해 더 복잡한 연산에 대응할 수 있도록 Java 8 부터는 `Future` 를 구현한 `Completable Future` 를 제공하고 있다.<br>

`Future`는 대표적으로 아래의 연산들을 제공하고 있다.<br>

- 비동기 계산이 끝났는지 확인하는 `isDone` 메서드
- 계산이 끝날때까지 기다리는 메서드, 
- 결과 회수 메서드

하지만, 이 메서드 들만으로는 동시성 코드를 작성하기에는 쉽지 않은 편이다. 예를 들면 **'오래 걸리는 A라는 계산이 끝나면 그 결과를 다른 오래 걸리는 계산 B로 전달 하시오. 그리고 B의 결과가 나오면 다른 질의의 결과와 B의 겨로가를 조합하시오'** 와 같은 요구사항을 구현하는 것은 쉽지 않다.<br>

Java 8 부터는 위의 기능들을 유연하게 대응할 수 있도록 `Future` 를 구현한 `CompletableFuture`가 제공되기 시작했다.

 `CompletableFuture` 는 아래의 기능들을 선언형으로 이용할 수 있도록 제공한다. 

- 두 개의 비동기 계싼 결과를 하나로 합친다.

	- 두 가지 계산 결과는 서로 독립적일 수 있으며 또는 두 번째 결과가 첫 번째 결과에 의존하게 되는 경우 역시 있을 수 있다.

- `Future` 집합이 실행하는 모든 태스크의 완료를 기다린다.

- `Future` 집합에서 가장 빨리 완료되는 태스크를 기다렸다가 결과를 얻는다.

- 프로그램 적으로 Future 를 완료시킨다. (즉, 비동기 동작에 수동으로 결과 제공)

- `Future` 의 완료 동작에 반응한다.
- 결과를 기다리면서 블록되지 않고 겨로가가 준비되었다는 알림을 받은 다음에 `Future` 의 결과로 원하는 추가 동작을 수행할 수 있다.

<br>

## 16.1.2 CompletableFuture 로 구현해볼 내용들

1 ) 비동기 API 구현

2 ) 동기 API 를 사용해야 할 때 코드를 비블록으로 만드는 방법을 배운다.

3 ) 비동기 동작의 완료에 대응하는 방법을 배운다.

- 가격 정보를 얻을 때 까지 기다리는 것이 아니라 각 상점에서 가격 정보를 얻을 때마다 즉시 최저가격을 찾는 애플리케이션을 갱신하는 방법을 설명한다.

<br>

## 16.2 비동기 API 구현

16.1 을 들어가기에 앞서 오래 걸리는 작업을 가정하기 위한 메서드들을 만들게 된다.

getPrice, calculatePrice

```java
public double getPrice(String product){
    return calculatePrice(product);
}

private double calculatePrice(String product){
    delay();
    return random.nextDouble() * product.charAt(0) + product.charAt(1);
}
```

<br>

그리고 delay() 메서드의 모습은 아래와 같다.<br>

```java
public static void delay(){
    try{
        Thread.sleep(1000L);
    }
    catch (InterruptedException e){
        throw new RuntimeException(e);
    }
}
```

시간은 직접 조절해가면서 조금 오래 걸리게끔 조절 가능할 것 같다.<br>

<br>

## 16.2.1 동기 메서드를 비동기 메서드로 변환

메서드 시그니처를 아래와 같이 수정해준다.

```java
public Future<Double> getPriceAsync(String product){...}
```

<br>

Java 5 부터는 비동기 계산의 결과를 표현할 수 있는 `java.util.concurrent.Future` 인터페이스를 제공하고 있다. 호출자 스레드(예를 들면 main() 문을 실행시키는 메인스레드)가 블록되지 않고 다른 작업을 실행할 수 있다.<br>

```java
public Future<Double> getPriceAsync(String product){
    CompletableFuture<Double> futurePrice = new CompletableFuture<>();
    new Thread( () -> {
        double price = calculatePrice(product);
        futurePrice.complete(price);
    }).start();

    return futurePRice;
}
```

<br>

Future 는 결과값의 핸들이고, 계산이 완료되면 get 메서드로 결과를 얻을 수 있다. getPriceAsync 메서드는 즉시 반환되기에 호출자 스레드는 다른 작업을 수행하고 있을 수 있다. <br>

비동기 메서드 getPriceAsync(String) 을 호출하는 클라이언트 측의 코드는 아래와 같다.<br>

```java
Shop shop = new Shop("BetsShop");
long start = System.nanoTime();
Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
long invocationTime = ((System.nanoTime() - start) / 1_000_000 );
System.out.println("Invocation retured after " + invocationTime + " msecs");
doSomethingElse();

// 블록이 일어나는 지점
try{
    double price = futurePrice.get();
    System.out.printf("Price is %.2f%n", price);
} 
catch(Exception e){
    throw new RuntimeException(e);
}
long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
System.out.println("Price retured after " + retrievalTime + " msecs");
```

<br>

위 코드의 결과는 아래와 같다.

```plain
Invocation returned after 43 msecs
Price is 123.26
Price retured after 1045 msescs
```

<br>

`getPriceAsync` 메서드가 끝나기까지 기다리지 않고 `Future` 객체르 반환받아 놓은 후에 `future.get()` 메서드가 실행 되기 전에 `doSomethingElse()` 메서드를 실행하면서, `future.get()` 메서드가 1초동안 대기할 동안 `doSomethingElse()`로 다른 작업을 실행하고 있다.<br>

<br>

## 16.2.2 예외 처리

API 를 요청해서 응답을 받아서 처리하는 로직일 경우 Timeout 을 처리하는 것이 좋다. 그래야 문제 발생시 클라이언트가 영원히 블록되지 않고 타임아웃 시간이 지나면 TimeoutException 을 받을 수 있기 때문이다.

```java
public Future<Double> getPriceAsync(String product){
    CompletableFuture<Double> futurePrice = new CompletableFuture<>();
    new Thread(() -> {
        try{
            double price = calculatePrice(product);
            futurePrice.complete(price);
        }
        catch(Exception e){
            futurePrice.completeExceptionally(e);
        }
    }).start();
    return futurePrice;
}
```

<br>

### `supplyAsync` 로 `CompletableFuture` 로직 단순화

위의 코드는 아래와 같이 조금 더 단순하게 변경 가능하다. 아래의 코드의 에러처리는 위에서 정의한 메서드와 에러 처리 기능 역시 모두 같은 방법으로 처리한다.

```java
public Future<Double> getPriceAsync(String product){
    return CompletableFuture.supplyAsync(() -> calculatePrice(product));
}
```

<br>

supplyAsync 메서드는 Supplier를 인수로 받아서 CompletableFuture 를 반환한다. CompletableFuture 는 Supplier 를 실행해서 비동기적으로 결과를 생성한다. ForkJoinPool 의 Executor 중 하나가 Supplier 를 실행하게 된다.<br>

조금 더 다른 버전인 오버로딩된 버전의 supplyAsync 는 Executor 인스턴스를 두번째 파라미터로 받아서 Executor 를 직접 지정 가능하다. 16.3.4 절에서 Executor 를 직접 지정해서 애플리케이션의 성능을 개선하는 예를 든다.<br>

<br>

## 16.3 비블록(`non-block`) 코드 만들기 

아래와 같은 상점 리스트가 있다.

```java
private final List<Shop> shops = Arrays.asList(
  new Shop("BestPrice"),
  new Shop("LetsSaveBig"),
  new Shop("MyFavoriteShop"),
  new Shop("BuyItAll"),
  new Shop("ShopEasy"));
```

<br>

그리고 상점 들의 리스트를 이름만 추출하하는 findPrices 메서드가 있다고 해보자.<br>

위에서 작성했던 findPrice() 메서드를 각 상점 하나마다 호출하고 있다. <br>

```java
public List<String> findPrices(String product){
    return Shops.stream()
        .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
        .collect(toList());
}
```

<br>

예제의 실행 결과는 아래와 같다. 5개 상점들에 대해 findPrice() 하고 있는데, 결과느 4초 가량 걸렸다.<br>

상점 한개를 순회하는 데에 1초가 소요된다. 그리고 이것을 1개의 스레드로 동시성 프로그래밍을 했기에, 1개의 작업 정도만 4개의 상점을 처리하는 작업과 함께 실행된다.<br>

```plain
[BestPrice price is 123.26, LastSaveBig price is 169.47, MyFavoriteShop price is 214.13 BuyItAll price is 184.74]
Done in 4032 msecs
```

<br>

### 16.3.1 `parallelStream` 으로 요청 병렬화하기

```java
public List<String> findPrices(String product){
    return shops.parallelStream()
        .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
        .collect(toList());
}
```

<br>

각 검색을 각자의 스레드에서 실행했다. 그래서 각 상점 당 1 초 씩 실행되었기에 1초 정도의 시간이 걸렸다.

```plain
[BestPrice price is 123.26, LastSaveBig price is 169.47, MyFavoriteShop price is 214.13 BuyItAll price is 184.74]
Done in 1180 msecs
```

<br>

이것을 더 개선해보자.<br>

아래에서부터는 `CompletableFuture` 의 기능을 활용해서 `findPrices` 메서드의 동기 호출을 비동기 호출로 바꾼다.<br>

<br>

## 16.3.2 `CompletableFuture` 로 비동기 호출 구현하기

`parallelStream()` 으로 구현한 로직을 `supplyAsync` 로 작성한 `CompletableFuture` 처리 로직으로 바꿔보면 아래와 같다.<br>

먼저 각 계산 작업들을 처리하는 `Future` 리스트를 얻어내는 로직은 아래와 같다.<br>

```java
List<CompletableFuture<String>> priceFutures = 
    shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))))
        .collect(toList());
```

<br>

이렇게 얻어낸 Future 들을 실제 처리하는 로직은 아래와 같다.

```java
priceFutures.stream()
    .map(CompletableFuture::join)
    .collect(toList());
```

<br>

이 로직을 하나로 합쳐서 하나의 메서드 안에서 수행되도록 작성해보면 아래와 같다.

```java
public List<String> findPrices(String product){
    List<CompletableFuture<String>> priceFutures =
        shops.stream()
            .map(
                shop -> CompletableFuture.supplyAsync(
                    () -> shop.getName() + " price is " + shop.getPrice(product))
            )
            .collect(Collectors.toList());

    return priceFutures.stream()
            .map(CompletableFuture::join)
            .collect(toList());
}
```

<br>

위의 두 map 연산을 하나의 스트림 파이프 라인으로 처리했으면 병목 현상이 나타났을 수도 있다. Future 작업을 생성하는 것은 가급적 병목현상 없이 바로바로 만들어내면 좋다. 그래서 Future를 만들어내는 map 연산과, 직접 계산을 기다리는 join 연산을 하는 map 연산을 따로 분리해냈다. <br>

이 과정을 그림으로 표현하면 아래와 같다. (그림 그려놓기)<br>

## TODO (그림 추가하면 HEADING 제거)

파워포인트로 그림그려야 한다.!!!



<br>

성능을 확인해보면 아래와 같은 결과가 나타난다.

```plain
[BestPrice price is 123.26, LastSaveBig price is 169.47, MyFavoriteShop price is 214.13 BuyItAll price is 184.74]
Done in 2005 msecs
```

<br>

순차적인 방식에 비해서는 빨라졌다. 하지만 paralleStream 을 사용한 구현보다는 아직 2배 느리다.<br>

<br>

## 16.3.3 더 확장성이 좋은 해결 방법

데이터를 더 추가해보면서 확인해보는 과정. skip<br>

<br>

## 16.3.4 커스텀 `executor` 사용

[자바 병렬 프로그래밍](http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9788960770485&orderClick=LAG&Kc=)에서 언급하는 적정 스레드 풀 산출 공식은 이렇다.

`N(thread) = N(cpu) x U(cpu) x (1 + W/C)`

- `N(cpu)` : `Runtime.getRuntime().availableProcessors()` 가 반환하는 코어 수
- `U(cpu)` : 0 ~ 1 사이의 값을 갖는 CPU 활용비율
- `W/C` : 대기시간과 계산 시간의 비율

<br>

우리가 작성하는 애플리케이션은 가격조회시 대부분의 시간을 소모한다. 상점의 응답을 대략 99퍼센트 기다린다고 가정해 `W/C` 비율을 100으로 간주한다.<br>

즉, CPU 활용율이 100 퍼센트라면 400 스레드를 갖는 풀을 만들어야 함을 의미한다.<br>

하지만 상점 수 보다 많은 스레드를 가지고 있어봐야 사용할 가능성이 전혀 없다. 상점 수 보다 많은 스레드를 갖는 것은 낭비다.<br>

따라서 한 상점에 하나의 스레드가 할당될 수 있도록, 가격 정보를 검색하려는 상점 수 만큼 스레드를 갖도록 `Executor` 를 설정한다.<br>

하나의 `Executor` 에서 사용할 스레드의 최대 갯수는 100 이하로 설정하는 것이 바람직하다.<br>

<br>

#### 커스텀 Executor 생성하기

```java
private final Executor executor = 
    Executor.newFixedThreadPool(Math.min(shops.size(), 100, new ThreadFactory () {
        public Thread newThread(Runnable r){
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });
```

<br>

우리가 만드는 풀은 데몬스레드를포함한다. 일반 스레드가 실행중이면 자바 프로그램은 종료되지 않는다. 따라서 어떤 이벤트를 한없이 기다리면서 종료되지 않는 일반 스레드가 있으면 문제가 될 수 있다. 반면 데몬 스레드는 자바 프로그램이 종료될 때 강제로 실행이 종료될 수 있다.<br>

<br>

#### 커스텀 `executor` 기반으로 `CompletableFuture` 실행하기

```java
CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product), executor);
```

<br>

전체 코드를 보면 이렇다.

```java
public List<String> findPrices(String product){
    List<CompletableFuture<String>> priceFutures =
        shops.stream()
            .map(
                shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product), executor)
            )
            .collect(Collectors.toList());

    return priceFutures.stream()
            .map(CompletableFuture::join)
            .collect(toList());
}
```

<br>

커스텀 Executor 를 적용한 CompletableFuture 버전의 코드 성능을 확인해보면 다섯개의 상점을 검색할 때 1021 밀리 초, 아홉개의 상점을 검색할 때는 1022 밀리초가 소요 된다. 이전에 계산한 것 처럼 400개의 상점까지 이같은 성능을 유지할 수 있다. 결국 애플리케이션의 특성에 맞도록 Executor 를 만들어서 CompletableFuture 를 활용하는 것이 바람직하다는 것을 확인할 수 있다. 비동기 동작을 많이 사용하는 상황에서는 이 방법이 가장 효과적일 수 있음을 인지하자.<br>

<br>

#### `parallelStream` 을 사용하는 방식 vs `CompletableFuture` 병렬화 방식

> `parallelStream` 과 `CompletableFuture` 의 차이점은 책에서 언급하는 주제다. `parallelStream` 과 `CompletableFuture` 를 비교하고 있다. <br>
>
> 인터넷을 찾아보면 `parallelStream` 을 사용하면서 접한 서비스 운영시의 장애 경험에 대해 굉장히 많은 글들을 찾아볼 수 있다. <br>
>
> 책에서는 굉장히 간접적으로 언급하는데, `parallelStream` 을 가급적 IO 가 있는 네트워크 처리 등에 사용하지 말고 가급적 계산작업에 사용하라고 하는 듯한 의견을 굉장히 간접적으로 이야기해주고 있다.<br>

<br>

지금까지 컬렉션을 병렬화하는 두 가지 방법을 살펴봤다. 하나는 `parllelStream` 을 사용해 컬렉션을 처리하는 방식이고, 다른 하나는 컬렉션을 반복하면서 `CompleteFuture` 내부 연산으로 만드는 방식이다. `CompletableFuture` 를 이용하면 전체적인 계산이 블록되지 않도록 스레드 풀의 크기를 조절할 수 있다.<br>

- `parallelStream` 사용 방식
	- I/O 작업이 없고 `Pending` 상태가 없는 연산일 경우는 `parallelStream` 을 사용하는 것이 오히려 구현상 간단하고 효율적일 수 있다.
	- 모든 스레드가 I/O 작업 없이 계산 작업을 수행하는 상황에서는 프로세서 코어 수 이상의 스레드를 가질 필요가 없다.
	- I/O가 많은 작업을 `parallelStream` 으로 사용하면 스트림의 게으른 특성 때문에 스트림에서 I/O 를 실제로 언제 처리할 지 예측하기 어려운 문제 역시 존재한다.
	
- `CompletableFuture` 병렬화 방식
	- I/O 작업을 병렬로 실행할 때는 `CompletableFuture` 가 더 많은 유연성을 제공한다.
	- 대기/계산(W/C) 의 비율에 적합한 스레드 수를 설정할 수 있다.





















































