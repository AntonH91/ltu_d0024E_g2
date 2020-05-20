package dbbg2.view.controllers.item.Edit;

import dbbg2.utils.persistence.JpaPersistence;

public class EditItemLauncher {
    public static void main(String[] args) {
        JpaPersistence.startUp();
        EditItemMain.main(args);
    }
}
