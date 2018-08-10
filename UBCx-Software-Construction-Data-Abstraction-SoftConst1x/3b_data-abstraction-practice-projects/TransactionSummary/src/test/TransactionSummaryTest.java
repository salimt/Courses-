package test;

import model.Transaction;
import model.TransactionSummary;
import org.junit.Before;
import org.junit.Test;

import static model.TransactionType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionSummaryTest {

    private TransactionSummary testSummary;
    private Transaction t1, t2, t3, t4, t5, t6, t7;

    @Before
    public void setUp() {
        testSummary = new TransactionSummary("Donald Knuth");
        t1 = new Transaction("Movie", "May 1st", 10, ENTERTAINMENT);
        t2 = new Transaction("Restaurant", "May 11th", 20, FOOD);
        t3 = new Transaction("Clothes", "May 9", 40, SHOPPING);
        t4 = new Transaction("Textbooks", "May 8", 150, EDUCATION);
        t5 = new Transaction("Korean Food", "May 11", 11, FOOD);
        t6 = new Transaction("Vancouver Symphony", "May 15", 40, ENTERTAINMENT);
        t7 = new Transaction("Cake", "May 4", 5, FOOD);

        testSummary.addTransaction(t1);
        testSummary.addTransaction(t2);
        testSummary.addTransaction(t3);
        testSummary.addTransaction(t4);
        testSummary.addTransaction(t5);
        testSummary.addTransaction(t6);
        testSummary.addTransaction(t7);
    }

    @Test
    public void testConstructor() {
        assertEquals(testSummary.getName(), "Donald Knuth");
    }

    @Test
    public void testAddTransaction() {
        Transaction testTransaction = new Transaction("Test", "Test date", 0, FOOD);
        assertEquals(testSummary.getNumTransactions(), 7);
        testSummary.addTransaction(testTransaction);
        assertEquals(testSummary.getNumTransactions(), 8);
        assertTrue(testSummary.contains(testTransaction));
    }

    @Test
    public void testAverageTransactionCost() {
        TransactionSummary oneSummary = new TransactionSummary("Edsger Dijkstra");
        Transaction testTransaction = new Transaction("Food", "June 12", 5, FOOD);
        oneSummary.addTransaction(testTransaction);
//        assertEquals(oneSummary.averageTransactionCost(), 5, 0.05);
//        assertEquals(testSummary.averageTransactionCost(), (10 + 20 + 40 + 150 + 11 + 40 + 5)/7, 0.05);
    }

    @Test
    public void testLargestTransaction() {
        assertEquals(testSummary.largestTransaction(), t4);
        TransactionSummary oneSummary = new TransactionSummary("Edsger Dijkstra");
        Transaction testTransaction = new Transaction("Food", "June 12", 5, FOOD);
        oneSummary.addTransaction(testTransaction);
        assertEquals(oneSummary.largestTransaction(), testTransaction);
    }

    @Test
    public void testspecificTypeAverage() {
        assertEquals(testSummary.specificTypeAverage(FOOD), (11+5+20)/3, 0.05);
        assertEquals( testSummary.specificTypeAverage(EDUCATION), 150, 0.05);
    }


}