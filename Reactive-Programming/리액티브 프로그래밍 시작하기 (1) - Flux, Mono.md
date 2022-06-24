# 리액티브 프로그래밍 시작하기 (1) - Flux, Mono



# 환경세팅

gradle 프로젝트 생성. 의존성은 아래와 같이 추가<br>

<br>

```groovy
plugins{
    id 'java'
    id 'eclipse'
}

group 'io.study'
version '1.0-SNAPSHOT'
sourceCompatibility = 11

repositories {
    mavenCentral()
}

dependencies {
    // reactor
    implementation 'io.projectreactor:reactor-core:3.4.19'
    implementation 'org.projectlombok:lombok:1.18.24'
    implementation 'com.github.javafaker:javafaker:1.0.2'

    // junit-jupiter
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.2'
    // reactor
    testImplementation 'io.projectreactor:reactor-test:3.4.19'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.7.2'
}

test {
    useJUnitPlatform()
}
```

<br>

실행시 아래와 같은 에러가 발생할 수 있는데, Add '--add-modules java.base' to module compiler options 버튼을 클릭해서, 컴파일러 옵션을 추가해주자. (intellij 에서만 발생하는 이슈인 듯 해보인다.)<br>

![1](./img/0-SETTING/1.png)

<br>

# Mono 기초

## Mono.just

**예제**<br>

Mono.just 를 단순히 선언한다고 해서 바로 선언할 수 있는 것은 아니다. Mono 를 선언했다는 것은 하나의 데이터의 흐름을 만들어놓은 것이다. (java 의 Stream 과 유사한 개념.)

```java
@Test
public void MONO_JUST_TEST(){
    Mono<Integer> one = Mono.just(1);
    System.out.println(one);
}
```

**출력결과**<br>

```plain
MonoJust
```

<br>

## Mono.just 로 생성된 데이터의 흐름을 subscribe 하기

**예제**<br>

특이한점은 Mono, Flux 는 Stream 과는 다르게, 한번 소모한 것을 재사용할 수 있다. 

>  참고로, 컬렉션과 Stream은 다른 개념이다. Stream 은 컬렉션으로부터 생성할 수 있는 데이터의 흐름이다. 보통은 함수의 입출력으로는 Stream을 반환하거나, 인자값으로 받지 않도록 권장하는 편이다. (참고: Effective Java)

```java
@Test
public void MONO_JUST_TEST2(){
    Mono<Integer> mono = Mono.just(1);
    mono.subscribe(i->System.out.println(i));
    // 또는 아래와 같이 간결하게 메서드 레퍼런스로도 가능
    mono.subscribe(System.out::println);
}
```

<br>

**출력결과**

```plain
1
1
```

<br>

Mono.just 의 인자값을 잠시 살펴보면 아래와 같은 것들이 있다. 

![1](./img/1-FLUX-MONO/1.png)

<br>

## Mono.subscribe

subscribe 메서드를 사용할 때 보통 아래 3가지의 인자값들을 기본으로 하는 subscribe 메서드를 주로 사용하는 편이다.

- onNext
  - `Consumer<T>` 
- onError 와 같은 역할을 하는 각각의 Consumer 객체를 전달하는 편이다.
  - `Consumer<Throwable>`
- onComplete
  - `Runnable` 

<br>

**예제**<br>

```java
@Test
public void MONO_JUST_TEST3(){
    Mono<Integer> mono = Mono.just(2);

    mono.subscribe(
        number -> System.out.println(number),
        throwable -> System.out.println(throwable.getMessage()),
        () -> System.out.println("Complete!!")
    );
}
```

<br>

**출력결과**<br>

```plain
2
Complete!!
```

<br>

## onError

일부러 에러가 나게끔 하고, 에러가 날 경우의 출력결과를 살펴보기 위해 아래의 코드를 살펴보자.

```java
@Test
public void ON_ERROR_TEST1(){
    Mono<Integer> hello = Mono.just("hello")
        .map(String::length)
        .map(i -> i / 0);

    hello.subscribe(
        number -> System.out.println(number),
        throwable -> System.out.println(throwable.getMessage()),
        () -> System.out.println("Complete!!")
    );
}
```

