import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;
import static org.junit.Assert.*;

@Points("45")
public class GreatestTest {

	public GreatestTest() {
	}

	@Test
	public void test1() {
		int[] syote = {-5, -8, -4, -4};
		assertEquals(virheilmoitus(syote), vastaus(syote), testaa(syote));
	}
	@Test
	public void test2() {
		int[] syote = {42, 5, 9, 42};
		assertEquals(virheilmoitus(syote), vastaus(syote), testaa(syote));
	}
	
	@Test
	public void test3() {
		int[] syote = {5, 42, 9, 42};
		assertEquals(virheilmoitus(syote), vastaus(syote), testaa(syote));
	}

	@Test
	public void test4() {
		int[] syote = {5, 9, 42, 42};
		assertEquals(virheilmoitus(syote), vastaus(syote), testaa(syote));
	}
	
	@Test
	public void test5() {
		int[] syote = {9, 9, 9, 9};
		assertEquals(virheilmoitus(syote), vastaus(syote), testaa(syote));
	}
	
	@Test
	public void test6() {
		int[] syote = {2, 7, 3, 7};
		assertEquals(virheilmoitus(syote), vastaus(syote), testaa(syote));
	}
	
	
	@Test
	public void test7() {
		int[] syote = {0, 0, 0, 0};
		assertEquals(virheilmoitus(syote), vastaus(syote), testaa(syote));
	}


	private int testaa(int[] syote) {
		return Greatest.greatest(syote[0], syote[1], syote[2]);
	}

	private int vastaus(int[] syote) {
		return syote[syote.length - 1];
	}

	private String virheilmoitus(int[] syote) {
		return "Greatest of " + syote[0] + ", " + syote[1] + ", " + syote[2];
	}
}
