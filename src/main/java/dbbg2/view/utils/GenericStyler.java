package dbbg2.view.utils;

import javafx.css.Styleable;

public class GenericStyler {

    /**
     * Sets the presence of the Invalid class on an element to indicate to the user if the element has a valid input or not.
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
}
