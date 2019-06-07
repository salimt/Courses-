/**
 * 
 */
package week3example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/**
 * @author Christine
 *
 */
public class MazeLoader {

	public static void loadMaze(String filename, Maze maze)
	{
		BufferedReader reader = null;
		try {
            String nextLine;
            int width = 0;
            int height = 0;
            reader = new BufferedReader(new FileReader(filename));
            if ((nextLine = reader.readLine()) != null) {
            	String[] dims = nextLine.split(" ");
            	width = Integer.parseInt(dims[0]);
            	height = Integer.parseInt(dims[1]);
            	maze.initialize(width, height);
            }
            int currRow = 0;
            int currCol = 0;
            while ((nextLine = reader.readLine()) != null) {
            	currCol = 0;
            	for (char c : nextLine.toCharArray()) {
            		if (c != '*') {
            			maze.addNode(currRow, currCol);
            		}
            		currCol++;
            	}
            	while (currCol < width) {
            		maze.addNode(currRow, currCol);
            		currCol++;
            	}
            	currRow++;
            	
            }
            while (currRow < height) {
            	for (int c = 0; c<width; c++) {
            		maze.addNode(currRow, c);
            	}
            	currRow++;
            }
    		reader.close();
		} catch (IOException e) {
            System.err.println("Problem loading maze file: " + filename);
            e.printStackTrace();
        }

		maze.linkEdges();
	}
	
}
