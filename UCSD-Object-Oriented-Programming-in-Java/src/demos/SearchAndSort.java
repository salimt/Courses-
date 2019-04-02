package demos;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/** 
 * A class to illustrate some searching and sorting algorithms
 * Used in module 6 of the UC San Diego MOOC Object Oriented Programming in Java
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * 
 *
 */
public class SearchAndSort {
	
	// Read the airpoirts in from the file.
	private static ArrayList<Airport> readFile(String fname) throws IOException
	{
		ArrayList<Airport> airports = new ArrayList<Airport>();
		FileInputStream fis = new FileInputStream(fname);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line;
		while ((line = br.readLine()) != null) 
		{
			String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			for (int i = 0; i < data.length; i++) {
				data[i] = data[i].replace("\"", "");
			}
			int airportID = Integer.parseInt(data[0]);
			String name = data[1];
			String city = data[2];
			String country = data[3];
			String code3 = data[4];
			String code4 = data[5];
			double lat = Double.parseDouble(data[6]);
			double lon = Double.parseDouble(data[7]);
			int alt = Integer.parseInt(data[8]);
			float tz = Float.parseFloat(data[9]);
			char dst = data[10].charAt(0);
			String dbtz = data[11];
			airports.add(new Airport(airportID, name, city, country, 
					code3, code4, lat, lon, alt, tz, dst, dbtz));
		}
		br.close();
		return airports;
	}
	
	// Linear search for the airport code
	public static String findAirportCode(String toFind, 
			ArrayList<Airport> airports)
	{
		int index = 0;
		while (index < airports.size()) {
			if (toFind.equals(airports.get(index).getCity())) {
				return airports.get(index).getCode3();
			}
			index++;
		}
		return null;
	}
	
	// Binary search for the airport code
	// toFind is a city name
	public static String findAirportCodeBS(String toFind, ArrayList<Airport> airports)    {
		int low = 0;
		int high = airports.size()-1;
		int mid;
		while (low <= high) {
			mid = low + ((high-low)/2);
			int compare = toFind.compareTo(airports.get(mid).getCity());
			if (compare < 0) {
				high = mid - 1;
			}
			else if (compare > 0) {
				low = mid+1;
			}
			else return (airports.get(mid)).getCode3();
		}
		return null; 
	}
	
	/** Sort an array of ints using Selection Sort */
	public static void selectionSort( int[] vals )    
	{
		int minI;

		for ( int i=0; i < vals.length-1 ; i++ ) {
			minI = i;
			
			for ( int j=i; j < vals.length; j++ ) {
				if ( vals[j] < vals[minI] ) {
					minI = j ;
				}
			}
			
			swap(vals, minI, i);
		}
		
	}

	
	private static void swap(int[] vals, int ind1, int ind2)
	{
		int temp = vals[ind1];
		vals[ind1] = vals[ind2];
		vals[ind2] = temp;
	}
	
	/** Sort an array of ints using a mystery algorithm */
	public static void mysterySort( int[] vals )    {
		  int currInd;
		  for ( int pos=1; pos < vals.length ; pos++ ) {
		    currInd = pos ;
		    while ( currInd > 0 && 
			    vals[currInd] < vals[currInd-1] ) {
		      swap(vals, currInd, currInd-1);
		      currInd = currInd - 1;
		    }
		  }
		}

	
	public static void main(String[] args) throws IOException
	{
		String datafile = "data/airports.dat";
		ArrayList<Airport> airports = readFile(datafile);
		Collections.sort(airports);
		
		System.out.println(findAirportCodeBS("Žilina", airports));
		
		int[] vals = new int[20];
		Random r = new Random();
		System.out.println("Unsorted ints are...");
		for (int i = 0; i < vals.length; i++) 
		{
			vals[i] = r.nextInt(100);  // Randomly generated int less than 100
			System.out.print(vals[i] + " ");
		}
		System.out.println("\nSorted ints are...");
		
		mysterySort(vals);
		//selectionSort(vals);
		
		for (int i = 0; i < vals.length; i++) 
		{
			System.out.print(vals[i] + " ");
		}
		
		
	}
}
