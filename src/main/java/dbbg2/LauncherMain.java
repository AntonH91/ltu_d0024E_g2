package dbbg2;

import dbbg2.persistence.JpaPersistence;

/**
 * Used to actually start the application so that there is no need to have downloaded JavaFX modules.
 * This way Gradle can handle everything.
 */
public class LauncherMain {
    public static void main(String[] args) {
        JpaPersistence.startUp();
        ApplicationMain.main(args);
    }
}
