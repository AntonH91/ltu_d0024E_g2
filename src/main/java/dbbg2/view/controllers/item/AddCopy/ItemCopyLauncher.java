package dbbg2.view.controllers.item.AddCopy;

import dbbg2.utils.persistence.JpaPersistence;
import dbbg2.view.controllers.item.manage.ItemManageMain;

public class ItemCopyLauncher {
    public static void main(String[] args) {
        JpaPersistence.startUp();
        ItemCopyMain.main(args);
    }
}
