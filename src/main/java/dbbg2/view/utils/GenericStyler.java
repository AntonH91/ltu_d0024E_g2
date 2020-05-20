package dbbg2.view.utils;

import dbbg2.view.utils.nested.ChildController;
import javafx.css.Styleable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
     * <p>Loads an XML file into the given Pane element and returns the controller object for the resource</p>
     * <p>Note that the controller must implement the ChildController interface.</p>
     *
     * @param thePane        The Pane element that the new XML should be loaded into.
     * @param resourceToLoad The FXML resource to be loaded.
     * @return The controller for the newly loaded FXML file
     * @throws IOException        Thrown if the FXMLLoader fails to load the FXML file
     * @throws ClassCastException Thrown if the controller in the loaded code does not implement the ChildController interface.
     */
    public static ChildController loadSinglePane(Pane thePane, String resourceToLoad) throws IOException, ClassCastException {
        ChildController controller;
        FXMLLoader loader = new FXMLLoader(GenericStyler.class.getResource(resourceToLoad));


        Object rootElement = loader.load();
        Pane content;

        // If the root element is a pane, insert it directly
        // Otherwise, wrap the content in a pane, then insert
        if (rootElement instanceof Pane) {
            content = (Pane) rootElement;
        } else {
            content = new Pane();
            content.getChildren().setAll((Node) rootElement);

        }

        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);

        if (thePane instanceof DialogPane) {
            ((DialogPane) thePane).setContent(content);
        } else {

            thePane.getChildren().setAll(content);
            thePane.autosize();

        }
        return loader.getController();

    }

    /**
     * Loads a single pane into an anchor without returning the controller
     *
     * @param thePane        The Pane to be loaded into
     * @param resourceToLoad The string reference to the FXML resource to be loaded.
     * @throws IOException Thrown if loading fails.
     */
    public static void loadSinglePaneWithoutController(Pane thePane, String resourceToLoad) throws IOException {
        try {
            loadSinglePane(thePane, resourceToLoad);
        } catch (ClassCastException ignored) {
            //Logger.getLogger("").log(Level.WARNING, "ClassCastException when fetching FXML.", ignored);
        }
    }


}
