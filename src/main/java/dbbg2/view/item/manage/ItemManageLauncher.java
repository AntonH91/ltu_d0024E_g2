package dbbg2.view.item.manage;

import dbbg2.utils.persistence.JpaPersistence;
import dbbg2.view.item.search.ItemMain;

public class ItemManageLauncher {
    public static void main(String[] args) {
        JpaPersistence.startUp();
        ItemManageMain.main(args);
    }
}
