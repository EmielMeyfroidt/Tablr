/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.TablesView;
import main.TablrManager;

/**
 * 
 */
public class TablesViewTest {

	@Test
	public void test() {
		TablrManager mgr = new TablrManager();
		TablesView view = new TablesView(mgr);
		mgr.addTable();
		assertEquals(view.getPaintData().size(), 1);
		for (int i = 0; i<10; i++) {
			mgr.addTable();
		}
		assertEquals(view.getPaintData().size(), 11);
		assertEquals(view.getPaintData(), mgr.getTableNames());
	}

}
