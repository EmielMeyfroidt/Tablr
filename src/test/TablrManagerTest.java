/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import main.TablrManager;

/**
 * 
 */
public class TablrManagerTest {
	
	@Test
	public void addTableTest() {
		TablrManager mgr = new TablrManager();
		mgr.addTable();
		assertEquals(mgr.getTableNames().size(), 1);
		for (int i = 0; i < 9; i++) {
			mgr.addTable();
		}
		assertEquals(mgr.getTableNames().size(), 10);
		assertTrue(mgr.getTableNames().stream().distinct().count() == mgr.getTableNames().size());
	}
	
	@Test
	public void removeTableTest() {
		TablrManager mgr = new TablrManager();
		for (int i = 0; i < 2; i++) {
			mgr.addTable();
		}
		assertEquals(mgr.getTableNames().size(), 2);
		assertTrue(mgr.getTableNames().contains("Table0"));
		assertTrue(mgr.getTableNames().contains("Table1"));
		mgr.removeTable("Table0");
		assertEquals(mgr.getTableNames().size(), 1);
		assertFalse(mgr.getTableNames().contains("Table0"));
		assertTrue(mgr.getTableNames().contains("Table1"));
	}
	
	@Test
	public void changeNameTest() {
		TablrManager mgr = new TablrManager();
		mgr.addTable();
		mgr.changeName("Table0", "New Name");
		assertTrue(mgr.getTableNames().contains("New Name"));
		assertFalse(mgr.getTableNames().contains("Table0"));
	}

}
