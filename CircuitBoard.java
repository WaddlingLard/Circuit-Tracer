import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail
 */
public class CircuitBoard {
	/** current contents of the board */
	private char[][] board;
	/** location of row,col for '1' */
	private Point startingPoint;
	/** location of row,col for '2' */
	private Point endingPoint;

	//constants you may find useful
	private final int ROWS; //initialized in constructor
	private final int COLS; //initialized in constructor
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	public CircuitBoard(String filename) throws FileNotFoundException {
		Scanner fileScan = new Scanner(new File(filename));
		Scanner lineReader;
		int rows, cols, firstOccurance = 0, secondOccurance = 0;
		int rowsCounter, colsCounter;
		try {
			String scan = fileScan.nextLine();
			lineReader = new Scanner(scan);
			rows = Integer.parseInt(lineReader.next());
			cols = Integer.parseInt(lineReader.next());
			board = new char[rows][cols];
			if(lineReader.hasNext()) {
				lineReader.close();
				throw new InvalidFileFormatException("Invalid Format in Dimension Parsing (REQUESTING ONLY 2 VALUES)");
			}
			lineReader.close();
			rowsCounter = 0;
			//Loop to iterate and add the elements to board[][]
			while(rowsCounter != rows) {
				colsCounter = 0;
				scan = fileScan.nextLine();
				lineReader = new Scanner(scan);
				while(colsCounter != cols) {
					String element = lineReader.next();
					char character = element.charAt(0);
					if(checkValue(character)) {
						board[rowsCounter][colsCounter] = character;
						if(character == START) {
							startingPoint = new Point(rowsCounter, colsCounter);
							firstOccurance++;
						}else if(character == END) {
							endingPoint = new Point(rowsCounter, colsCounter);
							secondOccurance++;
						}
					}else {
						lineReader.close();
						throw new InputMismatchException();
					}
					colsCounter++;
				}
				//Checking if the line has extra data
				if(lineReader.hasNext()) {
					lineReader.close();
					throw new InvalidFileFormatException("Extraneous Data in File");
				}
				rowsCounter++;
			}
			//Checking if the file didn't satisfy certain conditions
			if(fileScan.hasNextLine()) {
				throw new InvalidFileFormatException("Extraneous Data in File");
			}
			if(firstOccurance != 1 || secondOccurance != 1) {
				throw new InvalidFileFormatException("Invalid File Missing Critical Data");
			}
		}catch(NullPointerException e) {
			throw new InvalidFileFormatException("Missing Data in File");
		}catch(InputMismatchException e) {
			throw new InvalidFileFormatException("Invalid Datatype in File");
		}catch(NumberFormatException e) {
			throw new InvalidFileFormatException("Invalid Datatype in File");
		}catch(NoSuchElementException e) {
			throw new InvalidFileFormatException("Missing Data in File");
		}
			
		
		//TODO: parse the given file to populate the char[][]
		// throw FileNotFoundException if Scanner cannot read the file
		// throw InvalidFileFormatException if any formatting or parsing issues are encountered
		
		ROWS = rows; //replace with initialization statements using values from file
		COLS = cols;
		fileScan.close();
	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}
	
	/** utility method to check current value if it is valid during parsing
	 * @param test char from constructor
	 * @return 	 */
	private boolean checkValue(char test) {
		boolean comparison = false;
		int stringComparison = ALLOWED_CHARS.length(), counter = 0;
		while(comparison != true) {
			if(counter == stringComparison) {
				return false;
			}
			comparison = (test == ALLOWED_CHARS.charAt(counter));
			counter++;
		}
		return comparison;
	}
	
	/** utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point(row,col) */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point(row,col) */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
}// class CircuitBoard
