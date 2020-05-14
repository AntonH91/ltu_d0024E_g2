package dbbg2.view.user.details;

import dbbg2.controllers.user.UserController;
import dbbg2.controllers.user.VisitorController;
import dbbg2.data.users.User;
import dbbg2.data.users.Visitor;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class VisitorDetailController implements ChildController, Initializable {
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

    }

    @Override
    public void refreshInterface() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
