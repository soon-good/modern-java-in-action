package io.study.modernjavainaction.playground;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ReactivePlay {

	@Test
	@DisplayName("모노_샘플_사용해보기")
	void 모노_샘플_사용해보기(){
		Mono.just("Craig")
			.map(n -> n.toUpperCase())
			.map(cn -> "Hello, " + cn + "!")
			.subscribe(System.out::println);
	}

	@Test
	@DisplayName("객체들을_받아서_Flux_객체_생성하기")
	void 객체들을_받아서_Flux_객체_생성하기(){
		Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");
		fruitFlux.subscribe( fruit -> System.out.println("Here's some fruit :: " + fruit) );
	}

	@Test
	@DisplayName("Assertion_사용해보기_StepVerifier")
	void Assertion_사용해보기_StepVerifier(){
		Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");
		StepVerifier.create(fruitFlux)
			.expectNext("Apple")
			.expectNext("Orange")
			.expectNext("Grape")
			.expectNext("Banana")
			.expectNext("Strawberry")
			.verifyComplete();
	}

	@Test
	@DisplayName("String_배열로부터_생성하기")
	void String_배열로부터_생성하기(){
		String [] fruits = new String [] {"Apple", "Orange", "Grape", "Banana", "Strawberry"};

		Flux<String> stringFlux = Flux.fromArray(fruits);

		StepVerifier.create(stringFlux)
			.expectNext("Apple")
			.expectNext("Orange")
			.expectNext("Grape")
			.expectNext("Banana")
			.expectNext("Strawberry")
			.verifyComplete();
	}

	@Test
	@DisplayName("Iterable로부터_생성하기")
	void Iterable로부터_생성하기(){
		List<String> fruitList = new ArrayList<>();
		fruitList.add("Apple");
		fruitList.add("Orange");
		fruitList.add("Grape");
		fruitList.add("Banana");
		fruitList.add("Strawberry");

		Flux<String> fruitFlux = Flux.fromIterable(fruitList);

		StepVerifier.create(fruitFlux)
			.expectNext("Apple")
			.expectNext("Orange")
			.expectNext("Grape")
			.expectNext("Banana")
			.expectNext("Strawberry")
			.verifyComplete();
	}

	@Test
	@DisplayName("Stream_으로부터_생성하기")
	void Stream_으로부터_생성하기(){
		Stream<String> dataStream = Stream.of("Apple", "Banana", "Grape", "Cherry", "Strawberry");

		Flux<String> stringFlux = Flux.fromStream(dataStream);

		StepVerifier.create(stringFlux)
			.expectNext("Apple")
			.expectNext("Banana")
			.expectNext("Grape")
			.expectNext("Cherry")
			.expectNext("Strawberry")
			.verifyComplete();
	}

	@Test
	@DisplayName("Flux_데이터_축_생성하기")
	void Flux_데이터_축_생성하기(){
		// 숫자 0 에서 부터 5개의 숫자를 생성해낸다.
		System.out.println("range #1");
		Flux<Integer> range1 = Flux.range(0, 5);
		range1.subscribe(n -> System.out.print(n + "->"));
		System.out.println("||");

		// 숫자 3 에서 부터 5개의 숫자를 생성해낸다.
		System.out.println();
		System.out.println("range #2");
		Flux<Integer> range2 = Flux.range(3,5);
		range2.subscribe(n -> System.out.print(n + "->"));
		System.out.println("||");
	}

	@Test
	@DisplayName("Flux_초_간격으로_실행시켜보기")
	void Flux_초_간격으로_실행시켜보기(){
		Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1))
			.take(7);

//		intervalFlux.subscribe(interval -> System.out.print(interval + "->"));
//		System.out.println("||");

		StepVerifier.create(intervalFlux)
			.expectNext(0L)
			.expectNext(1L)
			.expectNext(2L)
			.expectNext(3L)
			.expectNext(4L)
			.expectNext(5L)
			.expectNext(6L)
			.verifyComplete();
	}
}
