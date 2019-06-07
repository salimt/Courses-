package basicgraph;

import util.*;

import java.util.*;

/** An abstract class that implements a directed graph. 
 * The graph may have self-loops, parallel edges. 
 * Vertices are labeled by integers 0 .. n-1
 * and may also have String labels.
 * The edges of the graph are not labeled.
 * Representation of edges is left abstract.
 * 
 * @author salimt
 * 
 */

public abstract class Graph {

	private int numVertices;
	private int numEdges;
	//optional association of String labels to vertices 
	private Map<Integer,String> vertexLabels;
	
	/**
	 * Create a new empty Graph
	 */
	public Graph() {
		numVertices = 0;
		numEdges = 0;
		vertexLabels = null;
	}

	
	/**
	 * Report size of vertex set
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		return numVertices;
	}
	
	
	/**
	 * Report size of edge set
	 * @return The number of edges in the graph.
	 */	
	public int getNumEdges() {
		return numEdges;
	}
	
	/**
	 * Add new vertex to the graph.  This vertex will
	 * have as its index the next available integer.
	 * Precondition: contiguous integers are used to 
	 * index vertices.
	 * @return index of newly added vertex
	 */
	public int addVertex() {
		implementAddVertex();
		numVertices ++;
		return (numVertices-1);
	}
	
	/**
	 * Abstract method implementing adding a new
	 * vertex to the representation of the graph.
	 */
	public abstract void implementAddVertex();
	
	/**
	 * Add new edge to the graph between given vertices,
	 * @param v Index of the start point of the edge to be added. 
	 * @param w Index of the end point of the edge to be added. 
	 */
	public void addEdge(int v , int w) {
		numEdges ++;
		if (v < numVertices && w < numVertices) {
			implementAddEdge(v , w);			
		}
		else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	/**
	 * Abstract method implementing adding a new
	 * edge to the representation of the graph.
	 */
	public abstract void implementAddEdge(int v, int w);
	
	/**
	 * Get all (out-)neighbors of a given vertex.
	 * @param v Index of vertex in question.
	 * @return List of indices of all vertices that are adjacent to v
	 * 	via outgoing edges from v. 
	 */
	public abstract List<Integer> getNeighbors(int v); 
	
	/**
	 * Get all in-neighbors of a given vertex.
	 * @param v Index of vertex in question.
	 * @return List of indices of all vertices that are adjacent to v
	 * 	via incoming edges to v. 
	 */
	public abstract List<Integer> getInNeighbors(int v);
	
	

	/** 
	 * The degree sequence of a graph is a sorted (organized in numerical order 
	 * from largest to smallest, possibly with repetitions) list of the degrees 
	 * of the vertices in the graph.
	 * 
	 * @return The degree sequence of this graph.
	 */
	public List<Integer> degreeSequence() {
        List<Integer> sequences = new ArrayList <>();
        for(int i=0; i<getNumVertices(); i++){
            sequences.add(getNeighbors(i).size()+getInNeighbors(i).size());
        }Collections.sort(sequences, Collections.reverseOrder());
		return sequences;
	}
	
	/**
	 * Get all the vertices that are 2 away from the vertex in question.
	 * @param v The starting vertex
	 * @return A list of the vertices that can be reached in exactly two hops (by 
	 * following two edges) from vertex v.
	 * XXX: Implement in part 2 of week 2 for each subclass of Graph
	 */
	public abstract List<Integer> getDistance2(int v); 

	/** Return a String representation of the graph
	 * @return A string representation of the graph
	 */
	public String toString() {
		String s = "\nGraph with " + numVertices + " vertices and " + numEdges + " edges.\n";
		s += "Degree sequence: " + degreeSequence() + ".\n";
		if (numVertices <= 20) s += adjacencyString();
		return s;
	}

	/**
	 * Generate string representation of adjacency list
	 * @return the String
	 */
	public abstract String adjacencyString();

	
	// The next methods implement labeled vertices.
	// Basic graphs may or may not have labeled vertices.
	
	/**
	 * Create a new map of vertex indices to string labels
	 * (Optional: only if using labeled vertices.)
	 */
	public void initializeLabels() {
		vertexLabels = new HashMap<Integer,String>();
	}	
	/**
	 * Test whether some vertex in the graph is labeled 
	 * with a given index.
	 * @param v being checked
	 * @return True if there's a vertex in the graph with this index; false otherwise.
	 */
	public boolean hasVertex(int v)
	{
		return v < getNumVertices();
	}
	
	/**
	 * Test whether some vertex in the graph is labeled 
	 * with a given String label
	 * @param s label being checked
	 * @return True if there's a vertex in the graph with this label; false otherwise.
	 */
	public boolean hasVertex(String s)
	{
		return vertexLabels.containsValue(s);
	}
	
	/**
	 * Add label to an unlabeled vertex in the graph.
	 * @param s of the vertex to be labeled.
	 * @param v to be assigned to this vertex.
	 */
	public void addLabel(int v, String s) {
		if (v < getNumVertices() && !vertexLabels.containsKey(v)) 
		{
			vertexLabels.put(v, s);
		}
		else {
			System.out.println("ERROR: tried to label a vertex that is out of range or already labeled");
		}
	}
	
	/**
	 * Report label of vertex with given index
	 * @param v index of the vertex
	 * @return The String label of this vertex 
	 */
	public String getLabel(int v) {
		if (vertexLabels.containsKey(v)) {
			return vertexLabels.get(v);
		}
		else return null;
	}

	/**
	 * Report index of vertex with given label.
	 * (Assume distinct labels for vertices.)
	 * @param s label of the vertex
	 * @return The integer index of this vertex 
	 */
	public int getIndex(String s) {
		for (Map.Entry<Integer,String> entry : vertexLabels.entrySet()) {
			if (entry.getValue().equals(s))
				return entry.getKey();
		}
		System.out.println("ERROR: No vertex with this label");
		return -1;
	}
	

	
	/** Main method provided with some basic tests.  */
	public static void main (String[] args) {
		GraphLoader.createIntersectionsFile("data/maps/ucsd.map", "data/intersections/ucsd.intersections");
		

		// For testing of Part 1 functionality
		// Add your tests here to make sure your degreeSequence method is returning
		// the correct list, after examining the graphs.
		System.out.println("Loading graphs based on real data...");
		System.out.println("Goal: use degree sequence to analyse graphs.");
		
		System.out.println("****");
		System.out.println("Roads / intersections:");
		GraphAdjList graphFromFile = new GraphAdjList();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", graphFromFile);
		System.out.println(graphFromFile);
		
		System.out.println("Observe all degrees are <= 12.");
		System.out.println("****");

		System.out.println("\n****");
		
		// You can test with real road data here.  Use the data files in data/maps
		
		System.out.println("Flight data:");
		GraphAdjList airportGraph = new GraphAdjList();
		GraphLoader.loadRoutes("data/airports/routesUA.dat", airportGraph);
		System.out.println(airportGraph);
		System.out.println("Observe most degrees are small (1-30), eight are over 100.");
		System.out.println("****");
		
		//For testing Part 2 functionality
		// Test your distance2 code here.
		System.out.println("Testing distance-two methods on sample graphs...");
		System.out.println("Goal: implement method using two approaches.");


		
	}
}
