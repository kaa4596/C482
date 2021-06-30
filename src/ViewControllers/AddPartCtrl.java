package ViewControllers;



import java.io.IOException;
import java.util.Optional;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**@author Kaitlynn Abshire
*Add part control class creates an interface that allows users to input values for a part and save it to an index.
 *RUNTIME ERROR - Line 142 and 148 cannot resolve symbols 'partid...etc'. Fixed by ensuring all field referencing
 *matched. For example partID vs partid were used in some places.
 *FUTURE ENHANCEMENT - Have the add part screen pop up next to the original scene so users know
 *which parts already exist and what data in in the tableview.**/
public class AddPartCtrl {

    /**Radio button for inhouse.*/
    @FXML
    private RadioButton partInRadio;
    /**Radio button for outsource.*/
    @FXML
    private RadioButton partOutRadio;
    /**Text inserted for part ID.*/
    @FXML
    private TextField partIdField;
    /**Text inserted for part name.*/
    @FXML
    private TextField partNameField;
    /**Text inserted for part inventory.*/
    @FXML
    private TextField partInvField;
    /**Text inserted for part price.*/
    @FXML
    private TextField partPriceField;
    /**Text inserted for part machine id.*/
    @FXML
    private TextField mIdField;
    /**Text inserted for part min.*/
    @FXML
    private TextField partMinField;
    /**Text inserted for part max.*/
    @FXML
    private TextField partMaxField;

/**Boolean added to help toggle radio buttons and set new text fields depending on selected radio.*/
    private boolean isOutsourced = true;

    @FXML
    private Label machLbl;

/***Toggle group ensures radio buttons switch last field from company to machine ID based on selection.
 * RUNTIME ERROR Toggling originally was just setText on line 70 switched to prompt text so users could not save "mach id" as id.
 *FUTURE ENHANCEMENT The GUI for this field could look nicer. Also a GUI made solely for a list of inHouse parts that displays when toggled
 *would help users compare what they are adding or saving in parts.
 */

    private ToggleGroup InHouseOut;

    /**Inhouse action event that changes last text item to ask for a machine id prompt.*/
    @FXML
    void partIsInhouse(ActionEvent event) {
        isOutsourced = false;
        InHouseOut = new ToggleGroup();
        this.partInRadio.setToggleGroup(InHouseOut);
        this.partOutRadio.setToggleGroup(InHouseOut);
        machLbl.setText("Machine ID");
        mIdField.setPromptText("Mach ID");

    }

/**Outsourced action event that switched the last text field to request a company name.
 * RUNTIME ERROR Originally switching radio buttons inserted normal text in the last field allowing users to save and "companyname" or "machine id"
*would go in as the values. Switching the set to prompt text allows the system to force users to enter before saving.
 *FUTURE ENHANCEMENT The GUI for this field could look nicer. Also a GUI made solely for a list of inHouse parts that displays when toggled
 *would help users compare what they are adding or saving in parts.*/
    @FXML
    void partIsOutsourced(ActionEvent event) {
        InHouseOut = new ToggleGroup();
        this.partInRadio.setToggleGroup(InHouseOut);
        this.partOutRadio.setToggleGroup(InHouseOut);

        isOutsourced = true;
        machLbl.setText("Company");
        mIdField.setPromptText("Name");
    }

/*** Method creates a cancel button that erases entered data and returns to main screen.
 * RUNTIME ERROR N/A
 */
    @FXML
    void cancelAddPartPushed(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all fields, continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/ViewControllers/Main_GUI.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        }
    }

/**Method saves all entered data, returns to main screen, and refreshes main screen tables to reflect change.
 * RUNTIME ERROR Incompatible types. Found: 'null', required: 'boolean' Line 120 changed "=" to "=="
 * RUNTIME ERROR: Attempted a boolean first and it did not filter the text field right at all. Using if then statements ran into same issue where catch was not trigger despite there
 * being a null pointer exception at part name field. Solved using the below if then catch statements. Sorted first by inHouse to accomadate for machine id then out for machine id.
 * Tried using if partNameField.gettext() == "" || null but that ran an error as well. Nullpointerexception was the function I was looking for in this instance.
 * Caused by: java.lang.NumberFormatException: For input string: ""; Fixed by changing non string field catches to Numberformat instead of NullpointerExceptions.
 * Variable 'companyName' is already defined in the scope. Fixed by reusing variable instead of redifining on lines 145 and 151.
 * FUTURE ENHANCEMENTS this code is very lengthy and messy. In the future I would look for simpler ways to code the if then statements or use swing if allowed to create easier methods.
 * Ended up scraping entire long if then statements for separate in house and outhouse and found a way to mold both into a shorter code with a single catch.*/
    @FXML
    void saveAddPartPushed(ActionEvent event) throws IOException {

        int id = 0;
        for (Part part : Inventory.getAllParts()) {

            if (part.getId() >= id)

                id = part.getId() + 1;

        }


        try {
            String name = partNameField.getText();
            int stock = Integer.parseInt(partInvField.getText());
            double price = Double.parseDouble(partPriceField.getText());
            int max = Integer.parseInt(partMaxField.getText());
            int min = Integer.parseInt(partMinField.getText());
            int machineID = Integer.parseInt(mIdField.getText());
            String companyName = mIdField.getText();

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max cannot be lower than min.");
                alert.showAndWait();
            } else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within min and max.");
                alert.showAndWait();

            } else {

                if (partInRadio.isSelected()) {
                    machineID = Integer.parseInt(mIdField.getText());
                    InHouse addPart = new InHouse(id, name, price, stock, min, max, machineID);

                    Inventory.addPart(addPart);
                }
                if (partOutRadio.isSelected()) {
                    companyName = mIdField.getText();
                    Outsourced addPart = new Outsourced(id, name, price, stock, min, max, companyName);

                    Inventory.addPart(addPart);
                }


                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/ViewControllers/Main_GUI.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("There are incorrect values or blank values in text fields.");
            alert.showAndWait();
           return;
        }
    }
}


