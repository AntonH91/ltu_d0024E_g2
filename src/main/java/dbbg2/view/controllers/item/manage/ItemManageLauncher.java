package dbbg2.view.controllers.item.manage;

import dbbg2.utils.persistence.JpaPersistence;

public class ItemManageLauncher {
    public static void main(String[] args) {
        JpaPersistence.startUp();
        ItemManageMain.main(args);
    }
}
