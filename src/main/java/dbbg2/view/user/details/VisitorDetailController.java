package dbbg2.view.user.details;

import dbbg2.controllers.user.UserController;
import dbbg2.controllers.user.VisitorController;
import dbbg2.data.users.User;
import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.VisitorCategory;
import dbbg2.utils.persistence.JpaPersistence;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VisitorDetailController implements ChildController, Initializable {
    public ComboBox<VisitorCategory> cbxVisitorCategory;

    public CheckBox chkShowReturned;
    public CheckBox chkShowLate;
    public CheckBox chkShowActive;

    private VisitorController visitorController;


    @Override
    public void initializeUserController(User u) {
        if (u instanceof Visitor) {
            visitorController = new VisitorController();
            visitorController.setUser(u);
        } else {
            throw new ClassCastException("Cannot initialize VisitorDetailController with non-Visitor User");
        }
    }

    @Override
    public UserController getDataController() {
        return visitorController;
    }

    @Override
    public void updateUserData() {
        visitorController.setVisitorCategory(cbxVisitorCategory.getValue());
    }

    @Override
    public void refreshInterface() {
        Visitor v = (Visitor) visitorController.getUser();
        cbxVisitorCategory.setValue(v.getCategory());

    }

    @Override
    public boolean isInputValid() {
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxVisitorCategory.setCellFactory(new Callback<ListView<VisitorCategory>, ListCell<VisitorCategory>>() {
            @Override
            public ListCell<VisitorCategory> call(ListView<VisitorCategory> param) {
                return new ListCell<VisitorCategory>() {
                    @Override
                    protected void updateItem(VisitorCategory item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            this.setGraphic(null);
                        } else {
                            this.setText(item.getCategoryTitle());
                        }
                    }
                };
            }
        });

        cbxVisitorCategory.setButtonCell(cbxVisitorCategory.getCellFactory().call(null));


        loadVisitorCategories();

    }

    private void loadVisitorCategories() {
        EntityManager em = JpaPersistence.getEntityManager();
        List<VisitorCategory> categoryList = em.createQuery("SELECT vc FROM VisitorCategory vc", VisitorCategory.class).getResultList();
        cbxVisitorCategory.setItems(FXCollections.observableArrayList(categoryList));
    }

}
