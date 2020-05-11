package dbbg2.view.item;
import dbbg2.utils.persistence.JpaPersistence;


/**
 * Used to actually start the application so that there is no need to have downloaded JavaFX modules.
 * This way Gradle can handle everything.
 */
public class ItemMainLauncher {
    public static void main(String[] args) {
        JpaPersistence.startUp();
        ItemMain.main(args);
    }
}