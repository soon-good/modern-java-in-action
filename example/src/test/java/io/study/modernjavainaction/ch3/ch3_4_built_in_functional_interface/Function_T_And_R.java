package io.study.modernjavainaction.ch3.ch3_4_built_in_functional_interface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Function_T_And_R {

	/**
	 * Function<T,R> 은 타입 T를 파라미터로 받아서 타입 R을 리턴한다.. 라고 읽으면 된다.
	 * 문자열을 input 으로 받아서 (문자열 타입 - Type(T))
	 * 문자열의 길이(Integer)를 Return(R)
	 */
	@Test
	@DisplayName("Function<T,R> 예제 #1")
	void testFunctionTAndR_1(){
		Function<String, Integer> lengthFunc = (t)->{
			return t.length();
		};

		Integer length = lengthFunc.apply("hello~");
		Assertions.assertThat(length).isEqualTo(6);
		System.out.println(length);
	}

	/**
	 * 인풋 타입 T 를 나열한 컬렉션인 input 을 받아서
	 * 리턴 타입 R과 인풋 타입 T에 대한 람다를 받아서 연산을 수행하도록 하는 함수.
	 * 리스트의 각 요소 T에 대해 mapperFn을 적용한 값 R을 리스트로 만든 값을 반환.
	 * @param input
	 * @param mapperFn
	 * @param <T>
	 * @param <R>
	 * @return
	 */
	public <T,R> List<R> mappingByFunction(List<T> input, Function<T, R> mapperFn){
		List<R> list = new ArrayList<>();
		for(T d : input){
			list.add(mapperFn.apply(d));
		}
		return list;
	}

	/**
	 * Function<T,R> 을 메서드의 파라미터로 전달해보기
	 */
	@Test
	@DisplayName("Function<T,R> 예제 #2")
	void testFucntionTAnR_2(){
		List<String> words = Arrays.asList("Apple", "Banana", "Carrot", "Dragon", "Eureka");

		List<Integer> integers = mappingByFunction(words, t -> {
			return t.length();
		});

		System.out.println(integers);
	}

}
