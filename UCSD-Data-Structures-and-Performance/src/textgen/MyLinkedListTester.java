/**
 * 
 */
package textgen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author salimt
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
        int b = list1.remove(1);
        assertEquals("Remove: check a is correct ", 42, b);
        assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
        assertEquals("Remove: check size is correct ", 1, list1.size());

        try {
            list1.remove(-1);
            fail("Check out of bounds");
        }
        catch (IndexOutOfBoundsException e) { }
        try {
            list1.remove(list1.size);
            fail("Check out of bounds");
        }
        catch (IndexOutOfBoundsException e) { }
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        list1.add(31);
        assertEquals("Remove: check element 0 is correct ", (Integer)31, list1.get(list1.size-1));
        list1.remove(3);
        assertEquals("Remove: check element 0 is correct ", (Integer)42, list1.get(list1.size-1));
        list1.add(22);
        list1.add(33);
        assertEquals("Remove: check element 0 is correct ", (Integer)33, list1.get(list1.size-1));
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
        assertEquals("Remove: check element 0 is correct ", (Integer)3, (Integer)list1.size());
        list1.add(22);
        list1.add(23);
        assertEquals("Remove: check element 0 is correct ", (Integer)5, (Integer)list1.size());
        list1.remove(0);
        list1.remove(0);
        list1.remove(0);
        list1.remove(0);
        assertEquals("Remove: check element 0 is correct ", (Integer)1, (Integer)list1.size());
        list1.remove(0);
        assertEquals("Remove: check element 0 is correct ", (Integer)0, (Integer)list1.size());
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        list1.add(0, 31);
        assertEquals("Add: check element 0 is correct ", (Integer)31, list1.get(0));
        list1.add(2, 66);
        list1.remove(0);
        assertEquals("Add: check element 0 is correct ", (Integer)66, list1.get(1));

        try {
            list1.add(1, null);
            fail("null cannot be added into the list");
        }
        catch (NullPointerException e) { }
        try {
            list1.add(5, 66);
            fail("Check out of bounds");
        }
        catch (IndexOutOfBoundsException e) { }
        try {
            list1.add(-1, 66);
            fail("Check out of bounds");
        }
        catch (IndexOutOfBoundsException e) { }
		
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
        int a = list1.set(0, 72);
        assertEquals("Add: check element 0 is correct ", (Integer)72, list1.get(0));
        assertEquals("Remove: check a is correct ", 65, a);

        try {
            list1.set(-1, 66);
            fail("Check out of bounds");
        }
        catch (IndexOutOfBoundsException e) { }
        try {
            list1.set(5, 66);
            fail("Check out of bounds");
        }
        catch (IndexOutOfBoundsException e) { }
	}
	
}
