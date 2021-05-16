package io.study.modernjavainaction.ch16;

import io.study.modernjavainaction.ch16.discount.Discount;
import io.study.modernjavainaction.ch16.discount.Quote;
import io.study.modernjavainaction.ch16.shop.Shop;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AsyncPipelineTest_16_4 {

	List<Shop> shops = Arrays.asList(
		new Shop("광명시 삼성센터"),
		new Shop("강남구 삼성센터"),
		new Shop("분당구 삼성센터"),
		new Shop("안양시 삼성센터"),
		new Shop("수원시 삼성센터")
	);

	private final Executor executor =
		Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory(){
			public Thread newThread(Runnable r){
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
		});

	public List<String> findPrices(String product){
		return shops.stream()
			.map(shop -> shop.getPrice(product))
			.map(Quote::parse)
			.map(Discount::applyDiscount)
			.collect(Collectors.toList());
	}

	@DisplayName("문제예제1_동기식으로_값_조회와_할인율계산을_한꺼번에_수행하기")
	@Test
	void 문제예제1_동기식으로_값_조회와_할인율계산을_한꺼번에_수행하기(){
		long start = System.nanoTime();
		List<String> results = findPrices("myphone");
		long duration = (System.nanoTime() - start) / 1000000;

		System.out.println("[완료] " + duration + " ms");
	}

	@DisplayName("예제2_두가지_비동기_연산을_thenCompose()_메서드를_이용해_파이프라인으로_만들어서_처리해보기")
	@Test
	void 예제2_두가지_비동기_연산을_thenCompose_메서드를_이용해_파이프라인으로_만들어서_처리해보기(){
		long start = System.nanoTime();
		String product = "myphone";

		List<CompletableFuture<String>> priceStrFutures = shops.stream()
			.map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
			.map(future -> future.thenApply(Quote::parse))
			.map(future -> future.thenCompose(
				quote -> CompletableFuture.supplyAsync(
					() -> Discount.applyDiscount(quote), executor)
			))
			.collect(Collectors.toList());

		priceStrFutures.stream()
			.map(CompletableFuture::join)
			.collect(Collectors.toList());

		long duration = (System.nanoTime() - start) / 1000000;
		System.out.println("[완료] " + duration + " ms");
	}
}
