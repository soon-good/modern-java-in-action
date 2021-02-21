package io.study.modernjavainaction.ch5.ch5_1_3_basic_stream_api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class BasicStreamAPI_Summary {

	@Test
	void testSplitString(){
		String apple = "Apple";
		String[] split = apple.split("");
		for (String s : split){
			System.out.println("s = " + s);
		}
	}

	@Test
	void testFlatMapAPI_1_intro(){
		List<String> words = new ArrayList<>();
		words.add("apple");
		words.add("banana");
		words.add("cherry");
		words.add("world");
		words.add("mango");

		List<String[]> destructuredStringList = words.stream()
			.map(word -> word.split(""))
			.distinct()
			.collect(Collectors.toList());
	}

	@Test
	void testFlatMapAPI_2_intro(){
		List<String> words = new ArrayList<>();
		words.add("apple");
		words.add("banana");
		words.add("cherry");
		words.add("world");
		words.add("mango");

		List<Stream<String>> destructuredStringList = words.stream()
			.map(word -> word.split(""))
			.map(Arrays::stream)
			.distinct()
			.collect(Collectors.toList());
	}

	@Test
	void testFlatMapAPI_3_usingFlatMap(){
		List<String> words = new ArrayList<>();
		words.add("apple");
		words.add("banana");
		words.add("cherry");
		words.add("world");
		words.add("mango");

		List<String> destructuredStringList = words.stream()
			.map(word -> word.split(""))
			.flatMap(Arrays::stream)
			.distinct()
			.sorted()
			.collect(Collectors.toList());

		destructuredStringList.stream().forEach(s->{
			System.out.print(s + " ");
		});
		System.out.println("");
	}
}
