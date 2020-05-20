package dbbg2.view.item.Edit;

import dbbg2.utils.persistence.JpaPersistence;
import dbbg2.view.item.AddCopy.ItemCopyMain;

public class EditItemLauncher {
    public static void main(String[] args) {
        JpaPersistence.startUp();
        EditItemMain.main(args);
    }
}