<br>

출력결과는 아래와 같이 에러에 대한 메시지를 출력하게 된다.

```plain
/ by zero
```

<br>

이번에는 아래와 같이 `onError` 없이 일반 메서드만 호출하도록 해보자. 이렇게 하는 이유는 onError 시의 에러처리를 지정해주는 것이 왜 좋은지를 직접 확인하기 위해서다.<br>

```java
@Test
public void ON_ERROR_TEST2_WITHOUT_ONERROR(){
    Mono<Integer> hello = Mono.just("hello")
        .map(String::length)
        .map(i -> i / 0);

    hello.subscribe(
        number -> System.out.println(number)
    );
}
```

<br>

출력결과는 아래와 같에 예외가 발생한다.

![1](./img/1-FLUX-MONO/2.png)

<br>

## Emitting Empty

Mono는 Optional.empty() 와 비슷한 역할을 하는 데이터를 내보낼 수 있다. 아주 편리하게 쓰이겠다 싶었다.

```java
@Test
public void EMITTING_EMPTY(){
    emptyData(1).subscribe(
        Util.onNext(),
        Util.onError(),
        Util.onComplete()
    );
}

public static Mono<String> emptyData(int userId){
    return Mono.empty();
}
```

출력결과

```plain
Completed
```

<br>

## Emitting Error

```java
@Test
public void EMMITTING_ERROR(){
    errorData(1).subscribe(
        Util.onNext(),
        Util.onError(),
        Util.onComplete()
    );
}

public static Mono<String> errorData(int userId){
    return Mono.error(new Throwable("테스트를 위해 예외를 발생시켰어요. "));
}
```

출력결과

```plain
Error >>> 테스트를 위해 예외를 발생시켰어요.
```

<br>

## fromSupplier

일단, Supplier 관련 내용은 아니지만, 먼저 아래 코드를 실행해보자.

```java
@Test
public void SOME_EXECUTING_CONTEXT_TEST(){
    Mono<String> mono = Mono.just(helloMessage());
}

public String helloMessage(){
    System.out.println("안뇽하세요~~~");
    return "반갑습니다~~~";
}
```

subscribe() 메서드를 통해 아직 데이터를 구독을 하지 않았음에도 아래와 같이 출력결과가 나타난다.

```plain
안뇽하세요~~~
```

helloMessage() 를 통해 리턴되는 값은 아직 리턴되지 않은 상태이지만, Mono.just로 데이터를 받을 때는 System.out.println 구문이 실행되었다.

<br>

subscribe() 로 구독을 할 때에만 실행되게끔 하고 싶다면, fromSupplier() 를 사용해야 한다.

이제는 아래와 같이 해보자.

```java
@Test
public void FROM_SUPPLIER_TEST(){
    Mono<String> mono = Mono.fromSupplier(() -> helloMessage());
}

public String helloMessage(){
    System.out.println("안뇽하세요~~~");
    return "반갑습니다~~~";
}
```

<br>

결과를 보면 아무일도 일어나지 않는다.<br>

즉, Supplier 를 통해 만들어낸 Mono 는 그냥 하나의 실행 문맥일 뿐 실행이 즉시 되지 않음을 알 수 있다.<br>

이번에는 아래와 같이 subscribe() 메서드를 작성해보자.<br>

```java
@Test
public void FROM_SUPPLIER_TEST2(){
    Mono.fromSupplier(() -> helloMessage())
        .subscribe(Util.onNext());
}

public String helloMessage(){
    System.out.println("안뇽하세요~~~");
    return "반갑습니다~~~";
}
```

<br>

## fromCallable

callable 역시 Mono 에 등록해서 사용하는 것이 가능하다. 자세한 사용법은 아래의 예제를 보자.

```java
@Test
public void FROM_CALLABLE_TEST(){
    Callable<String> helloCallable = () -> helloMessage();
    Mono.fromCallable(helloCallable)
        .subscribe(Util.onNext());
}

public String helloMessage(){
    System.out.println("안뇽하세요~~~");
    return "반갑습니다~~~";
}
```

