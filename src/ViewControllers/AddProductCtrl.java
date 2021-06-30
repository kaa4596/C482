package ViewControllers;

import Model.Inventory;
import Model.Part;
import Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**@author Kaitlynn Abshire
 * Class add product control creates a GUI for new product information. Allows users to associate parts with products and save to an index.
 * RUNTIME ERROR Add product initially wouldn't save fixed by indexing appropriately. Associated parts table would not load properly fixed
 * by making sure MAINGUI passed index correctly to ADDPRODUCT.
 *FUTURE ENHANCEMENTS Displaying current list of products in GUI for users. Removing a part from top table when added so it isn't repeatedly added.
 * */


public class AddProductCtrl implements Initializable{

    Product newProduct;

/** Text that retrieves the product name. */
    @FXML
    private TextField productName;
/** Text that retrieves the product id. */
    @FXML
    private TextField productIDText;
    /** Text that retrieves the product inventory. */
    @FXML
    private TextField productInv;
    /** Text that retrieves the product price. */
    @FXML
    private TextField productPrice;
    /** Text that retrieves the product max. */
    @FXML
    private TextField productMax;
    /** Text that retrieves the product min. */
    @FXML
    private TextField productMin;
    /** Text that retrieves the product searchable items. */
    @FXML
    private TextField SearchText;
    /** Table that contains all parts. */
    @FXML
    private TableView<Part> partTable;
    /** Table column that contains id. */
    @FXML
    private TableColumn<Part, Integer> PartID;
    /** Table column that contains name. */
    @FXML
    private TableColumn<Part, String> PartName;
    /** Table column that contains inventory levels. */
    @FXML
    private TableColumn<Part, Integer> PartInvLvl;
    /** Table column that contains prices. */
    @FXML
    private TableColumn<Part, Double> PartPricePU;

    @FXML
    private Button AddProduct;
    /** Table that includes associated parts. */
    @FXML
    private TableView<Part> assocPartTable;
    /** Table column that contains associated id. */
    @FXML
    private TableColumn<Part, Integer> assocPartID;
    /** Table column that contains associated name. */
    @FXML
    private TableColumn<Part, String> assocPartName;
    /** Table column that contains associated inventory. */
    @FXML
    private TableColumn<Part, Integer> assocPartInventory;
    /** Table column that contains associated prices. */
    @FXML
    private TableColumn<Part, Double> assocPartPricePU;
    /** Button used to push action and remove a part. */
    @FXML
    private Button removePart;
    /** Button used to push action and save a part. */
    @FXML
    private Button saveProduct;
    /** Button used to push action and cancel all fields. */
    @FXML
    private Button cancelProduct;
    /** Button used to push action and search for a part in both tables. */
    @FXML
    private Button productAddSearch;

/**Initialize is used ot populate the tables on the GUI with accurate data that is inserted from the main GUI.
 * RUNTIME ERROR - 'Product()' in 'Model.Product' cannot be applied to '(javafx.collections.ObservableList<Model.Part>, int, null, double, int, int, int)'
*Fixed, had accidentally put an observable list command inside of the brackets on line 18 in Product Data.
*This caused it to ask for incorrect input.
*Caused by: java.util.NoSuchElementException Fixed, text field label in html did not match java value.
*FUTURE ENHANCEMENT Part table could include the number of associated parts with a product in a column, so users know
*before they attempt to delete it.*/

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        updatePartTable();

        PartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPricePU.setCellValueFactory(new PropertyValueFactory<>("price"));

        newProduct = new Product(0, null, 0.0, 0, 0, 0);
        assocPartTable.setItems(newProduct.getAllAssociatedParts());

        assocPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPricePU.setCellValueFactory(new PropertyValueFactory<>("price"));



    }

/**Function used to add a new part from the list to the associated parts list/table.
 * RUNTIME ERROR Originally part would not save had wrong variable in line 127 switched to newProduct to
*appropriately match.*/

    @FXML
    void AddProduct(ActionEvent event) throws IOException {
        if (partTable.getSelectionModel().isEmpty()){
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setTitle("No Selection");
            e.setContentText("There is no part selected to associate.");
            e.showAndWait();
            return;
        }
        Part singlePart = partTable.getSelectionModel().getSelectedItem();
        newProduct.addAssociatedParts(singlePart);

    }

