/**
 * A class that represents a maze to navigate through.
 */
package week3example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;

/**
 * A class that represents a 2D maze, represented using a graph.  
 * 
 * @author UCSD Intermediate Programming MOOC Team
 *
 */
public class Maze {
	private MazeNode[][] cells;
	private int width;
	private int height;

	private final int DEFAULT_SIZE = 10;

	/** 
	 * Create a new empty maze with default size 10x10
	 */
	public Maze() {

		cells = new MazeNode[DEFAULT_SIZE][DEFAULT_SIZE];
		this.width = DEFAULT_SIZE;
		this.height = DEFAULT_SIZE;
	}

	/** 
	 * Create a new empty Maze with specified height and width
	 * 
	 * */
	public Maze(int width, int height) {
		cells = new MazeNode[height][width];
		this.width = width;
		this.height = height;
	}

	/**
	 * Reset the maze to have the given height and width
	 * @param width The width of the maze
	 * @param height The height of the maze
	 */
	public void initialize(int width, int height) {
		cells = new MazeNode[height][width];
		this.width = width;
		this.height = height;

	}

	/**
	 * Add a graph node (i.e. not a wall) at the given location.
	 * Any grid entry that doesn't contain a node is interpreted as a wall.
	 * @param row  The row where the node exists
	 * @param col  The column where the node exists
	 */
	public void addNode(int row, int col) {
		cells[row][col] = new MazeNode(row, col);
	}

	/**
	 * Link the nodes that are adjacent (and not null) to each other with an
	 * edge. There is an edge between any two adjacent nodes up, down, left or
	 * right.
	 */
	public void linkEdges() {
		int numRows = cells.length;
		for (int row = 0; row < numRows; row++) {
			int numCols = cells[row].length;
			for (int col = 0; col < numCols; col++) {
				if (cells[row][col] != null) {
					if (row > 0 && cells[row - 1][col] != null) {
						cells[row][col].addNeighbor(cells[row - 1][col]);
					}
					if (col > 0 && cells[row][col - 1] != null) {
						cells[row][col].addNeighbor(cells[row][col - 1]);
					}
					if (row < numRows - 1 && cells[row + 1][col] != null) {
						cells[row][col].addNeighbor(cells[row + 1][col]);
					}
					if (col < numCols - 1 && cells[row][col + 1] != null) {
						cells[row][col].addNeighbor(cells[row][col + 1]);
					}
				}
			}
		}
	}

