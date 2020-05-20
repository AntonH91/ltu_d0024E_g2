package dbbg2.view.utils;

import dbbg2.view.utils.nested.ChildController;
import javafx.css.Styleable;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GenericStyler {

    /**
     * <p>Sets the presence of the Invalid class on an element to indicate to the user if the element has a valid input or not.</p>
     *
     * @param styleable The styleable element to be changed
     * @param valid     True if the input is valid, false otherwise
     */
    public static void markValidity(Styleable styleable, boolean valid) {

        if (!valid) {
            styleable.getStyleClass().add("invalid");

        } else {
            styleable.getStyleClass().removeAll("invalid");
        }

    }

    /**
     * <p>Loads an XML file into the given anchor element and returns the controller object for the resource</p>
     * <p>Note that the controller must implement the ChildController interface.</p>
     *
     * @param anchor         The AnchorPane element that the new XML should be loaded into.
     * @param resourceToLoad The FXML resource to be loaded.
     * @return The controller for the newly loaded FXML file
     * @throws IOException        Thrown if the FXMLLoader fails to load the FXML file
     * @throws ClassCastException Thrown if the controller in the loaded code does not implement the ChildController interface.
     */
    public static ChildController loadSinglePane(AnchorPane anchor, String resourceToLoad) throws IOException, ClassCastException {
        ChildController controller;

        FXMLLoader loader = new FXMLLoader(GenericStyler.class.getResource(resourceToLoad));
        anchor.getChildren().setAll((AnchorPane) loader.load());

        return loader.getController();

    }

    /**
     * Loads a single pane into an anchor without returning the controller
     *
     * @param anchor         The AnchorPane to be loaded
     * @param resourceToLoad The string reference to the FXML resource to be loaded.
     * @throws IOException Thrown if loading fails.
     */
    public static void loadSinglePaneWithoutController(AnchorPane anchor, String resourceToLoad) throws IOException {
        try {
            loadSinglePane(anchor, resourceToLoad);
        } catch (ClassCastException ignored) {

        }
    }


}
