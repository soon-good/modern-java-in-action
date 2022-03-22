package io.study.lang.javastudy2022ty1.grammer.file;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaFileTest {

    @Test
    @DisplayName("File 클래스의 기초적인 사용법")
    public void test1(){
        File file1 = new File("C:\\JAVA-MASTER\\test1.txt");
        String pathStr1 = String.valueOf(file1.getAbsolutePath());
        System.out.println(file1.exists());

        File file2 = new File("./inputDir/test1.txt");
        System.out.println(file2.exists());
    }

    @Test
    @DisplayName("윈도우에서는 파일 구분자가 다르다. \\ 이다.")
    public void test2(){
        File file1 = new File("C\\JAVA-MASTER\test1.txt");
        System.out.println(file1.exists());

        File file2 = new File("C:\\JAVA-MASTER\\test1.txt");
        System.out.println(file2.exists());
        System.out.println("문자열 내에서 \\ 을 사용하려면 '\\\\' 으로 문자열을 만들어야 합니다.");
    }

    @Test
    @DisplayName("File Separator 를 사용해보기")
    public void test3(){
        System.out.println("현재 사용하고 있는 운영체제의 디렉터리 구분자는 " + File.separator + " 입니다.");
    }

    @Test
    @DisplayName("File 클래스로 디렉터리만 인식하게끔 해보자.")
    public void test4(){
        File file1 = new File("C:\\JAVA-MASTER");
        System.out.println(file1);
    }

    @Test
    @DisplayName("File 클래스로 디렉터리의 하위 디렉터리를 출력해보자")
    public void test5(){
        File file1 = new File("C:\\JAVA-MASTER");

        System.out.println("C:\\JAVA-MASTER 디렉터리 아래의 모든 디렉터리를 출력해봅니다. >> ");
        for(String file : file1.list()){
            System.out.println(file);
        }
        System.out.println();

        System.out.println("현재 프로젝트의 루트 디렉터리 아래의 모든 파일 객체를 출력해봅니다 >> ");
        File file2 = new File("."); // 현재 프로젝트 디렉터리의 모든 파일 객체를 출력해보자.
        for(String file : file2.list()){
            System.out.println(file);
        }
        System.out.println();
    }

    @Test
    @DisplayName("Paths 클래스를 이용한 디렉터리 지정")
    public void test6(){
        // 절대경로를 파일 구분자 없이 지정하기(윈도우에서만 동작한다 ㅠㅠ)
        // 디렉터리 구분자 없이 지정하는 것은 확실히 유연하고 편리하다.
        Path path1 = Paths.get("C:", "JAVA-MASTER", "test1.txt");
        assertThat(Files.exists(path1)).isTrue();

        // 절대 경로도 Paths 클래스로 지정하는 것 역시 가능하다.(윈도우에서만 동작한다.)
        Path path2 = Paths.get("C:\\JAVA-MASTER\\test1.txt");
        assertThat(Files.exists(path2)).isTrue();

        // C: 가 아닌 C 로 드라이브를 지정하면 디렉터리를 못찾는다.
        Path path3 = Paths.get("C", "JAVA-MASTER", "test1.txt");
        assertThat(Files.exists(path3)).isFalse();

        // 윈도우의 파일 구분자인 \ 을 쓰지 않아도 Path 클래스에서는 /(슬래시)를 사용하고도 Path 를 지정하는 것이 가능하다.
        Path path4 = Paths.get("C:/JAVA-MASTER/test1.txt");
        assertThat(Files.exists(path4)).isTrue();

        // 상대 경로를 파일 구분자 없이 지정하기
        // 디렉터리 구분자 없이 지정하는 것은 확실히 유연하고 편리하다.
        Path path5 = Paths.get("inputDir", "test1.txt");
        assertThat(Files.exists(path5)).isTrue();

        // URI 형식 역시도 Path 클래스로 지정가능하다.
        URI uri = URI.create("file:///C:/JAVA-MASTER/test1.txt");
        Path path6 = Paths.get(uri);
        assertThat(Files.exists(path6)).isTrue();
    }

    @Test
    @DisplayName("디렉터리, 디렉터리 내의 모든 파일들 얻어오기")
    public void test7(){
        Path path1 = Paths.get("inputdir", "test1.txt");
        System.out.println("path1.getParent() = " + path1.getParent());
        assertThat(Files.exists(path1)).isTrue();

        Path path2 = path1.resolveSibling("test2.txt");
        System.out.println("path2 = " + path2);
        assertThat(Files.exists(path2)).isTrue();

        Path path3 = path1.resolveSibling("../README1.md");
        System.out.println("path3.toString() = " + path3.toString());
        System.out.println("path3.normalize().toString() = " + path3.normalize());
        assertThat(Files.exists(path3)).isTrue();
    }

    @Test
    @DisplayName("Path 객체를 File 객체 또는 URI 객체로 변환해보기")
    public void test8(){
        Path path = Paths.get("inputDir", "test1.txt");
        File file = path.toFile();
        System.out.println(file);

        URI uri = path.toUri();
        System.out.println(uri);
    }
}
