package test;

import model.Package;
import model.AmazonWarehouse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static model.PackageType.*;
import static model.AmazonWarehouse.MAX_CAPACITY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WarehouseTest {

    private AmazonWarehouse warehouse;
    private Package p1, p2, p3, p4, p5, p6;

    @Before
    public void setUp() {
        p1 = new Package("Anthony Estey", "Victoria", FOOD, 22.1, true);
        p2 = new Package("Celina Berg", "Vancouver", CLOTHING, 1.1, false);
        p3 = new Package("Nate Bade", "Boston", HOME, 8.4, true);
        p4 = new Package("Richard Anstee", "Bowen Island", SCHOOL, 4.3, false);
        p5 = new Package("John Parker", "New York City", OUTDOOR, 7.4, true);
        p6 = new Package("Reid Holmes", "Toronto", SCHOOL, 2.3, true);
        warehouse = new AmazonWarehouse("Vancouver");
    }

    @Test
    public void testGetters() {
        assertEquals(warehouse.getLocation(), "Vancouver");
        testSizeOfStorage(0);
        testSizeOfShipping(0);
    }

    @Test
    public void testaddPackageNoLimit() {
        testSizeOfStorage(0);
        assertTrue(warehouse.addPackage(p1));
        testSizeOfStorage(1);
    }

    @Test
    public void testaddPackageAtLimit() {
        testSizeOfStorage(0);
        for (int i = 0; i < MAX_CAPACITY; i++) {
            assertTrue(warehouse.addPackage(p1));
        }
        testSizeOfStorage(MAX_CAPACITY);
        assertFalse(warehouse.addPackage(p2));
        testSizeOfStorage(MAX_CAPACITY);
    }

    @Test
    public void testpreparePrimePackages() {
        addPackages(3);
        testSizeOfShipping(0);
        testSizeOfStorage(3);
        warehouse.preparePrimePackages();
        testSizeOfStorage(1);
        testSizeOfShipping(2);
        assertTrue(warehouse.getShippingPackages().contains(p1));
        assertTrue(warehouse.getShippingPackages().contains(p3));
    }

    @Test
    public void testshipLightPackages() {
        addPackages(6);
        testSizeOfShipping(0);
        testSizeOfStorage(6);
        warehouse.shipLightPackages(0);
        testSizeOfShipping(0);
        testSizeOfStorage(6);
        warehouse.shipLightPackages(8.4);
        testSizeOfShipping(4);
        assertTrue(warehouse.getShippingPackages().contains(p6));
        assertTrue(warehouse.getShippingPackages().contains(p5));
        assertTrue(warehouse.getShippingPackages().contains(p4));
        assertTrue(warehouse.getShippingPackages().contains(p2));
        assertEquals(warehouse.getStorage().size(), 2);
    }

    private void testSizeOfStorage(int n) {
        assertEquals(warehouse.getStorage().size(), n);
    }

    private void testSizeOfShipping(int n) {
        assertEquals(warehouse.getShippingPackages().size(), n);
    }

    // REQUIRES: n is constrained by the interval [1,6]
    // EFFECTS: adds packages p1 up to pn, dependent on the given n
    private void addPackages(int n) {
        ArrayList<Package> packageArray = new ArrayList<>();
        packageArray.add(p1);
        packageArray.add(p2);
        packageArray.add(p3);
        packageArray.add(p4);
        packageArray.add(p5);
        packageArray.add(p6);
        for (int i = 0; i < n; i++) {
            warehouse.addPackage(packageArray.get(i));
        }
    }


}