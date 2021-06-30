package ViewControllers;


import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**@author Kaitlynn Abshire
*Modify parts controler is used to populate the modify part GUI with data from the main screen and index. It also helps save new data to index.
 *RUNTIME ERROR Modified parts were being saved with an id of plus 1 and saving to wrong index. Fixed on line 155 and line 216.
 *FUTURE ENHANCEMENT Modify part GUI should display a small list of current parts so the user does not have to
 *try to memorize what was on the main gui while modifying.**/
public class ModifyPartCtrl{

    Part selectedPart;
    int selectedIndex;

    /** Button that sets a part to inhouse. */
    @FXML
    private RadioButton partInRadio;
    /** Button that sets a part to outsourced. */
    @FXML
    private RadioButton partOutRadio;
    /** Text that retrieves part id. */
    @FXML
    private TextField partID;
    /** Text that retrieves part name. */
    @FXML
    private TextField partName;
    /** Text that retrieves part inventory. */
    @FXML
    private TextField partInv;
    /** Text that retrieves part price. */
    @FXML
    private TextField partPrice;
    /** Text that retrieves part max. */
    @FXML
    private TextField partMax;
    /** Text that retrieves part min. */
    @FXML
    private TextField partMin;
    /** Text that retrieves part machine id. */
    @FXML
    private TextField machId;
    /** Button that cancels all inserted data. */
    @FXML
    private Button partCancelMod;
    /** Button that saves all changes. */
    @FXML
    private Button partSaveMod;
    /** Text that is the label for the last text field. */
    @FXML
    private Label machLbl;

    /**Toggle group used to allow radio buttons to switch context of last input value.*/
    private ToggleGroup InHouseOut;

    /**Private boolean used to dictate which radio button is selected which helps with toggle.*/
    private boolean isOutsourced = true;

/**Inhouse action event that changes last text item to ask for a machine id prompt.
 * RUNTIME ERROR Originally set machine id to text when toggled which enabled the user to hit save
 *since the box was filled with text. Changed to prompt on line 80.
 *FUTURE ENHANCEMENT GUI shows other inhouse parts upon selection for comparison to modify.*/
    @FXML
    void partHouseSelect(ActionEvent event) {
        isOutsourced = false;
        machLbl.setText("Machine ID");
        InHouseOut = new ToggleGroup();
        this.partInRadio.setToggleGroup(InHouseOut);
        this.partOutRadio.setToggleGroup(InHouseOut);
        machId.setPromptText("Mach ID");

    }
/** function to call for the part inventory*/
    @FXML
    void partInv(ActionEvent event) {

    }
    /** function to call for the part machine id*/
    @FXML
    void partMach(ActionEvent event) {

    }
    /** function to call for the part max*/
    @FXML
    void partMax(ActionEvent event) {

    }
    /** function to call for the part min*/
    @FXML
    void partMin(ActionEvent event) {

    }
    /** function to call for the part machine label*/
    @FXML
    private Label getMachIdLbl;

/***Toggles setting for outsource part and alters text fields.
 * RUNTIME ERROR Originally set machine id to text when toggled which enabled the user to hit save
 *since the box was filled with text. Changed to prompt on line 118.
 *FUTURE ENHANCEMENT GUI shows other outsourced parts upon selection for comparison to modify.
 */
    @FXML
    void partOutSourceSelect(ActionEvent event) {

        machLbl.setText("Company Name");
        InHouseOut = new ToggleGroup();
        this.partInRadio.setToggleGroup(InHouseOut);
        this.partOutRadio.setToggleGroup(InHouseOut);

        isOutsourced = true;
        machLbl.setText("Company Name");
        machId.setPromptText("Comp Name") ;
    }

/**function to call for the part price */
    @FXML
    void partPrice(ActionEvent event) {

    }
/***Cancel button allows removal off data and return to main screen.
 * RUNTIME ERROR N/A
 *FUTURE ENHANCEMENT Highlights on main screen which part was left off on.
 */
    @FXML
    void partCancelModPushed(ActionEvent event) throws IOException {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text fields, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/ViewControllers/Main_GUI.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }

    }

/**Save button allows all data to be saved with return to homescreen and incorporates new data into table.
 * RUNTIME ERROR Did not catch if company name was blank. Fixed by moving string company name up to line 155 so it was
 *in the appropriate try catch block.
 *FUTURE ENHANCEMENT Confirmation of successful save. Highlight altered part on main gui upon arrival.
 */
    @FXML
    void partSaveModPushed(ActionEvent event) throws IOException {
        try {
            int id = selectedPart.getId();
            String name = partName.getText();
            double price = Double.parseDouble(partPrice.getText());
            int stock = Integer.parseInt(partInv.getText());
            int max = Integer.parseInt(partMax.getText());
            int min = Integer.parseInt(partMin.getText());
            int machineID = Integer.parseInt(machId.getText());
            String companyName = machId.getText();

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max cannot be lower than min.");
                alert.showAndWait();
            } else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within min and max.");
                alert.showAndWait();

            } else {
                if (partInRadio.isSelected()) {

                    machineID = Integer.parseInt(machId.getText());

                    InHouse inhousePart = new InHouse(id, name, price, stock, min, max, machineID);
                    Inventory.getAllParts().set(selectedIndex, inhousePart);


                }

                if (partOutRadio.isSelected()) {

                    companyName = machId.getText();

                    Outsourced outsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.getAllParts().set(selectedIndex, outsourcedPart);


                }

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/ViewControllers/Main_GUI.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();}


            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("There are incorrect values or blank values in text fields.");
                alert.showAndWait();
                return;
            }
        }




/**Set parts allows parts to be added to the index after modification to ensure proper details are saved.
 * RUNTIME ERROR - Missing appropriate amount of closed brackets in statements on line 184 and 198. Added closures.
 *Had to use if then statements due to inhouse vs outsourced parts despite table displaying same data.
 *Had tried to just collect main data at first but then had no method to save in vs outhouse.
 *FUTURE ENHANCEMENT Accept timestamp of changes to data.*/
    public void setParts(Part part, int index) {
        selectedPart = part;
        selectedIndex = index;

        if (part instanceof InHouse) {

            InHouse newPart = (InHouse) part;
            partInRadio.setSelected(true);
            machLbl.setText("Machine ID");
            this.partName.setText(newPart.getName());
            this.partInv.setText((Integer.toString(newPart.getStock())));
            this.partPrice.setText((Double.toString(newPart.getPrice())));
            this.partMin.setText((Integer.toString(newPart.getMin())));
            this.partMax.setText((Integer.toString(newPart.getMax())));
            this.machId.setText((Integer.toString(newPart.getMachineID())));

        }

        if (part instanceof Outsourced) {

            Outsourced newPart = (Outsourced) part;
            partOutRadio.setSelected(true);
            machLbl.setText("Company Name");
            this.partName.setText(newPart.getName());
            this.partInv.setText((Integer.toString(newPart.getStock())));
            this.partPrice.setText((Double.toString(newPart.getPrice())));
            this.partMin.setText((Integer.toString(newPart.getMin())));
            this.partMax.setText((Integer.toString(newPart.getMax())));
            this.machId.setText((newPart.getCompanyName()));
        }
    }
}