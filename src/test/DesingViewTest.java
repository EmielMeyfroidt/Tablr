/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.DesignView;
import main.Table;
import main.TablrManager;

/**
 * 
 */
public class DesingViewTest {

	@Test
	public void test() {
		TablrManager mgr = new TablrManager();
		Table table = new Table("Table0");
		DesignView view = new DesignView(mgr, table);
		table.addColumn();
		assertEquals(view.getPaintData().size(), 1);
		for (int i = 0; i < 9; i++) {
			table.addColumn();
		}
		assertEquals(view.getPaintData().size(), 10);
		assertEquals(view.getPaintData(), table.getColumnsInfo());
		for (String s : view.getPaintData()) {
			System.out.println(s);
		}
	}

}
