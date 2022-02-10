package minesweeper;

import java.util.List;

public class Field {
	private boolean     isMine;
	private boolean     isOpen;
	private boolean     isMarked;
	private boolean     isAllowedToOpenMine;
	private List<Field> neighbors;
	private String      MARKED_SIGN = "*";
	private String      MINE_SIGN = "X";
	private String      SAFE_SIGN   = ".";
	private String      OPENED_SIGN = "/";

	public Field() {
		this.isMine = false;
	}

	public boolean isMine() {
		return isMine;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void open() {
		isOpen = true;
		openNeighbors(neighbors);
	}

	public void allowOpenMines() {
		isAllowedToOpenMine = true;
	}

	private void openNeighbors(List<Field> neighbors) {
		for (Field field : neighbors) {
			if (!field.isMine && !field.isOpen) {
				field.open();
			}
		}
	}

	public void setMine() {
		isMine = true;
	}

	public void setMarked(boolean isMarked) {
		this.isMarked = isMarked;
	}

	public boolean isMarked() {
		return isMarked;
	}

	public void setNeighbors(List<Field> neighbors) {
		this.neighbors = neighbors;
	}

	@Override
	public String toString() {
		int countOfMines = getCountOfMines();
		if (isOpen) {
			if (countOfMines == 0) {
				return OPENED_SIGN;
			}
			return String.valueOf(countOfMines);
		}
		if (isMarked) {
			return MARKED_SIGN;
		}
		if (isAllowedToOpenMine) {
			return MINE_SIGN;
		}
		return SAFE_SIGN;
	}

	private int getCountOfMines() {
		int count = 0;
		for (Field neighbor : neighbors) {
			if (neighbor.isMine) {
				count++;
			}
		}
		return count;
	}
}