<br>

**출력결과**<br>

```plain
안뇽하세요~~~
Received >>> 반갑습니다~~~
```

<br>

## blocking 처럼 동작하는 코드

Mono 의 fromSupplier 로 데이터의 흐름을 풀어놓는 코드다. 프로그램의 흐름을 풀어놓은 듯한 느낌이지만 실제로는 `Util.sleepSeconds()` 에서 blocking 된다. <br>

reactor 는 기본적으로 실제 발생하는 모든 로직들이 실행되는 곳은 main thread 이다. 메인 스레드는 프로그램이 실행될 때 current thread로 간주된다. 그리고 아래 코드에서 Thread.sleep(n) 코드는 메인스레드(current thread) 에서 실행되기에 현재 스레드를 3초간 블록되게 되는 것이다.<br>

<br>

```java
@Test
public void TEST_WAIT_HELLO(){
    waitHelloName();
    waitHelloName().subscribe(Util.onNext());
    waitHelloName();
}

public Mono<String> waitHelloName(){
    System.out.println("안녕하세요~~~");
    return Mono
        .fromSupplier(()->{
            System.out.println("[Supplier] >>> generating ... ");
            Util.sleepSeconds(3);
            return "[Supplier] >>> " + "(After 3s) (echo)... Helloooooo...";
        })
        .map(String::toUpperCase);
}
```

<br>

실행결과를 캡쳐해보면 아래와 같다.<br>

![1](./img/1-FLUX-MONO/3.png)

<br>

이번에는 아래의 코드를 보자. 메인 스레드 대신 Scheduler 위에서 동작하게끔 해줬다. 즉, Scheduler 가 점유하는 스레드 위에서 sleep 을 실행되게끔 했다.<br>

```java
@Test
public void TEST_SCHEDULER_HELLO(){
    schedulerHelloName();
    schedulerHelloName()
        .subscribeOn(Schedulers.boundedElastic())
        .subscribe(Util.onNext());
    schedulerHelloName();
}

public Mono<String> schedulerHelloName(){
    System.out.println("안녕하세요~~~");
    return Mono
        .fromSupplier(()->{
            System.out.println("[Supplier] >>> generating ... ");
            Util.sleepSeconds(3);
            return "[Supplier] >>> " + "(After 3s) (echo)... Helloooooo...";
        })
        .map(String::toUpperCase);
}
```

<br>

fromSupplier 의 스레드가 종료되기 전에 메인스레드가 실행을 종료하기에 아래와 같이 fromSupplier 에 작성한 로직이 모두 실행되지 않고 메인 스레드의 코드를 모두 실행한후 메인 스레드를 종료시킨다.<br>

![1](./img/1-FLUX-MONO/4.png)

<br>

main 스레드에서 실행시킬 코드를 모두 완료시킨 후에 Scheduler 코드도 모두 마무리 될 때까지 기다리고 싶다면? 아래의 코드를 보자.

```java
@Test
public void TEST_SCHEDULER_HELLO_BLOCK(){
    schedulerHelloName();
    String block = schedulerHelloName()
        .subscribeOn(Schedulers.boundedElastic())
        .block();
    System.out.println(block);
    schedulerHelloName();
}

public Mono<String> schedulerHelloName(){
    System.out.println("안녕하세요~~~");
    return Mono
        .fromSupplier(()->{
            System.out.println("[Supplier] >>> generating ... ");
            Util.sleepSeconds(3);
            return "[Supplier] >>> " + "(After 3s) (echo)... Helloooooo...";
        })
        .map(String::toUpperCase);
}
```

`subscribe()` 로 마무리하던 로직을 `block()` 코드를 호출하도록 변경해줬다.<br>

![1](./img/1-FLUX-MONO/5.png)

<br>

Scheduler 스레드가 모두 실행될 때 까지 기다렸다가 실행되는 것을 확인 가능하다.<br>

여기서 주의점이 있다.<br>

> String block = ...; 

