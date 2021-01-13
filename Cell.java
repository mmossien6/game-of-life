import java.awt.Color;

public class Cell {
	private Color cellColor;
	private ColorPanel cellPanel;
	private boolean isAlive;
	private int row, column;
	private int lifeStage; // 2-0

	public Cell(Color color, int r, int c) {
		cellColor = color;
		cellPanel = new ColorPanel(cellColor);
		row = r;
		column = c;
		isAlive = false;
		lifeStage = 0;
	}

	public int getLocation() { // 1-400
		return row * column;
	}

	public Color getColor() {
		return cellColor;
	}

	public boolean alive() {
		return isAlive;
	}

	public void birth() {
		isAlive = true;
		lifeStage = 2;
	}

	public void death() {
		isAlive = false;
		lifeStage = 0;
	}

	public void setColor(Color newColor) {
		cellColor = newColor;
		if (cellColor == Color.YELLOW)
			lifeStage--;
	}

	public ColorPanel getColorPanel() {
		cellPanel.setBackground(cellColor);
		return cellPanel;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public int countLiveNeighbors(Cell[][] gameBoard) {
		int neighborCount = 0;
		int row = getRow();
		int column = getColumn();
		// outside edges
		if (row == 1) {
			if (column == 1) {
				if (gameBoard[0][1].alive())
					neighborCount++;
				if (gameBoard[1][1].alive()) // corner 1,1
					neighborCount++;
				if (gameBoard[1][0].alive())
					neighborCount++;
				return neighborCount;
			} else if (column == 20) {
				if (gameBoard[0][18].alive()) // corner 1,20
					neighborCount++;
				if (gameBoard[1][18].alive())
					neighborCount++;
				if (gameBoard[1][19].alive())
					neighborCount++;
				return neighborCount;
			} else {
				if (gameBoard[row - 1][column - 2].alive())
					neighborCount++;
				if (gameBoard[row - 1][column].alive())
					neighborCount++;
				for (int i = 0; i < 3; i++)
					if (gameBoard[row][column - 2 + i].alive())
						neighborCount++;
				return neighborCount;
			}

		} else if (row == 20) {

			if (column == 1) {
				if (gameBoard[18][0].alive())
					neighborCount++;
				if (gameBoard[18][1].alive()) // corner 20,1
					neighborCount++;
				if (gameBoard[19][1].alive())
					neighborCount++;
				return neighborCount;
			} else if (column == 20) {
				if (gameBoard[18][18].alive())
					neighborCount++;
				if (gameBoard[18][19].alive()) // corner 20,20
					neighborCount++;
				if (gameBoard[19][18].alive())
					neighborCount++;
				return neighborCount;
			} else {
				if (gameBoard[row - 1][column - 2].alive())
					neighborCount++;
				if (gameBoard[row - 1][column].alive())
					neighborCount++;
				for (int i = 0; i < 3; i++)
					if (gameBoard[row - 2][column - 2 + i].alive())
						neighborCount++;
				return neighborCount;
			}

		} else { // if row is 2 - 19

			if (column == 1) {
				if (gameBoard[row - 2][column - 1].alive())
					neighborCount++;
				if (gameBoard[row][column - 1].alive())
					neighborCount++;
				for (int i = 0; i < 3; i++)
					if (gameBoard[row - 2 + i][column].alive())
						neighborCount++;
				return neighborCount;
			} else if (column == 20) {
				if (gameBoard[row - 2][column - 1].alive())
					neighborCount++;
				if (gameBoard[row][column - 1].alive())
					neighborCount++;
				for (int i = 0; i < 3; i++)
					if (gameBoard[row - 2 + i][column - 2].alive())
						neighborCount++;
				return neighborCount;
			}

		}
		// all others
		if (gameBoard[row - 1][column - 2].alive())
			neighborCount++;
		if (gameBoard[row - 1][column].alive())
			neighborCount++;
		for (int i = 0; i < 3; i++) {
			if (gameBoard[row - 2][column - 2 + i].alive())
				neighborCount++;
			if (gameBoard[row][column - 2 + i].alive())
				neighborCount++;
		}
		return neighborCount;
	}

}
