package org.thor.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.thor.integration.InventoryCatalog;

import static org.junit.Assert.fail;

public class InventoryCatalogTest {

    private InventoryCatalog inventoryCatalog;

    @Before
    public void setUp() {
        inventoryCatalog = new InventoryCatalog();
    }

    @After
    public void tearDown() {
        inventoryCatalog = null;
    }

    @Test
    public void getItemById_Success() {
        try {
            Assert.assertEquals("7350042710102", inventoryCatalog.getItemById("7350042710102", 1).getItemId());                 // String
            Assert.assertEquals(20.3 + (20.3 * 0.12), inventoryCatalog.getItemById("7340001803525", 1).getPrice().getPrice(), 0.000001);               // Double
            Assert.assertEquals(27.5, inventoryCatalog.getItemById("7340888803525", 1).getPrice().getVat(), 0.000001);                   // Double 20 kr moms
            Assert.assertEquals(25, inventoryCatalog.getItemById("7392257101382", 1).getPrice().getVatPercentage(), 0.00001);        // int 25% moms
            Assert.assertEquals("Mycket god Sur lakrits", inventoryCatalog.getItemById("7392257101337", 1).getDescription());   // String Beskrivning
            Assert.assertEquals(5, inventoryCatalog.getItemById("7070866022716", 5).getQuantity());                                //int antal
        } catch (DatabaseFailureException | InvalidItemIdException exception) {
            fail("Failed due to exception: " + exception.getMessage());
        }
    }
    @Test
    public void getItemById_negativeQuantity() {
        try {
            Assert.assertEquals(0, inventoryCatalog.getItemById("7350042710102", -5).getQuantity());
        } catch (DatabaseFailureException | InvalidItemIdException exception) {
            fail("Failed due to exception: " + exception.getMessage());
        }
    }

    @Test
    public void getItemById_InvalidIdException() {
        try {
            inventoryCatalog.getItemById("NotValidId", 1);
            fail("Failed test due to invalid item ID found");
        } catch (DatabaseFailureException | InvalidItemIdException exception) {
            Assert.assertEquals("Invalid item identifier. Item identifier can not be found in database.", exception.getMessage());
        }
    }

    @Test
    public void getItemById_DatabaseException() {
        try {
            inventoryCatalog.getItemById("DBTest", 1);
            fail("Failed test due to database could be reached");
        } catch (DatabaseFailureException | InvalidItemIdException exception) {
            Assert.assertEquals("Database connection failure.", exception.getMessage());
        }
    }
}