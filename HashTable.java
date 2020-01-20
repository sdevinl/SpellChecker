/*
 * NAME: Steven Liu
 * PID: A12902940
 */

/**
 * Title: HashTable.java
 * Description: File that can create a hash table.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A class that implements a hash table and its associated methods
 * @author Steven Liu
 * @since 12/4/2019
 */
public class HashTable implements IHashTable {
	private LinkedList<String>[] array;//Array that stores linkedlists
	private int numElem;//Number of element stored in the hash table
	private int expand;//Times that the table is expanded
	private int collision;//Times of collisions occurs
	private int chain; // Longest chain
	private String statsFileName;
	private boolean printStats = false;
	private int size; // size of array
	
	/**
	 * Constructor for hash table
	 * @param size size of the array
	 */
	@SuppressWarnings("unchecked")
	public HashTable(int size) {
		//TODO
		this.size = size;
		array = new LinkedList[size];
		numElem = 0;
		expand = 0;
		collision = 0;
		statsFileName = null;
	}

	/**
	 * Constructor for hash table
	 * @param size size of the array
	 * @param fileName file to write statistics to
	 */
	@SuppressWarnings( "unchecked" )
    public HashTable(int size, String fileName){
		//TODO
		this.size = size;
		array = new LinkedList[size];
		numElem = 0;
		expand = 0;
		collision = 0;
		statsFileName = fileName;
		printStats = true;

	}


	/**
	 * Insert the string value into the hash table
	 * @param value value to insert
	 * @return true if the value was inserted, false if the value was already
	 * present
	 */
    @Override
	public boolean insert(String value) {
		//TODO
		if (value == null) {
			throw  new NullPointerException();
		}

		int index = hashString(value);
		// First element
		if (array[index] == null) {
			array[index] = new LinkedList<>();
		}

		// If array doesn't contain
		if (!array[index].contains(value)) {
			// If array already has at least one element
			if (array[index].size() > 0) {
				// collision
				collision++;
			}

			numElem++;
			array[index].add(value);

			// if length of chain is longer than current
			if (array[index].size() > chain) {
				// new longest chain
				chain = array[index].size();
			}

			// rehash if loadFactor is over 2/3
			double loadFactor = (double) numElem/size;
			if (loadFactor > (double) 2/3) {
				rehash();
			}

			return true;
		}

		return false;
	}

	/**
	 * Delete the given value from the hash table
	 * @param value value to delete
	 * @return true if the value was deleted, false if the value was not found
	 */
	@Override
	public boolean delete(String value) {
		//TODO
		if (value == null) {
			throw  new NullPointerException();
		}

		int index = hashString(value);
		// if index has no list
		if (array[index] == null) {
			return false;
		}

		// if index list doesn't contain value
		if (!array[index].contains(value)) {
			return false;
		}

		// if index contained the longest chain
		if (array[index].size() == chain) {
			chain--;
		}

		array[index].remove(value);
		numElem--;
		return true;

	}

	/**
	 * Check if the given value is present in the hash table
	 * @param value value to look up
	 * @return true if the value was found, false if the value was not found
	 */
	@Override
	public boolean lookup(String value) {
		//TODO
		if (value == null) {
			throw  new NullPointerException();
		}

		int index = hashString(value);

		if (array[index] == null) {
			return false;
		}

		return array[index].contains(value);
	}

	/**
	 * Print the contents of the hash table to stdout.
	 */
	@Override
	public void printTable() {
		//TODO
		for (int i=0; i<array.length; i++ ) {
			Iterator iter = array[i].iterator();
			System.out.print(i + ": ");
			while(iter.hasNext()) {
				System.out.print(iter.next() + " ");
			}
			System.out.print("\n");
		}
	}

	/**
	 * Get the number of elements in the array
	 * @return number of elements in the array
	 */
	@Override
	public int getSize() {
		//TODO
		return numElem;
	}

	/**
	 * String to index
	 * @param value string to convert to an index
	 * @return index of array
	 */
	private int hashString(String value) {
		//TODO
		int HASH_CONSTANT = 27;

		int hashVal = 0;
		for (int i=0; i< value.length(); i++ ) {
			int letter = value.charAt(i);
			hashVal = (hashVal * HASH_CONSTANT + letter) % array.length;
		}
		return hashVal;
	}
	
	/**
	 * Expands the array and rehashes all values.
	 */
	@SuppressWarnings( "unchecked" )
    private void rehash() {
	    //TODO
		expand++;
		int EXPAND_MULTIPLIER = 2;

		if (printStats) {
			printStatistics();
		}

		LinkedList<String>[] oldArray = array;
		array = new LinkedList[oldArray.length * EXPAND_MULTIPLIER];

		//reset instances
		size = oldArray.length * EXPAND_MULTIPLIER;
		collision = 0;
		numElem = 0;

		for (LinkedList<String> strings : oldArray) {
			if (strings != null) {
				for (String s : strings) {
					insert(s);
				}
			}
		}
	}
	
	/**
	 * Print statistics to the given file.
	 * @return True if successfully printed statistics, false if the file
	 *         could not be opened/created.
	 */

	//@Override
	public boolean printStatistics()  {
		//TODO

		DecimalFormat format = new DecimalFormat("#.##");

		double loadFactor =  (double) numElem /  size;
		try {
			PrintStream file = new PrintStream(new FileOutputStream(new File(statsFileName), true));
			file.print(expand + " resizes, load factor " + format.format(loadFactor) + ", " + collision + " collisions "
					+ chain + " longest chain" + "\n");
			file.flush();
			file.close();

		} catch (FileNotFoundException e) {
			return false;
		}

		return true;
	}
}
