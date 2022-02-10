package minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MineSweeperGame {
	private final int  GRID_SIZE = 9;
	private final Field[][] grid = new Field[GRID_SIZE][GRID_SIZE];
	private int countOfMines;

	public MineSweeperGame(int countOfMines) {
		this.countOfMines = countOfMines;
		initialize();
	}

	private void initialize() {
		createGrid();
		fillGrid(countOfMines);
		setNeighborsToFields();
	}

	public void start() {
		Scanner scanner = new Scanner(System.in).useDelimiter("\\s+");
		while (isPlaying()) {
			printGrid();
			System.out.println("Set/unset mines marks or claim a cell as free:");
			int x = scanner.nextInt() - 1;
			int y = scanner.nextInt() - 1;
			String action = scanner.next();

			if (isWrongCoordinates(y, x)) {
				System.out.println("Wrong coordinates");
				continue;
			}

			Field field = grid[y][x];

			if (field.isOpen()) {
				System.out.println("There is a opened field here!");
				continue;
			}

			switch (action) {
				case "free" :
					exploreFieldAction(field);
					break;
				case "mine" :
					markOrUnmarkAction(field);
					break;
			}
		}
		printGrid();
		System.out.println("Congratulations! You found all mines!");
	}

	/**
	 * Opens field
	 */
	private void exploreFieldAction(Field field) {
		if (field.isMine()) {
			System.out.println("You stepped on a mine and failed!");
			openAllMines();
			printGrid();
			System.exit(0);
		}
		if (field.isMarked()) {
			field.setMarked(false);
		}
		field.open();
	}

	private void openAllMines() {
		for (Field[] fields : grid) {
			for (Field field : fields) {
				field.allowOpenMines();
			}
		}
	}

	private void markOrUnmarkAction(Field field) {
		if (field.isMarked()) {
			field.setMarked(false);
			if (field.isMine()) {
				countOfMines++;
			}
		} else {
			field.setMarked(true);
			if (field.isMine()) {
				countOfMines--;
			}
		}
	}

	private boolean isWrongCoordinates(int y, int x) {
		return (x > GRID_SIZE - 1 || x < 0) || (y > GRID_SIZE - 1 || y < 0);
	}

	private boolean isPlaying() {
		return countOfMines != 0;
	}

	private List<Field> getNeighborsOfField(int y, int x) {
		List<Field> neighbors = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				try {
					Field neighbor = grid[y+i][x+j];
					neighbors.add(neighbor);
				} catch (IndexOutOfBoundsException ignored) {}
			}
		}
		return neighbors;
	}

	/**
	 * Fills in the field with the specified number of mines
	 */
	private void fillGrid(int count) {
		Random random = new Random();
		for (int i = 0; i < count; ) {
			int y = random.nextInt(GRID_SIZE);
			int x = random.nextInt(GRID_SIZE);
			if (isSafe(y, x)) {
				grid[y][x].setMine();
				i++;
			}
		}
	}

	/**
	 * Checks if the field is empty
	 */
	private boolean isSafe(int y, int x) {
		return !grid[y][x].isMine();
	}

	private void setNeighborsToFields() {
		for (int y = 0; y < GRID_SIZE; y++) {
			for (int x = 0; x < GRID_SIZE; x++) {
				grid[y][x].setNeighbors(getNeighborsOfField(y, x));
			}
		}
	}

	/**
	 * Prints game grid
	 */
	private void printGrid() {
		System.out.print(" |");
		for (int i = 1; i < GRID_SIZE + 1; i++) {
			System.out.print(i);
		}
		System.out.println("|");

		System.out.print("-|");
		for (int i = 0; i < GRID_SIZE; i++) {
			System.out.print("-");
		}
		System.out.println("|");

		for (int y = 0; y < GRID_SIZE; y++) {
			System.out.print(y + 1 + "|");
			for (int x = 0; x < GRID_SIZE; x++) {
				System.out.print(grid[y][x]);
			}
			System.out.println("|");
		}

		System.out.print("-|");
		for (int i = 0; i < GRID_SIZE; i++) {
			System.out.print("-");
		}
		System.out.println("|");
	}

	/**
	 * Creates game grid
	 */
	private void createGrid() {
		for (int y = 0; y < GRID_SIZE; y++) {
			for (int x = 0; x < GRID_SIZE; x++) {
				grid[y][x] = new Field();
			}
		}
	}
}
