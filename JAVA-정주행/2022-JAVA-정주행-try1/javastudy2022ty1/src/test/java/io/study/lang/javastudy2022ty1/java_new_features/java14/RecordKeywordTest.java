package io.study.lang.javastudy2022ty1.java_new_features.java14;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class RecordKeywordTest {

    @Test
    @DisplayName("고전적인 데이터 클래스")
    public void test1(){
        Item item1 = new Item("A book on C", new BigDecimal("10000"));
        Item item2 = new Item("B book on D", new BigDecimal("20000"));
        Item item3 = new Item("A book on C", new BigDecimal("10000"));

        assertThat(item1).isEqualTo(item3);
        assertThat(item1).isNotEqualTo(item2);
    }

    public class Item{
        private final String name;
        private final BigDecimal price;

        public Item(String name, BigDecimal price){
            this.name = name;
            this.price = price;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, price);
        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj){
                return true;
            }
            else if(!(obj instanceof Item)){
                return false;
            }
            else{
                Item other = (Item) obj;
                return Objects.equals(name, other.name) && Objects.equals(price, other.price);
            }
        }

        @Override
        public String toString() {
            return "Item{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }
    }

    public record Item2(String name, BigDecimal price){}

    @Test
    @DisplayName("레코드 사용해보기 #1")
    public void test2(){
        Item2 item = new Item2("A book on C", new BigDecimal("1000"));
        System.out.println(item);
    }

    @Test
    @DisplayName("레코드 사용해보기 #2 = Getter 가 있는지 확인해보자.")
    public void test3(){
        Item2 item = new Item2("A book on C", new BigDecimal("1000"));

        assertThat(item.name()).isEqualTo("A book on C");
        assertThat(item.price()).isEqualTo(new BigDecimal("1000"));
    }

    @Test
    @DisplayName("레코드 사용해보기 #3 = Equals 사용하기")
    public void test4(){
        Item2 item1 = new Item2("A book on C", new BigDecimal("1000"));
        Item2 item2 = new Item2("A book on C", new BigDecimal("1000"));

        assertThat(item1).isEqualTo(item2);
    }

    @Test
    @DisplayName("레코드 사용해보기 #4 = hashCode")
    public void test5(){
        Item2 item1 = new Item2("A book on C", new BigDecimal("1000"));
        Item2 item2 = new Item2("A book on C", new BigDecimal("1000"));

        assertThat(item1.hashCode()).isEqualTo(item2.hashCode());
    }

    @Test
    @DisplayName("레코드 사용해보기 #5 = toString")
    public void test6(){
        Item2 item1 = new Item2("A book on C", new BigDecimal("1000"));
        Item2 item2 = new Item2("A book on C", new BigDecimal("1000"));

        assertThat(item1.toString()).isEqualTo(item2.toString());
        System.out.println(item1.toString());
        System.out.println(item2.toString());
    }

    public record Item3(String name, BigDecimal price){
        public Item3{
            Objects.requireNonNull(name);
            Objects.requireNonNull(price);
        }

        public Item3(String name){
            this(name, new BigDecimal("0"));
        }
    }

    @Test
    @DisplayName("레코드 사용해보기 #6 = 생성자 커스터마이징해서 사용해보기")
    public void test7(){
        Item3 item1 = new Item3("A book on C");
        System.out.println(item1);
    }

    public record Item4(String name, BigDecimal price){
        public static String NO_NAME = "NO_NAME";
        public Item4{
            Objects.requireNonNull(name);
            Objects.requireNonNull(price);
        }

        public static Item4 newItem4(String name, BigDecimal price){
            return new Item4(name, price);
        }

        public static Item4 newEmptyItem(String name){
            return new Item4(name, new BigDecimal("0"));
        }
    }

    @Test
    @DisplayName("레코드 사용해보기 #7 = static 메서드, static 필드 사용해보기")
    public void test8(){
        System.out.println(Item4.NO_NAME);
        System.out.println(Item4.newEmptyItem("A Book on C"));
    }
}