	/**
	 * Print the maze grid to the screen.
	 */
	public void printMaze() {
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				if (cells[r][c] == null) {
					System.out.print('*');
				} else {
					System.out.print(cells[r][c].getDisplayChar());
				}
			}
			System.out.print("\n");
		}

	}

	/**
	 * Change the display of the maze so that it will print the 
	 * path found from start to goal.
	 * 
	 * NOTE: This method could use redesigning so that it did not expose
	 * the MazeNode class to the outside world.
	 * 
	 * @param path A path of MazeNodes from start to goal.
	 */
	public void setPath(List<MazeNode> path) {
		int index = 0;
		for (MazeNode n : path) {
			if (index == 0) {
				n.setDisplayChar(MazeNode.START);
			} else if (index == path.size() - 1) {
				n.setDisplayChar(MazeNode.GOAL);
			} else {
				n.setDisplayChar(MazeNode.PATH);
			}
			index++;
		}

	}

	/**
	 * Clear (reset) the maze so that it will not disply a path
	 * from start to goal.
	 */
	public void clearPath() {
		for (int r = 0; r < cells.length; r++) {
			for (int c = 0; c < cells[r].length; c++) {
				MazeNode n = cells[r][c];
				if (n != null) {
					n.setDisplayChar(MazeNode.EMPTY);
				}
			}
		}
	}

	/** depth first search from (startRow,startCol) to (endRow,endCol)
	 * 
	 * NOTE: This method is refactored during the videos in week 3.  
	 * The refactored code is shown commented out below.
	 * 
	 * @param startRow  The row of the starting position
	 * @param startCol  The column of the starting position
	 * @param endRow The row of the end position
	 * @param endCol The column of the end position
	 * @return the path from starting position to ending position, or
	 * an empty list if there is no path.
	 */
	public List<MazeNode> dfs(int startRow, int startCol, int endRow, int endCol) {
		
		// Initialize everything
		MazeNode start = cells[startRow][startCol];
		MazeNode goal = cells[endRow][endCol];

		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<MazeNode>();
		}

		HashMap<MazeNode, MazeNode> parentMap = new HashMap<MazeNode, MazeNode>();
		
		HashSet<MazeNode> visited = new HashSet<MazeNode>();
		Stack<MazeNode> toExplore = new Stack<MazeNode>();
		toExplore.push(start);
		boolean found = false;

		// Do the search
		while (!toExplore.empty()) {
			MazeNode curr = toExplore.pop();
			if (curr == goal) {
				found = true;
				break;
			}
			List<MazeNode> neighbors = curr.getNeighbors();
			ListIterator<MazeNode> it = neighbors.listIterator(neighbors.size());
			while (it.hasPrevious()) {
				MazeNode next = it.previous();
				if (!visited.contains(next)) {
					visited.add(next);
					parentMap.put(next, curr);
					toExplore.push(next);
				}
			}
		}
		
		if (!found) {
			System.out.println("No path exists");
			return new LinkedList<MazeNode>();
		}

		// reconstruct the path
		LinkedList<MazeNode> path = new LinkedList<MazeNode>();
		MazeNode curr = goal;
		while (curr != start) {
			path.addFirst(curr);
			curr = parentMap.get(curr);
		}
		path.addFirst(start);
		return path;
	}
	
	
	/** breadth first search from (startRow,startCol) to (endRow,endCol)
	 * 
	 * Note: This method could also stand to be refactored.
	 * 
	 * @param startRow  The row of the starting position
	 * @param startCol  The column of the starting position
	 * @param endRow The row of the end position
	 * @param endCol The column of the end position
	 * @return the path from starting position to ending position, or
	 * an empty list if there is no path.
	 */
	public List<MazeNode> bfs(int startRow, int startCol, int endRow, int endCol) {
		MazeNode start = cells[startRow][startCol];
		MazeNode goal = cells[endRow][endCol];

		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<MazeNode>();
		}

		HashSet<MazeNode> visited = new HashSet<MazeNode>();
		Queue<MazeNode> toExplore = new LinkedList<MazeNode>();
		HashMap<MazeNode, MazeNode> parentMap = new HashMap<MazeNode, MazeNode>();
		toExplore.add(start);
		boolean found = false;
		while (!toExplore.isEmpty()) {
			MazeNode curr = toExplore.remove();
			if (curr == goal) {
				found = true;
				break;
			}
			List<MazeNode> neighbors = curr.getNeighbors();
			ListIterator<MazeNode> it = neighbors.listIterator(neighbors.size());
			while (it.hasPrevious()) {
				MazeNode next = it.previous();
				if (!visited.contains(next)) {
					visited.add(next);
					parentMap.put(next, curr);
					toExplore.add(next);
				}
			}
		}

		if (!found) {
			System.out.println("No path exists");
			return new ArrayList<MazeNode>();
		}
		// reconstruct the path
		LinkedList<MazeNode> path = new LinkedList<MazeNode>();
		MazeNode curr = goal;
		while (curr != start) {
			path.addFirst(curr);
			curr = parentMap.get(curr);
		}
		path.addFirst(start);
		return path;
	}

/*	public List<MazeNode> dfsRefactored(int startRow, int startCol, 
										int endRow, int endCol) {
		// Initialize
		MazeNode start = cells[startRow][startCol];
		MazeNode goal = cells[endRow][endCol];

		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<MazeNode>();
		}

		HashMap<MazeNode, MazeNode> parentMap = new HashMap<MazeNode, MazeNode>();
		boolean found = dfsSearch(start, goal, parentMap);

		if (!found) {
			System.out.println("No path exists");
			return new LinkedList<MazeNode>();
		}

		// reconstruct the path
		return constructPath(start, goal, parentMap);

	}

	private static boolean dfsSearch(MazeNode start, MazeNode goal, 
									HashMap<MazeNode, MazeNode> parentMap) {
		HashSet<MazeNode> visited = new HashSet<MazeNode>();
		Stack<MazeNode> toExplore = new Stack<MazeNode>();
		toExplore.push(start);
		boolean found = false;

		while (!toExplore.empty()) {
			MazeNode curr = toExplore.pop();
			if (curr == goal) {
				found = true;
				break;
			}
			List<MazeNode> neighbors = curr.getNeighbors();
			ListIterator<MazeNode> it = neighbors.listIterator(neighbors.size());
			while (it.hasPrevious()) {
				MazeNode next = it.previous();
				if (!visited.contains(next)) {
					visited.add(next);
					parentMap.put(next, curr);
					toExplore.push(next);
				}
			}
		}
		return found;
	}

	private static List<MazeNode> constructPath(MazeNode start, MazeNode goal, HashMap<MazeNode, MazeNode> parentMap) {
		LinkedList<MazeNode> path = new LinkedList<MazeNode>();
		MazeNode curr = goal;
		while (curr != start) {
			path.addFirst(curr);
			curr = parentMap.get(curr);
		}
		path.addFirst(start);
		return path;
	}

*/
	public static void main(String[] args) {
		String mazeFile = "data/mazes/maze1.maze";
		Maze maze = new Maze();
		MazeLoader.loadMaze(mazeFile, maze);
		maze.printMaze();
		List<MazeNode> path = maze.dfs(3, 3, 2, 0);
		maze.setPath(path);
		System.out.println("\n");
		maze.printMaze();
		maze.clearPath();
		maze.setPath(maze.bfs(3, 3, 2, 0));
		System.out.println("\n");
		maze.printMaze();
	}
}
