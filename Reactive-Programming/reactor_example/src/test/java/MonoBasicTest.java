import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import util.Util;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class MonoBasicTest {
    @Test
    public void MONO_JUST_TEST(){
        Mono<Integer> one = Mono.just(1);
        System.out.println(one);
    }

    @Test
    public void MONO_JUST_TEST2(){
        Mono<Integer> mono = Mono.just(1);
        mono.subscribe(i->System.out.println(i));
        // 또는 아래와 같이 간결하게 메서드 레퍼런스로도 가능
        mono.subscribe(System.out::println);
    }

    @Test
    public void MONO_SUBSCRIBE_TEST(){
        Mono<Integer> mono = Mono.just(2);

        mono.subscribe(
            number -> System.out.println(number),
            throwable -> System.out.println(throwable.getMessage()),
            () -> System.out.println("Complete!!")
        );
    }

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

    @Test
    public void ON_ERROR_TEST2_WITHOUT_ONERROR(){
        Mono<Integer> hello = Mono.just("hello")
                .map(String::length)
                .map(i -> i / 0);

        hello.subscribe(
                number -> System.out.println(number)
        );
    }

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

    @Test
    public void SOME_EXECUTING_CONTEXT_TEST(){
        Mono<String> mono = Mono.just(helloMessage());
    }

    public String helloMessage(){
        System.out.println("안뇽하세요~~~");
        return "반갑습니다~~~";
    }

    @Test
    public void FROM_SUPPLIER_TEST(){
        Mono<String> mono = Mono.fromSupplier(() -> helloMessage());
    }

    @Test
    public void FROM_SUPPLIER_TEST2(){
        Mono.fromSupplier(() -> helloMessage())
                .subscribe(Util.onNext());
    }

    @Test
    public void FROM_CALLABLE_TEST(){
        Callable<String> helloCallable = () -> helloMessage();
        Mono.fromCallable(helloCallable)
                .subscribe(Util.onNext());
    }

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

    @Test
    public void TEST_SCHEDULER_HELLO(){
        schedulerHelloName();
        schedulerHelloName()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(Util.onNext());
        schedulerHelloName();
    }

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

    @Test
    public void TEST_FUTURE_HELLO_NAME_BY_SLEEP(){
        Mono.fromFuture(futureHelloName())
                .subscribe(Util.onNext());
        Util.sleepSeconds(1);
    }

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

    @Test
    public void FAKER_TEST(){
        Faker instance = Faker.instance();
        IntStream.range(1, 11)
                .forEach(i -> System.out.println(i + ". " + instance.name().fullName()));
    }
}