과 같이 리턴값을 받아줘야 한다. 마치 CompletableFuture.supplyAsync() 의 실행결과를 future.get(); 으로 받아오는 로직을 작성하지 않으면 CompletableFuture 의 스레드 로직은 다른 스레드에서 실행되고 있고, 메인스레드에서는 별개로 동작하는 것과 비슷한 흐름이다.<br>

![1](./img/1-FLUX-MONO/6.png)

<br>

## fromFuture

CompletableFuture 를 fromFuture 메서드로 사용해보자.<br>

```java
@Test
public void TEST_FUTURE_HELLO_NAME(){
    Mono.fromFuture(futureHelloName())
        .subscribe(Util.onNext());
}

public static CompletableFuture<String> futureHelloName(){
    System.out.println("안녕하세요~~~");
    return CompletableFuture.supplyAsync(() -> {
        return Faker.instance().name().fullName();
    });
}
```

`futureHelloName()` 메서드

- "안녕하세요~~~" 라는 문자열을 출력한후, 임의의 영어이름을 리턴하는 역할의 CompletableFuture 객체를 리턴한다.

TSET_FUTURE_HELLO_NAME() 메서드

- future 객체를 Mono 로 받아서, 이것을 subscribe 한다.

<br>

**출력결과**<br>

이제 출력결과를 살펴보자.

```plain
안녕하세요~~~
```

이상하게도 future에서 실행하는 구문이 출력되지 않는다. 왜일까?<br>

Future로 실행하는 구문은 메인스레드가 아니라 별도의 스레드에서 실행하고 있고, 이것을 출력해주는 스레드는 메인 스레드다. 메인스레드는 Future 스레드가 종료되기 전에 매우 빠르게 종료된다. 이런 이유로 Future 스레드에서 subscribe() 를 통해 출력하는 문자열이 onNext() 를 통해 출력되지 못했다.<br>

<br>

이번에는 아래와 같이 코드를 작성해보자.<br>

```java
@Test
public void TEST_FUTURE_HELLO_NAME_BY_SLEEP(){
    Mono.fromFuture(futureHelloName())
        .subscribe(Util.onNext());
    Util.sleepSeconds(1);
}

public static CompletableFuture<String> futureHelloName(){
    System.out.println("안녕하세요~~~");
    return CompletableFuture.supplyAsync(() -> {
        return Faker.instance().name().fullName();
    });
}
```

<br>

출력결과는 아래와 같다.

```plain
안녕하세요~~~
Received >>> Miss Elmo Oberbrunner
```

컴퓨터에게는 매우 긴 시간인 1초를 딜레이를 주어서 메인스레드가 future 스레드의 동작이 완료될때까지 기다렸고 그 결과 subscribe() 에서 사용하는 Util.onNext() 메서드에 의해 future 스레드가 반환하는 문자열을 출력할 수 있게 되었다.<br>

<br>

## fromRunnable

이번에는 Runnable 객체를 fromRunnable() 메서드로 실행시켜 보자.<br>

```java
    @Test
    public void TEST_RUNNABLE_HELLO_NAME(){
        Mono.fromRunnable(runnableHelloName())
                .subscribe(Util.onNext());
    }

    public static Runnable runnableHelloName(){
        System.out.println("안녕하세요~~~");
        Util.sleepSeconds(3);
        return () -> System.out.println(Faker.instance().name().fullName());
    }
```

<br>

출력결과

```plain
안녕하세요~~~
Carmel Davis
```

<br>

`fromFuture()` 메서드를 사용할 때와 결과가 조금 다르다. `fromFuture()` 메서드를 사용했을 때는, 메인스레드와 future 스레드가 별도로 실행됐었다. 하지만, fromRunnable() 메서드를 사용할 때는 Runnable 객체를 메인스레드에서 실행시키기에, 메인스레드에서 runnable 객체가 종료될 때 까지 기다려주는 것을 볼 수 있다.<br>

<br>

# Flux 기초

이미 Mono 를 예제로 해서 여러가지 문법들을 공부했기에 중복되는 부분들은 설명을 최대한 줄여볼 생각이다. 대신 예제와 출력결과를 남겨두었으니, 나중에 가물가물해지더라도 다시 봤을때 이해가 되지 않을까 싶다.<br>

