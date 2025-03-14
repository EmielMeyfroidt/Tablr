package test;

import canvaswindow.CanvasWindow;
import main.AbstractView;
import main.MyCanvasWindow;
import main.TablesView;
import main.TablrManager;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class FlowTests {

    private void replaySession(String sessionFile, TablrManager tablrManager) throws InterruptedException {

        AbstractView view = new TablesView(tablrManager);
        java.awt.EventQueue.invokeLater(() -> {
            MyCanvasWindow window = new MyCanvasWindow("My Canvas Window", view);
            tablrManager.addListener(window.getTablrManagerListener());
            window.show();

            CanvasWindow.replayRecording(sessionFile, window);

        });
        Thread.sleep(2000);
    }

    @Test
    public void testAddTable() throws InterruptedException {
        TablrManager tablrManager = new TablrManager();
        replaySession("SessionRecordings/add_table.txt", tablrManager);

        // Validate: Confirm that a new table was added to the TablrManager
        assertEquals(2, tablrManager.getTableNames().size());
    }

    @Test
    public void testRenameTable() throws InterruptedException {
        TablrManager tablrManager = new TablrManager();
        replaySession("SessionRecordings/edit_tableName.txt", tablrManager);

        // Validate: Confirm that the table was renamed
        assertEquals("Table2", tablrManager.getTableNames().getLast());
    }
}

