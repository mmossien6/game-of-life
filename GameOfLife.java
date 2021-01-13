import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameOfLife {
	public static void main(String[] args) {
		Cell[][] gameBoard = new Cell[20][20];
		Cell[] initialCells;
		initGameBoard(gameBoard);
		int cellStart = getCellOption();
		if (cellStart == 8)
			initialCells = new Cell[8];
		else
			initialCells = new Cell[40];
		storeInitialCells(cellStart, initialCells);

		for (int i = 0; i < gameBoard.length; i++)
			for (int j = 0; j < initialCells.length; j++)
				if (initialCells[j].getLocation() == gameBoard[i][initialCells[j].getColumn() - 1].getLocation())
					gameBoard[i][initialCells[j].getColumn() - 1] = initialCells[j];

		JFrame GUI = new JFrame("Game Of Life");
		GUI.setSize(600, 600);
		GUI.setLocation(new Point(100, 100));
		GUI.setResizable(false);
		GUI.setVisible(true);
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setLayout(new GridLayout(20, 20));
		Container mainContainer = GUI.getContentPane();

		for (int i = 0; i < gameBoard.length; i++)
			for (int j = 0; j < gameBoard.length; j++)
				mainContainer.add(gameBoard[i][j].getColorPanel());
		runGame(gameBoard, mainContainer, GUI);
	}

	public static void runGame(Cell[][] gameBoard, Container mainContainer, JFrame GUI) {
		int userContinue = 0, genCount = 0;
		boolean isMovement;
		while (userContinue == 0) {
			userContinue = JOptionPane.showConfirmDialog(null, "Next Thirty Generations?", "Continue Game", 0, 1);
			if (userContinue == 1)
				break;
			for (int i = 0; i < 30; i++) {
				isMovement = nextGeneration(gameBoard);
				if (isMovement) {
					genCount++;
					redrawBoard(gameBoard, mainContainer);
					try {
						TimeUnit.MILLISECONDS.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					GUI.repaint();
					GUI.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "No More Motion After\n" + genCount + " Generations");
					System.exit(0);
				}
			}
		}
	}

	public static void initGameBoard(Cell[][] gameBoard) {
		for (int i = 0; i < gameBoard.length; i++)
			for (int j = 0; j < gameBoard.length; j++)
				gameBoard[i][j] = new Cell(Color.WHITE, i + 1, j + 1);
	}

	public static int getCellOption() {
		String cellInputString = JOptionPane.showInputDialog(null, "40 Random Cells Or 8 Dictated Cells? [40 Or 8]",
				"Choose Your Cells", 3);
		return Integer.parseInt(cellInputString);
	}

	public static void storeInitialCells(int cellOption, Cell[] initialCells) {
		if (cellOption == 8) {
			for (int i = 0; i < initialCells.length; i++) {
				String rowLocationString = JOptionPane.showInputDialog(null,
						"Enter Row Of Location " + (i + 1) + ": [1-20]", "Choose Your Cells", 3);
				String columnLocationString = JOptionPane.showInputDialog(null,
						"Enter Column Of Location " + (i + 1) + ": [1-20]", "Choose Your Cells", 3);
				initialCells[i] = new Cell(Color.RED, Integer.parseInt(rowLocationString),
						Integer.parseInt(columnLocationString));
				initialCells[i].birth();
			}
		} else {
			Random r = new Random();
			int[] values = new int[20];
			for (int i = 0; i < values.length; i++) {
				values[i] = i + 1;
			}
			int[] randRows = new int[40];
			int[] randColumns = new int[40];
			boolean areDuplicates = true;
			while (areDuplicates) {
				areDuplicates = false;
				for (int i = 0; i < randColumns.length; i++) {
					randRows[i] = r.nextInt(values.length) + 1;
					randColumns[i] = r.nextInt(values.length) + 1;
				}
				for (int i = 0; i < randRows.length && !areDuplicates; i++) {
					for (int j = i + 1; j < randColumns.length && !areDuplicates; j++) {
						if (randRows[i] == randRows[j] && randColumns[i] == randColumns[j])
							areDuplicates = true;
					}
				}
			}
			for (int i = 0; i < initialCells.length; i++) {
				initialCells[i] = new Cell(Color.RED, randRows[i], randColumns[i]);
				initialCells[i].birth();
			}
		}
	}

	public static boolean nextGeneration(Cell[][] gameBoard) {
		boolean isMovement = false;
		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard.length; j++) {
				Cell currentCell = gameBoard[i][j];
				int neighborCount = currentCell.countLiveNeighbors(gameBoard);
				if (neighborCount == 3 && !currentCell.alive() && currentCell.getColor() == Color.WHITE) {
					currentCell.setColor(Color.RED);
					currentCell.birth();
					isMovement = true;
				} else if (currentCell.alive() && (neighborCount < 2 || neighborCount > 3)) {
					isMovement = true;
					if (currentCell.getColor() == Color.RED)
						currentCell.setColor(Color.YELLOW);
					else if (currentCell.getColor() == Color.YELLOW) {
						currentCell.setColor(Color.BLUE);
						currentCell.death();
					}
				}
			}
		}
		return isMovement;
	}

	public static void redrawBoard(Cell[][] gameBoard, Container c) {
		c.removeAll();
		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard.length; j++) {
				c.add(gameBoard[i][j].getColorPanel());
			}
		}
	}
}
