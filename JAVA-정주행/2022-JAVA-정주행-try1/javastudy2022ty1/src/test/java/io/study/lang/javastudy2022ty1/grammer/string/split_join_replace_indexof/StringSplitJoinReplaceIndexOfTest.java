package io.study.lang.javastudy2022ty1.grammer.string.split_join_replace_indexof;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class StringSplitJoinReplaceIndexOfTest {

    @Test
    @DisplayName("split 메서드")
    public void test1(){
        String line = "This is               soccer.";

        String[] arr = line.split(" ");
        System.out.println("first case >> ");
        System.out.println(Arrays.toString(arr));
        System.out.println();

        String line2 = "This is soccer.";
        String[] arr2 = line2.split(" ");
        System.out.println("second case >> ");
        System.out.println(Arrays.toString(arr2));
        System.out.println();
    }

    @Test
    @DisplayName("split 메서드 > 정규표현식으로 분할하기")
    public void test2(){
        String url = "www.naver.com";

        String[] split = url.split("\\.");
        System.out.println(Arrays.toString(split));
    }

    @Test
    @DisplayName("join 메서드 #1")
    public void test3(){
        List<String> players = Arrays.asList("손흥민", "황의조", "남태희", "황희찬");
        String joinStr = String.join(",", players);
        System.out.println(joinStr);
    }

    @Test
    @DisplayName("join 메서드 #2")
    public void test4(){
        String players = String.join(",", "손흥민", "황의조", "남태희", "황희찬");
        System.out.println(players);
    }

    @Test
    @DisplayName("replace 메서드")
    public void test5(){
        String players = "손흥민,황의조,남태희,황희찬";
        String replace = players.replace(",", "+");
        System.out.println(replace);
    }

    @Test
    @DisplayName("indexOf 메서드 #1")
    public void test6(){
        String players = "손흥민,황의조,남태희,황희찬";
        int index = players.indexOf(",");
        System.out.println("indexOf(\",\") result >> ");
        System.out.println(index);
        System.out.println();

        int index2 = players.indexOf("#");
        System.out.println("indexOf(\"#\") result >> ");
        System.out.println(index2);
        System.out.println();
    }

    @Test
    @DisplayName("indexOf 메서드 #2 >> 특정위치에서부터 문자열의 출현을 검색하기 ")
    public void test7(){
        String players = "손흥민,황의조,남태희,황희찬";
        int index1 = players.indexOf(",", 3);
        System.out.println(index1);
        int index2 = players.indexOf(",", 4);
        System.out.println(index2);
    }

    @Test
    @DisplayName("lastIndexOf 메서드")
    public void test8(){
        String players = "손흥민,황의조,남태희,네이마르";

        int lastIndex1 = players.lastIndexOf(",");
        System.out.println("lastIndex1 >> " + lastIndex1);

        // String.charAt(4) 에서부터 가장 마지막으로 ","가 나타나는 곳은 String.charAt(3) 이다.
        int lastIndex2 = players.lastIndexOf(",", 4);
        System.out.println("lastIndex2 >> " + lastIndex2);
        System.out.println("char >> " + players.charAt(lastIndex2+1));
    }
}
