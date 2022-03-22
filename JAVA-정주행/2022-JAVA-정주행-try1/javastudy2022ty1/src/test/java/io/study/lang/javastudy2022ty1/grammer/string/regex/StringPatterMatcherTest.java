package io.study.lang.javastudy2022ty1.grammer.string.regex;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringPatterMatcherTest {

    @Test
    @DisplayName("String을 이용한 단순 정규표현식 살펴보기")
    public void test1(){
        String line = "This            is scocer.";

        // 1 ) Th 뒤에 어떤 문자열이든 문자열이 있는지 체크
        // 2 ) 중간에 문자열 is 가 있으면서
        // 3 ) 문장의 가장 마지막을 . 으로 마무리 하는지 체크
        System.out.println("matches 테스트 >> ");
        boolean test = line.matches("Th.* is .*\\.");
        System.out.println("line.matches ? " + test);
        System.out.println("");

        System.out.println("split 테스트 >> ");
        String[] splitArr = line.split("\\s+");
        for (String word : splitArr){
            System.out.println("word = " + word);
        }
        System.out.println();

        System.out.println("replaceAll 테스트 >> ");
        String replacedString = line.replaceAll("\\s+", "+");
        System.out.println("replaced String = " + replacedString);
    }

    @Test
    @DisplayName("Matcher.matches")
    public void test2(){
        // 'Ths is ' 뒤로는 어떤 문자열이든 올 수 있고 "." 으로 문장이 끝나야 한다.
        Pattern pattern = Pattern.compile("This is .*\\.");

        String line = "This is soccer.";

        // Pattern 객체로 특정 정규표현식에 대한 Matcher 객체 생성
        Matcher matcher = pattern.matcher(line);

        if(matcher.matches()){
            System.out.println("정규표현식이 일치합니다.!!!");
        }
        else{
            System.out.println("정규표현식을 다시 확인 부탁드립니다.");
        }
    }

    @Test
    @DisplayName("Pattern.split")
    public void test3(){
        // 정규표현식 : 공백
        Pattern pattern = Pattern.compile("\\s+");

        String line = "This is               soccer.";

        String[] split = pattern.split(line);
        System.out.println(Arrays.toString(split));
    }

    @Test
    @DisplayName("Matcher.replaceAll")
    public void test4(){
        // 정규표현식 : 공백
        Pattern pattern = Pattern.compile("\\s+");

        String line = "This is               soccer.";

        Matcher matcher = pattern.matcher(line);

        String replacedLine = matcher.replaceAll("+");

        System.out.println(replacedLine);
    }
}