<br>

## Flux.just

```java
@Test
public void FLUX_JUST_TEST(){
    Flux<Integer> flux = Flux.just(1,2,3,4);
    flux.subscribe(
        Util.onNext(),
        Util.onError(),
        Util.onComplete()
    );
}
```

<br>

출력결과는 아래와 같다.<br>

```plain
Received >>> 1
Received >>> 2
Received >>> 3
Received >>> 4
Completed
```

<br>

## Flux.empty

이번에도 역시 설명은 생략하려 한다. Mono 에서 `Emitting Empty` 라는 예제와 비슷한 예제이기 때문이다.

```java
@Test
public void FLUX_EMPTY_TEST(){
    Flux<Integer> flux = Flux.empty();
    flux.subscribe(
        Util.onNext(),
        Util.onError(),
        Util.onComplete()
    );
}
```

<br>

출력결과는 아래와 같다.<br>

```plain
Completed
```

<br>

## Flux.just를 여러가지 타입으로

엄청 중요한 내용은 아닌데, 그냥 예제를 다양하게 해두면 좋을 것 같아서 정리를 시작하게 됐다.<br>

```java
@Test
public void FLUX_JUST_VARIOUS_TYPE(){
    Flux<Object> flux = Flux.just(1,2,3, "a", Faker.instance().name().fullName());
    flux.subscribe(
        Util.onNext(),
        Util.onError(),
        Util.onComplete()
    );
}
```

<br>

**출력결과**

```plain
Received >>> 1
Received >>> 2
Received >>> 3
Received >>> a
Received >>> Raymon Rippin
Completed
```

<br>

## Multiple Subscribers

Stream 의 경우는 최종연산을 마치면서 Stream 이 close 되고 나면, 재사용할 수 없었다. 하지만 Flux의 경우 하나의 Flux 로부터 다른 Flux를 만들어낼 때 복사본을 만들어내는 듯해보인다. 아래는 이런 동작에 대한 예제다.<br>

이런 점은 확실히 Flux, Mono 의 장점이라는 것이 느껴진다.<br>

```java
@Test
public void FLUX_MULTIPLE_SUBSCRIBERS(){
    Flux<Integer> numbersFlux = Flux.just(1,2,3,4,5,6,7,8,9,10);
    Flux<Integer> oddFlux = numbersFlux.filter(n -> n%2 == 1);

    numbersFlux.subscribe(n -> System.out.println("Subs 1 >>> " + n));
    oddFlux.subscribe(n -> System.out.println("Subs 2 >>> " + n));
    numbersFlux.subscribe(n -> System.out.println("Subs 1 >>> " + n));
}
```

<br>

**출력결과**<br>

numbersFlux 의 내용과 numbersFlux의 데이터를 기반으로 filter 를 거친 oddFlux의 내용이 각각 다른 내용이다.<br>

그리고 이것을 출력해보기 위해 subscribe() 로 확인해보면, 확실히 Flux로 subscribe를 한다고 해서 자료가 달라지지 않고 각각의 객체가 별개의 객체라는 것을 확인 가능하다.<br>

<br>

```plain
Subs 1 >>> 1
Subs 1 >>> 2
Subs 1 >>> 3
Subs 1 >>> 4
Subs 1 >>> 5
Subs 1 >>> 6
Subs 1 >>> 7
Subs 1 >>> 8
Subs 1 >>> 9
Subs 1 >>> 10
Subs 2 >>> 1
Subs 2 >>> 3
Subs 2 >>> 5
Subs 2 >>> 7
Subs 2 >>> 9
Subs 1 >>> 1
Subs 1 >>> 2
Subs 1 >>> 3
Subs 1 >>> 4
Subs 1 >>> 5
Subs 1 >>> 6
Subs 1 >>> 7
Subs 1 >>> 8
Subs 1 >>> 9
Subs 1 >>> 10
```

<br>

## Flux.fromIterable, fromArray

**Flux.fromIterable()**<br>

