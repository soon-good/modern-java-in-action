import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import util.Util;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FluxBasicTest {

    @Test
    public void FLUX_JUST_TEST(){
        Flux<Integer> flux = Flux.just(1,2,3,4);
        flux.subscribe(
            Util.onNext(),
            Util.onError(),
            Util.onComplete()
        );
    }

    @Test
    public void FLUX_EMPTY_TEST(){
        Flux<Integer> flux = Flux.empty();
        flux.subscribe(
            Util.onNext(),
            Util.onError(),
            Util.onComplete()
        );
    }

    @Test
    public void FLUX_JUST_VARIOUS_TYPE(){
        Flux<Object> flux = Flux.just(1,2,3, "a", Faker.instance().name().fullName());
        flux.subscribe(
            Util.onNext(),
            Util.onError(),
            Util.onComplete()
        );
    }

    @Test
    public void FLUX_MULTIPLE_SUBSCRIBERS(){
        Flux<Integer> numbersFlux = Flux.just(1,2,3,4,5,6,7,8,9,10);
        Flux<Integer> oddFlux = numbersFlux.filter(n -> n%2 == 1);

        numbersFlux.subscribe(n -> System.out.println("Subs 1 >>> " + n));
        oddFlux.subscribe(n -> System.out.println("Subs 2 >>> " + n));
        numbersFlux.subscribe(n -> System.out.println("Subs 1 >>> " + n));
    }

    @Test
    public void FLUX_FROM_ITERABLE(){
        List<String> list = Arrays.asList("a", "b", "c");
        Flux.fromIterable(list)
                .subscribe(Util.onNext());
    }

    @Test
    public void FLUX_FROM_ARRAY(){
        Integer [] numbers = {1,2,3,4,5,6,7,8,9,10};
        Flux.fromArray(numbers)
                .subscribe(Util.onNext());
    }

    @Test
    public void FLUX_FROM_STREAM(){
        List<Integer> list = List.of(1,2,3,4,5);
        Stream<Integer> stream = list.stream();

        Flux<Integer> flux = Flux.fromStream(stream);

        flux.subscribe(Util.onNext(), Util.onError(), Util.onComplete());
        flux.subscribe(Util.onNext(), Util.onError(), Util.onComplete());
    }

    @Test
    public void FLUX_FROM_STREAM_LAZY_CREATION(){
        List<Integer> list = List.of(1,2,3,4,5);
        Flux<Integer> numberFlux = Flux.fromStream(() -> list.stream());
        numberFlux.subscribe(Util.onNext(), Util.onError(), Util.onComplete());
        numberFlux.subscribe(Util.onNext(), Util.onError(), Util.onComplete());
    }

    @Test
    public void FLUX_RANGE_TEST(){
        Flux.range(5,15)
                .subscribe(Util.onNext());
    }

    @Test
    public void FLUX_RANGE_AND_MAP_TEST(){
        Flux.range(5,15)
                .map(number -> Faker.instance().name().fullName())
                .subscribe(Util.onNext());
    }

    @Test
    public void FLUX_RANGE_AND_MAP_AND_LOG_TEST(){
        Flux.range(5,3)
                .log()
                .map(number -> Faker.instance().name().fullName())
                .log()
                .subscribe(Util.onNext());
    }

    @Test
    public void LIST_TEST(){
        System.out.println(List.of(1,2,3,4,5));
    }

    @Test
    public void LIST_TO_STREAM_MULTIPLE_TEST(){
        Stream<Integer> stream = List.of(1, 2, 3, 4, 5).stream();
        System.out.println("First try >>> ");
        stream.forEach(System.out::println);
        
        System.out.println();
        System.out.println("Second try >>> ");
        
        stream.forEach(System.out::println);
    }

    @Test
    public void LIST_TO_FLUX_MULTIPLE_TEST(){
        Flux<Integer> numbers = Flux.range(1, 5);

        System.out.println("First try >>> ");
        numbers.subscribe(Util.onNext());

        System.out.println();
        System.out.println("Second try >>> ");
        numbers.subscribe(Util.onNext());
    }

    @Test
    public void FLUX_INTERVAL_TEST(){
        Flux.interval(Duration.ofSeconds(1))
                .subscribe(Util.onNext());

        Util.sleepSeconds(5);
    }

    @Test
    public void MONO_to_FLUX_TEST(){
        Mono<String> mono = Mono.just("a");
        Flux<String> flux = Flux.from(mono);
        flux.subscribe(Util.onNext());
    }

    @Test
    public void FLUX_to_MONO_TEST(){
        Mono<String> a = Flux.just("a").next();
        a.subscribe(Util.onNext());
    }
}
