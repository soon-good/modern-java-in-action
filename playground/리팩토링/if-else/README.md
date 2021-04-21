# if ~ else 문 리팩토링 연습

DFS 또는 Union Find 를 풀어보다보면 인덱스가 0에 닿는지, 배열의 상한 값에 도달했는지를 체크해야 한다. DFS에서는 상하좌우를 움직이면서 Index Out Of Bounds 가 되지 않기 위해 노력해야 하고, Union Find의 경우 배열기반 트리로 Union Find를 구성할 때 인덱스 체크가 필요하다.  

  

알고리즘을 풀어보다가 **'이런 단순 if ~ else 조건식을 하나의 변수처럼 할당해서 재사용하면 어떨까?'** 하고 생각했다.



## 목차

- [CASE 1 - 단순 적용 with BiFunction](#case-1---단순적용-with-bifunction)
- [CASE 2](#CASE-2)



## CASE 1 - 단순적용 with BiFunction

단순하게 INDEX 값을 넘겨서 테스트하는 방식이다. 메모리 제한 조건에 걸리지 않는다는 확신이 든다는 장점이 있는 것 같다. 객체지향적으로 더 고도화 시키는 것은 뒤에서 더 추가할 예정이다.  

예를 들면 아래와 같은 구문이 있다고 해보자.  

```java
package io.study.modernjavainaction.playground.functional_refactoring.if_else;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Try1 {

	public void solution1(int row_size, int col_size, String [][] array){
		int row = 1; int col = 1;

		final String ISLAND = "1";
		final String WATER 	= "0";

		// 상
		if(row-1 >= 0 && array[row-1][col] == ISLAND){
			System.out.println("위에 노드를 방문했는데 육지에요.");
		}
	}

	public static void main(String [] args){
		String [][] array = {
			{"1", "1", "0", "0", "0"},
			{"1", "1", "0", "0", "1"},
			{"1", "0", "0", "0", "1"}
		};
		int row_size = array.length;
		int col_size = array[0].length;

		Try1 t = new Try1();
		t.solution1(row_size, col_size, array);
	}
}
```



이 경우 출력값은 아래와 같다.

```plain
위에 노드를 방문했는데 육지에요.
```



이것을 더 BiFunction\<T,U,R\> 을 이용해 조건식을 만들어서 적용해봤다. 아직 이 정도 까지는 누구나 생각할 수 있을 법해보인다. 

```java
public class Try1 {

	public void solution1(int row_size, int col_size, String [][] array){
		int row = 1; int col = 1;

		final String ISLAND = "1";
		final String WATER 	= "0";

		// 상
		if(row-1 >= 0 && array[row-1][col] == ISLAND){
			System.out.println("위에 노드를 방문했는데 육지에요.");
		}

		BiFunction<Integer, Integer, Boolean> fnIndexZeroCheck_and_Island = (_row, _col)->{
			boolean result = false;

			if(_row >=0 && array[_row][_col] == ISLAND){
				result = true;
			}
			else{
				result = false;
			}

			return result;
		};

		BiFunction<Integer, Integer, Boolean> fnIndexMaxCheck_and_Island = (_row, _col)->{
			boolean result = false;

			if(_row < array.length && array[_row][_col] == ISLAND){
				result = true;
			}
			else {
				result = false;
			}

			return result;
		};

		Boolean zero_check = fnIndexZeroCheck_and_Island.apply(row - 1, col);
		System.out.println("index zero check = " + zero_check);

		Boolean max_check = fnIndexMaxCheck_and_Island.apply(row + 1, col);
		System.out.println("index max check = " + max_check);
	}

	// 또는 클래스 하나에 래핑해서 아래와 같이 할 수도 있을 것 같다.
	class MatrixUnit{
		int row;
		int col;
		String [][] array;

		public MatrixUnit(int row, int col, String[][] array){
			this.row = row;
			this.col = col;
			this.array = array;
		}
	}

	public static void main(String [] args){
		String [][] array = {
			{"1", "1", "0", "0", "0"},
			{"1", "1", "0", "0", "1"},
			{"1", "0", "0", "0", "1"}
		};
		int row_size = array.length;
		int col_size = array[0].length;

		Try1 t = new Try1();
		t.solution1(row_size, col_size, array);
	}
}
```


출력결과

```plain
위에 노드를 방문했는데 육지에요.
index zero check = true
index max check = false
```



아직까지는 아래와 같은 BiFunction으로 index 가 0에 도달하는지만을 체크하고 있다.

```java
BiFunction<Integer, Integer, Boolean> fnIndexZeroCheck_and_Island = (_row, _col)->{
  boolean result = false;

  if(_row >=0 && array[_row][_col] == ISLAND){
    result = true;
  }
  else{
    result = false;
  }

  return result;
};
// ...
Boolean zero_check = fnIndexZeroCheck_and_Island.apply(row - 1, col);
System.out.println("index zero check = " + zero_check);
// ...
```

  

내 목표는 row -1 도 하나의 산식으로 만들어서 람다화 하는 것이 목표이다.   

  

## CASE 2

일단 아래와 같이 람다를 포함하고 있는 Position 클래스를 만들었다. 알고리즘 테스트 프로그램에서는 메모리 제약조건에 위배될 수 있을 것 같기는 하지만, static 하게 구현하거나, 싱글턴으로 처리할 수 있는 부분을 잘 판단해서 적용하면 어떨까 생각해보긴 했다.

```java
class Try1{
  
  // ...
  
  static class Position{
		int row;
		int col;

		public Position(int row, int col){
			this.row = row;
			this.col = col;
		}

		Function<Position,Position> topperApplier = (pos -> {
			return new Position(pos.row+1, pos.col);
		});

		public Position getTopperPosition(Position pos){
			return this.topperApplier.apply(pos);
		}
	}
  
  // ...
  
}
```



점심시간이 끝나가서 일단은 저녁에 정리를 다시 시작해야 할 것 같다.