List 와 같은 컬렉션 타입이면서, Iterable 객체인 타입은 Flux 에서는 `Flux.fromIterable` 이라는 메서드로 변환 가능하다.<br>

**Flux.fromArray()**<br>

일반 배열도 `Flux.fromArray()` 메서드를 통해 하나의 데이터의 흐름으로 만들어내는 것이 가능하다.<br>

<br>

**예제) Flux.fromIterable()**<br>

```java
@Test
public void FLUX_FROM_ITERABLE(){
    List<String> list = Arrays.asList("a", "b", "c");
    Flux.fromIterable(list)
        .subscribe(Util.onNext());
}
```

<br>

**출력결과**<br>

```plain
Received >>> a
Received >>> b
Received >>> c
```

<br>

**예제) Flux.fromArray()**<br>

```java
@Test
public void FLUX_FROM_ARRAY(){
    Integer [] numbers = {1,2,3,4,5,6,7,8,9,10};
    Flux.fromArray(numbers)
        .subscribe(Util.onNext());
}
```

<br>

**출력결과**<br>

```plain
Received >>> 1
Received >>> 2
Received >>> 3
Received >>> 4
Received >>> 5
Received >>> 6
Received >>> 7
Received >>> 8
Received >>> 9
Received >>> 10
```

<br>

## Flux.fromStream

예제의 대략적인 내용을 정리해보면 이렇다.<br>

Stream을 생성해서 stream 이라는 이름의 변수에 담아두고 있는다. 그리고, 이 stream을 각각 두번의 subscribe() 로 subscribe 한다. 이때 첫번째 subscribe() 에서 이미 생성된 Stream 을 소모했기에, 더 이상 stream이 생성되지 않는다.

```java
@Test
public void FLUX_FROM_STREAM(){
    List<Integer> list = List.of(1,2,3,4,5);
    Stream<Integer> stream = list.stream();

    Flux<Integer> flux = Flux.fromStream(stream);

    flux.subscribe(Util.onNext(), Util.onError(), Util.onComplete());
    flux.subscribe(Util.onNext(), Util.onError(), Util.onComplete());
}
```

<br>

**출력결과**

```plain
Received >>> 1
Received >>> 2
Received >>> 3
Received >>> 4
Received >>> 5
Completed
Error >>> stream has already been operated upon or closed
```

<br>

이번에는 아래와 같이 lazy evalution 방식으로 람다안에서 stream을 생성하도록 수정해보는 예제다. 당연하게도 이번 결과는 잘 수행된다. 왜냐하면, 실행시에 stream 을 생성하기 때문이다. (그래도 가급적이면 Stream 보다는 컬렉션을 함수의 인자값/출력값으로 사용하자.)<br>

```java
@Test
public void FLUX_FROM_STREAM_LAZY_CREATION(){
    List<Integer> list = List.of(1,2,3,4,5);
    Flux<Integer> numberFlux = Flux.fromStream(() -> list.stream());
    numberFlux.subscribe(Util.onNext(), Util.onError(), Util.onComplete());
    numberFlux.subscribe(Util.onNext(), Util.onError(), Util.onComplete());
}
```

**출력결과**<br>

```plain
Received >>> 1
Received >>> 2
Received >>> 3
Received >>> 4
Received >>> 5
Completed
Received >>> 1
Received >>> 2
Received >>> 3
Received >>> 4
Received >>> 5
Completed
```

데이터가 반복적으로 잘 출력되는 것을 볼 수 있다.<br>

<br>

## Flux.range

숫자 5에서부터 출발해서 15개의 숫자를 만들어내는 Flux의 range를 사용하는 예제는 아래와 같다.

```java
@Test
public void FLUX_RANGE_TEST(){
    Flux.range(5,15)
        .subscribe(Util.onNext());
}
```

<br>

출력결과는 아래와 같다.

```plain
Received >>> 5
Received >>> 6
Received >>> 7
Received >>> 8
Received >>> 9
Received >>> 10
Received >>> 11
Received >>> 12
Received >>> 13
Received >>> 14
Received >>> 15
Received >>> 16
Received >>> 17
Received >>> 18
Received >>> 19

Process finished with exit code 0
```

