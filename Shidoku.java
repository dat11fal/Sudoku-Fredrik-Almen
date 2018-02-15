import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Shidoku {
	private static final int CELLS = 16;
	private static final int ROW_NR = 4, COL_NR = 4;
	private static Random random = new Random();
	private int[][] grid = new int[4][4];
	private List<Integer> sortedList = new ArrayList<Integer>();

	public Shidoku() {
	}

	/**
	 * Initialize the grid
	 */
	private void initBoard() {
		int index = 0, value, attempts = 0;
		boolean retry;
		setList();

		while (!sortedList.isEmpty()) {
			retry = false;
			for (int row = 0; row < ROW_NR; row++) {
				for (int col = 0; col < COL_NR; col++) {
					attempts = 0;
					index = random.nextInt(sortedList.size());
					while (grid[row][col] == 0 && attempts < sortedList.size()) {
						value = sortedList.get(index);
						if (checkRow(row, value) && checkCol(col, value) && checkBox(row, col, value)) {
							sortedList.remove(index);
							grid[row][col] = value;
							break;
						}
						attempts++;
						if (!sortedList.isEmpty())
							index = (index + 1) % sortedList.size();
					}
					if (grid[row][col] == 0) {
						reset();
						retry = true;
						break;
					}
				}
				if (retry) {
					break;
				}
			}
		}
	}

	/**
	 * Sets the numbers to be used on the board
	 */
	private void setList() {
		for (int i = 0; i < ROW_NR * COL_NR; i++) {
			sortedList.add(i % 4 + 1);
		}
	}

	/**
	 * Resets the list and the grid
	 */
	private void reset() {
		sortedList.clear();
		setList();
		for (int row = 0; row < ROW_NR; row++) {
			for (int col = 0; col < COL_NR; col++) {
				grid[row][col] = 0;
			}
		}
	}

	/**
	 * Prints the board in the console
	 */
	private void drawBoard() {
		System.out.println();
		for (int row = 0; row < ROW_NR; row++) {
			for (int col = 0; col < COL_NR; col++) {
				if (row == 2 && col == 0) {
					System.out.println("----+----");
				}
				if (col == 2) {
					System.out.print("| ");
				}
				if (grid[row][col] == 0) {
					System.out.print("  ");
				} else {
					System.out.print(grid[row][col] + " ");
				}
			}
			System.out.println();
		}
	}

	/**
	 * Removes cells from the board
	 */
	private void reduceBoard(int filledCells) {
		int row = 0, col = 0;

		int emptyCells = CELLS - filledCells;
		while (emptyCells > 0) {
			row = random.nextInt(4);
			col = random.nextInt(4);
			if (grid[row][col] != 0) {
				grid[row][col] = 0;
				emptyCells--;
			}
		}
	}

	/**
	 * Checks if the value appears on the row
	 */
	private boolean checkRow(int row, int value) {
		for (int i = 0; i < ROW_NR; i++) {
			if (grid[row][i] == value) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the value appears in the column
	 */
	private boolean checkCol(int col, int value) {
		for (int j = 0; j < COL_NR; j++) {
			if (grid[j][col] == value) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks the boxes of the board
	 */
	private boolean checkBox(int row, int col, int value) {
		if (row < 2) {
			if (col < 2) {
				if (!checkSingleBox(0, 0, value))
					return false;
			} else {
				if (!checkSingleBox(0, 2, value))
					return false;
			}
		} else {
			if (col < 2) {
				if (!checkSingleBox(2, 0, value))
					return false;
			} else {
				if (!checkSingleBox(2, 2, value))
					return false;
			}
		}
		return true;
	}

	/**
	 * Checks if value appears in a box (2x2)
	 */
	private boolean checkSingleBox(int row, int col, int value) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (grid[i + row][j + col] == value) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException {

		Scanner scanner = new Scanner(System.in);
		Shidoku shidoku = new Shidoku();

		shidoku.initBoard();
		shidoku.drawBoard();
		System.out.println();
		System.out.println("How many cells do you want filled?");
		shidoku.reduceBoard(scanner.nextInt());
		shidoku.drawBoard();
		scanner.close();
	}

}