/**Method creates a cancel button that erases entered data and returns to main screen.
 * RUNTIME ERROR N/A
*/
    @FXML
    void productCancelPushed(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all entered information, are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/ViewControllers/Main_GUI.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

/**Method enables a selected part in a table to be deleted and confirmation before execution.
 * RUNTIME ERROR N/A
*FUTURE ENHANCEMENTS Use boolean statement linked to Inventory instead of creating a new variable in addProduct.
*/
    @FXML
    void removePartOfficially(ActionEvent event) {
        if (assocPartTable.getSelectionModel().isEmpty()){
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setTitle("No Selection");
            e.setContentText("There is no associated part selected to delete.");
            e.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the entire Part, are you sure?");
        alert.setTitle("Deletion");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            newProduct.getAllAssociatedParts().remove(assocPartTable.getSelectionModel().getSelectedItem());

        }

    }


/**Method saves all entered data, returns to main screen, and refreshes main screen tables to reflect change.
 * RUNTIME ERROR - 'setProductmax(int)' in 'Model.Product' cannot be applied to '(javafx.scene.control.TextField)'
*Had unmatching value names that needed to be fixed.
*RUNTIME ERROR New added products had same ID as last product in table. This method fixed it by adding
*1 to the product ID. on line 177.
*FUTURE ENHANCEMENT Adding a confirmation that it saved correctly or have added product highlighted on main screen on return.
*This method associates parts with the products on line 229 -233.
 * RUNTIME ERROR Associated parts would not originally save to the product creating the array
 *enabled a smoother format to saving the parts to the product.
 *RUNTIME ERROR First attempt line 204 of setting index for a new product but did not work appropriately.
 * Inventory.getAllProducts().set(newProductID - 1, newProduct);*/

    @FXML
    void productSavePushed(ActionEvent event) throws IOException {

        int newProductID = 0;
        for (Product product : Inventory.getAllProducts())

            if (product.getId() >= newProductID)

                newProductID = product.getId() + 1;


        try {
            String name = productName.getText();
            int stock = Integer.parseInt(productInv.getText());
            double price = Double.parseDouble(productPrice.getText());
            int max = Integer.parseInt(productMax.getText());
            int min = Integer.parseInt(productMin.getText());


            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max cannot be lower than min.");
                alert.showAndWait();
                return;
            } else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within min and max.");
                alert.showAndWait();
                return;
            }

            Product newProduct = new Product(newProductID, name, price, stock, min, max);



            newProduct.setId(newProductID);
            ArrayList<Part> assocparts = new ArrayList<>();
            assocparts.addAll(assocPartTable.getItems());
            newProduct.arrayAssociatedParts(assocparts);
            Inventory.getAllProducts().add(newProduct);





            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/ViewControllers/Main_GUI.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();


        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("There are incorrect values or blank values in text fields.");
            alert.showAndWait();
            return;
        }
    }

/**Methods used to search for a designated part in the table.
 * RUNTIME ERROR Originally line 240 would only return exactly matching text in the search box.
*Adding toUppercase in line 242 to the variable ensures that whatever is typed in search is
*set to the same format so it checks all variations appropriately.
*FUTURE ENHANCEMENTS The search box could auto suggests something to be filled in after a
*portion of a word is typed in.
*/
    @FXML
    void productAddSearch(ActionEvent event) {

        String searchString = SearchText.getText();

        if (searchString.isEmpty()) {
            //Search box is empty.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Empty");
            alert.setHeaderText("Empty");
            alert.setContentText("Search box is empty.");
            alert.showAndWait();
        } else {{
            //Input in the search box.
            String x = SearchText.getText();
            x = x.toUpperCase();
            partTable.getSelectionModel().clearSelection();
            partTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            for (Part part : Inventory.getAllParts()) {

                Part p = Inventory.searchPart(x, part);
                partTable.getSelectionModel().select(p);


            }
            //No match in table.
            if (partTable.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle("No Part");
                alert.setHeaderText("Could Not Find");
                alert.setContentText("Part does not exist.");
                alert.showAndWait();
                return;
            }
        }

    }
}
/**Methods used to refresh the part table with the new parts added and refresh the inventory.*/
    public void updatePartTable() {
        partTable.setItems(Inventory.getAllParts());

    }
}