<br>

## Flux.map

이번에는 Flux.range 로 생성된 데이터를 map으로 다른 데이터로 변환해보는 예제다.

```java
@Test
public void FLUX_RANGE_AND_MAP_TEST(){
    Flux.range(5,15)
        .map(number -> Faker.instance().name().fullName())
        .subscribe(Util.onNext());
}
```

<br>

출력결과

```plain
Received >>> Mr. Dean O'Reilly
Received >>> Del Baumbach
Received >>> Hollis Gutmann
Received >>> Karly Schiller
Received >>> Olinda Sipes
Received >>> Dean Moen
Received >>> Solomon Swaniawski
Received >>> Sharilyn Zieme
Received >>> Mirna Konopelski
Received >>> Delcie O'Connell
Received >>> Marcus Towne
Received >>> Jenny Schroeder
Received >>> Percy Conn
Received >>> Rochel Brakus
Received >>> Ms. Janie Howe

Process finished with exit code 0
```

<br>

## Flux.log

숫자 5에서부터 시작해서 3개의 숫자의 수열을 만들어내고 이것을 log() 메서드로 출력한다. 그리고 중간에 map 을 통해서 임의의 영어이름을 반환하게끔 했는데, 또 이것을 출력하기 위해 log() 메서드를 사용했다.<br>

마지막으로 이 데이터를 사용하기 위해 subscribe() 메서드에 Util.onNext() 를 통해 `Consumer<Object> onNext()` 을 전달하여 구독을 한다.

```java
@Test
public void FLUX_RANGE_AND_MAP_AND_LOG_TEST(){
    Flux.range(5,3)
        .log()
        .map(number -> Faker.instance().name().fullName())
        .log()
        .subscribe(Util.onNext());
}
```

<br>

출력결과<br>

```plain
[ INFO] (main) | onSubscribe([Synchronous Fuseable] FluxRange.RangeSubscription)
[ INFO] (main) | onSubscribe([Fuseable] FluxMapFuseable.MapFuseableSubscriber)
[ INFO] (main) | request(unbounded)
[ INFO] (main) | request(unbounded)
[ INFO] (main) | onNext(5)
[ INFO] (main) | onNext(Babette Nolan)
Received >>> Babette Nolan
[ INFO] (main) | onNext(6)
[ INFO] (main) | onNext(Fausto Von)
Received >>> Fausto Von
[ INFO] (main) | onNext(7)
[ INFO] (main) | onNext(Leeanne Osinski)
Received >>> Leeanne Osinski
[ INFO] (main) | onComplete()
[ INFO] (main) | onComplete()

Process finished with exit code 0
```

<br>

가급적이면, log() 메서드는 상용코드에서는 사용하지 않는게 좋다.<br>

<br>

## Flux vs List

List 는 데이터를 한번에 통으로 전달한다.<br>

Flux는 데이터를 하나씩 하나씩 흘려서 보낸다.<br>

예제를 한번 보자.<br>

먼저, List를 출력하는 예제다.<br>

```java
@Test
public void LIST_TEST(){
    System.out.println(List.of(1,2,3,4,5));
}
```

<br>

출력결과<br>

```plain
[1, 2, 3, 4, 5]
```

<br>

이번 예제는 List 로부터 stream을 만들어내서 여러 번 사용가능한지 확인해보는 예제다.<br>

출력결과를 보면 Stream 은 재사용을 못한다. 최종연산을 한번만 할수 있다.<br>

```java
@Test
public void LIST_TO_STREAM_MULTIPLE_TEST(){
    Stream<Integer> stream = List.of(1, 2, 3, 4, 5).stream();
    System.out.println("First try >>> ");
    stream.forEach(System.out::println);

    System.out.println();
    System.out.println("Second try >>> ");

    stream.forEach(System.out::println);
}
```

<br>

출력결과는 아래와 같다.

```java
First try >>> 
1
2
3
4
5

Second try >>> 

java.lang.IllegalStateException: stream has already been operated upon or closed
```

<br>

이번에는 Flux 를 여러번 사용하는 예제다. 재사용이 된다.

