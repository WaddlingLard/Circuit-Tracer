import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 */
public class CircuitTracer {

	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		new CircuitTracer(args); //create this with args
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private void printUsage() {
		System.out.println("java CircuitTracer [-q queue| -s stack] [-c console| -g GUI] filename");
		//TODO: print out clear usage instructions when there are problems with
		// any command line args
		// See https://en.wikipedia.org/wiki/Usage_message for format and content guidance
	}

	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 */
	public CircuitTracer(String[] args) {
		//TODO: parse and validate command line args - first validation provided
		TraceState trace;
		Point startingPoint;
		CircuitBoard board = null;
		Storage stateStore = null;
		ArrayList<TraceState> bestPaths = new ArrayList<TraceState>();
		if (args.length != 3) {
			printUsage();
			return; //exit the constructor immediately
		}else if(args[0].toString().compareTo("-q") != 0 && args[0].toString().compareTo("-s") != 0){
			printUsage();
			return;
		}else if(args[1].toString().compareTo("-g") == 0) { //GUI isn't available so its a separate condition
			System.out.println("GUI not avaliable");
			printUsage();
			return;
		}else if(args[1].toString().compareTo("-c") != 0) {
			printUsage();
			return;
		}
		//creating of the circuit board using user input file
		try {
			board = new CircuitBoard(args[2].toString());
		}catch(FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			printUsage();
			return;
		}catch(InvalidFileFormatException e){
			System.out.println("InvalidFileFormatException");
			printUsage();
			return;
		}catch(Exception e) {
			System.out.println("InvalidFileFormatException");
			printUsage();
			return;
		}
		
		//TODO: initialize the Storage to use either a stack or queue
		//TODO: read in the CircuitBoard from the given file
		//TODO: run the search for best paths
		//TODO: output results to console or GUI, according to specified choice

		if(args[0].toString().compareTo("-q") == 0) {//Using a queue (breadth search)
			stateStore = new Storage(Storage.DataStructure.queue);
		}

		if(args[0].toString().compareTo("-s") == 0) {//Using a stack (depth search)
			stateStore = new Storage(Storage.DataStructure.stack);
		}
		startingPoint = new Point(board.getStartingPoint().x, board.getStartingPoint().y);
		//addition of initial trace states
		try {
			trace = new TraceState(board, startingPoint.x - 1, startingPoint.y);
			stateStore.store(trace);
		}catch(OccupiedPositionException e) {
		}catch(Exception e) {
		}

		try {
			trace = new TraceState(board, startingPoint.x + 1, startingPoint.y);
			stateStore.store(trace);
		}catch(OccupiedPositionException e) {
		}catch(Exception e) {
		}
		
		try {
			trace = new TraceState(board, startingPoint.x, startingPoint.y - 1);
			stateStore.store(trace);
		}catch(OccupiedPositionException e) {
		}catch(Exception e) {
		}
		
		try {
			trace = new TraceState(board, startingPoint.x, startingPoint.y + 1);
			stateStore.store(trace);
		}catch(OccupiedPositionException e) {
		}catch(Exception e) {
		}
		//searching algorithm using either queue or stack
		while(!stateStore.isEmpty()) {
			TraceState current = (TraceState) stateStore.retrieve();
			if(current.isComplete()) {
				if(bestPaths.isEmpty() || current.getPath().size() == bestPaths.get(0).getPath().size()) {
					bestPaths.add(current);
				}else if(current.getPath().size() < bestPaths.get(0).getPath().size()) {
					bestPaths.clear();
					bestPaths.add(current);
				}
			}else {
				if(current.isOpen(current.getRow() - 1, current.getCol())) {
					trace = new TraceState(current, current.getRow() - 1, current.getCol());
					stateStore.store(trace);
				}
				if(current.isOpen(current.getRow() + 1, current.getCol())) {
					trace = new TraceState(current, current.getRow() + 1, current.getCol());
					stateStore.store(trace);
				}
				if(current.isOpen(current.getRow(), current.getCol() - 1)) {
					trace = new TraceState(current, current.getRow(), current.getCol() - 1);
					stateStore.store(trace);
				}
				if(current.isOpen(current.getRow(), current.getCol() + 1)) {
					trace = new TraceState(current, current.getRow(), current.getCol() + 1);
					stateStore.store(trace);
				}
				
			}
		}
		for(int i = 0; i < bestPaths.size(); i++) {
			System.out.println(bestPaths.get(i).toString());
		}
		
	}

} // class CircuitTracer
