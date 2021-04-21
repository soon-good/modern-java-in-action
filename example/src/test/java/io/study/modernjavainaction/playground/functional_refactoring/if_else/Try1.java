package io.study.modernjavainaction.playground.functional_refactoring.if_else;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Try1 {

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