```java
@Test
public void LIST_TO_FLUX_MULTIPLE_TEST(){
    Flux<Integer> numbers = Flux.range(1, 5);

    System.out.println("First try >>> ");
    numbers.subscribe(Util.onNext());

    System.out.println();
    System.out.println("Second try >>> ");
    numbers.subscribe(Util.onNext());
}
```

<br>

```plain
First try >>> 
Received >>> 1
Received >>> 2
Received >>> 3
Received >>> 4
Received >>> 5

Second try >>> 
Received >>> 1
Received >>> 2
Received >>> 3
Received >>> 4
Received >>> 5

Process finished with exit code 0
```

<br>

## Flux.interval

먼저 아래와 같이 interval을 단순하게 메인스레드에서 실행해보자. 아마 아무것도 출력되지 않을 것이다.

```java
@Test
public void FLUX_INTERVAL_TEST(){
    Flux.interval(Duration.ofSeconds(1))
        .subscribe(Util.onNext());
}
```

<br>

화면에 출력하는 역할을 수행할 수 있는 메인스레드가 interval 을 기다려줘야 한다. 아래의 코드를 실행해보자.

```java
@Test
public void FLUX_INTERVAL_TEST(){
    Flux.interval(Duration.ofSeconds(1))
        .subscribe(Util.onNext());

    Util.sleepSeconds(5);
}
```

출력결과는 아래와 같이 잘 출력된다.

```plain
Received >>> 0
Received >>> 1
Received >>> 2
Received >>> 3
Received >>> 4

Process finished with exit code 0
```

<br>

## Mono → Flux 변환

Mono를 Flux로 변환할 수 있다. 아래의 코드를 보자.

```java
@Test
public void MONO_to_FLUX_TEST(){
    Mono<String> mono = Mono.just("a");
    Flux<String> flux = Flux.from(mono);
    flux.subscribe(Util.onNext());
}
```

<br>

출력결과

```plain
Received >>> a

Process finished with exit code 0
```

<br>

## Flux → Mono 변환

Flux 를 Mono 로 변환하는 것은 next() 메서드를 사용하면 된다.

```java
@Test
public void FLUX_to_MONO_TEST(){
    Mono<String> a = Flux.just("a").next();
    a.subscribe(Util.onNext());
}
```

<br>

출력결과

```plain
Received >>> a

Process finished with exit code 0
```
<br>

# Util 클래스

```java
package util;

import java.util.function.Consumer;

public class Util {
    public static Consumer<Object> onNext(){
        return obj -> System.out.println("Received >>> " + obj);
    }
    
    public static Consumer<Throwable> onError(){
        return error -> System.out.println("Error >>> " + error.getMessage());
    }
    
    public static Runnable onComplete(){
        return () -> System.out.println("Completed");
    }
    
    public static void sleepSeconds(int n){
        try {
            Thread.sleep(n * 1000L);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

<br>

# Faker 클래스 테스트

Faker 클래스는 가짜 데이터를 만드는 용도로 사용한다. 예를 들면 아래와 같은 방식으로 사용하는 편이다.

```java
@Test
public void FAKER_TEST(){
    Faker instance = Faker.instance();
    IntStream.range(1, 11)
        .forEach(i -> System.out.println(i + ". " + instance.name().fullName()));
}
```

<br>

출력결과

```plain
1. Matthew Grant
2. Mr. Allen Thiel
3. Linette Stracke PhD
4. Randy Ankunding Sr.
5. Sarah Mohr
6. Reid Keeling
7. Rod Mann
8. Ricky Kuvalis
9. Alton Friesen
10. Melodee Koepp
```

<br>

<br>

<br>

<br>

<hr/>

이렇게 해서 엄청 기본적인 Flux, Mono 사용법 예제 정리는 모두 마쳤다. 정리하는게 꽤 힘들었지만, 한번 정리해놓고 나니, 나중에 헷갈리는 문법이 있을때 찾아볼수 있겠다는 생각이 들어서 안심은 된다.<br>

설명을 적으면서 개념이 명확해진 부분도 있다.<br>

<br>
