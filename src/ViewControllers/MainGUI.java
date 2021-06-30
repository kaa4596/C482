package ViewControllers;

import Model.Inventory;
import Model.Part;
import Model.Product;
import ViewControllers.ModifyProductCtrl;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import static Model.Inventory.lookupPartId;
import static Model.Inventory.lookupPartName;

/**@author Kaitlynn Abshire

 *MainGUI is the home screen of the app including a parts and product table view with various action buttons and search bars.
 * RUNTIME ERROR Class not initialized. Struggled with acknowledging when to initialize a class vs not.
 *FUTURE ENHANCEMENT Link to associated parts list or other table view columns like machine id could be added
 * to display more information.*/
public class MainGUI implements Initializable {

    @FXML
    private TextField partSearchBox;

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableColumn<Part, Integer> PartID;

    @FXML
    private TableColumn<Part, String> PartName;

    @FXML
    private TableColumn<Part, Integer> PartInvLvl;

    @FXML
    private TableColumn<Part, Double> PricePU;

    @FXML
    private Button partAdd;

    @FXML
    private Button partModify;

    @FXML
    private Button partDelete;


    @FXML
    private TextField productSearchBox;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> ProductID;

    @FXML
    private TableColumn<Product, String> ProductName;

    @FXML
    private TableColumn<Product, Integer> ProductInvLvl;

    @FXML
    private TableColumn<Product, Double> ProductPricePU;

    @FXML
    private Button productAdd;

    @FXML
    private Button productModify;

    @FXML
    private Button productDelete;

    @FXML
    private Button exitButton;

    @FXML
    private Button ModifyProducts;

    @FXML
    private Button ModifyParts;

/** Sets a static integer for the index for a product. */
    private static int modifiedProductIndex;
/** Returns a static integer for the index for a product. */
    public static int getModifyProductIndex(){ return modifiedProductIndex;}

/**Initialize incorporates data into the main table views from an index and inventory.
 * RUNTIME ERRORS - Lots of errors caused by missnaming/same values but capital vs not. Fixed by uniform naming.
 *FUTURE ENHANCEMENT - Login screen with username entry/pass needed. Need help button with contact information to
 *those who can help users who experience errors.
 *Somewhere for errors to be reported.
 *Search bars have no limit and could be overloaded by those with mal intent.*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        productTable.setItems(Inventory.getAllProducts());

        ProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPricePU.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTable.setItems(Inventory.getAllParts());

        PartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PricePU.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

/**Search bars that allow user to scan table for part.*/
    @FXML
    void SearchTextPart(ActionEvent event) {


    }
/**Search bars that allow user to scan table for product.*/
    @FXML
    void SearchTextProduct(ActionEvent event) {


    }

/**Add part button that takes user to new scene with part data loaded into table
 * RUNTIME ERROR Caused by: java.lang.NullPointerException: Location is required. Wrong fxml spelling line 141.
*Add button event that takes user to new scene.*/
    @FXML
    void partAddPushed(ActionEvent event) throws IOException {


        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/ViewControllers/AddPart.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();


    }

/**Modify button action that opens a scene where users can edit a selected part.
 * RUNTIME ERROR Was struggling with different loader viewing methods that would copy the part data selected to the next screen.
 *the commented out section switched scenes, but did not load selected part. Coding below adds part information into GUI, left in case needed later.
 *FUTURE ENHANCEMENT A screen or list should popup showing current parts for a user to compare modded part to.**/
    @FXML
    public void partModifyPushed(ActionEvent event) throws IOException {
//No part selected to modify.
        if (partTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There is no part selected to modify.");
            alert.showAndWait();
            return;
        }

        Stage stage;
        Parent root;
        stage = (Stage) partModify.getScene().getWindow();
//Load up OTHER FXML document
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewControllers/ModifyPart.fxml"));

        root = loader.load();
        ModifyPartCtrl controller = loader.getController();
        Part part = partTable.getSelectionModel().getSelectedItem();
        int index = partTable.getSelectionModel().getSelectedIndex();

        if (part != null) {
            controller.setParts(part, index);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

/***User can select from table and delete that information with confirmation.
 * RUNTIME ERROR N/A
 *FUTURE ENHANCEMENT Shows if a part is linked to a product before deletion. This was not a requirement in the UML
 *or project instructions.
 */
    @FXML
    void partDeletePushed(ActionEvent event) {

        if (partTable.getSelectionModel().isEmpty()){
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setTitle("No Selection");
            e.setContentText("There is no part selected to delete.");
            e.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete all the data for this part, are you sure?");
        alert.setTitle("Deletion");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            ObservableList<Part> allParts, singlePart;
            allParts = partTable.getItems();
            singlePart = partTable.getSelectionModel().getSelectedItems();
            singlePart.forEach(allParts::remove);

        }
    }

/**Add product button that takes user to new scene with product data loaded into table
 * RUNTIME ERROR N/A
 .*/
    @FXML
    void productAddPushed(ActionEvent event) throws IOException {

        Parent addViewProducts = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ViewControllers/AddProduct.fxml")));
        Scene addSceneProducts = new Scene(addViewProducts);

//       This line gets the stage information for the addproducts transition.

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(addSceneProducts);

    }
/**Modify button action that opens a scene where users can edit a selected product.
 * RUNTIME ERROR Modify would originally not open do to a Location Error, the incorrect fxml was provided on 233. It
 *was spelled incorrectly.
 *Learned from mistake on modify part and copied code over modifying which view table was used and which GUI fxml doc.*/
    @FXML
    void productModifyPushed(ActionEvent event) throws IOException {
//User does not select a product to modify.
        if (productTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There is no product selected to modify.");
            alert.showAndWait();
            return;
        }
        Stage stage;
        Parent root;

        int index = productTable.getSelectionModel().getSelectedIndex();
        Product product = productTable.getSelectionModel().getSelectedItem();

        setProductID(product.getId());
        setProductIndex(index);


        stage = (Stage) productModify.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewControllers/ModifyProduct.fxml"));
        root = loader.load();
        ModifyProductCtrl controller = loader.getController();

        if (product != null) {
            controller.setProduct(product, index);
        }


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

/** *User can select product from home screen and delete all product info.
 * RUNTIME ERROR Originally line 258 was not included and the app did not check if a part was associated before deleting.
 *The if if else statement ensures no associated part is connected before deleting.
 *FUTURE ENHANCEMENT Products show if associated parts are present on MainGUI.
*/
    @FXML
    public void productDeletePushed(ActionEvent event) {

        if (productTable.getSelectionModel().isEmpty()){
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setTitle("No Selection");
            e.setContentText("There is no product selected to delete.");
            e.showAndWait();
            return;
        }

        Product deletedProduct = productTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete all the data for this product, are you sure?");
        alert.setTitle("Deletion");
        Optional<ButtonType> result = alert.showAndWait();

        if((deletedProduct.getAllAssociatedParts()).size() != 0 && result.isPresent() && result.get() == ButtonType.OK){
            Alert cannot = new Alert(Alert.AlertType.CONFIRMATION, "There is a part associated with this product cannot delete.");
            cannot.setTitle("Deletion Failed");
            Optional<ButtonType> r = cannot.showAndWait();
           return;
        }
        else if (result.isPresent() && result.get() == ButtonType.OK) {

            ObservableList<Product> allProducts, singlePart;
            allProducts = productTable.getItems();
            singlePart = productTable.getSelectionModel().getSelectedItems();
            singlePart.forEach(allProducts::remove);

        }
    }

    /**Functions that assist with Modify Product by setting an integer.*/
    private static int modifyThisProduct;
    /**Functions that assist with Modify Product by setting an integer.*/
    private static int theProductID;
    /**Functions that assist with Modify Product by setting an integer for an index.*/
    public void setProductID(int id) {
        theProductID = id;
    }
    /**Functions that assist with Modify Product by returning an integer for an index.*/
    public static int getProductID() {
        return theProductID;
    }
    /**Functions that assist with Modify Product by returning an integer for an index.*/
    public static int getProductIndex() {
        return modifyThisProduct;
    }
    /**Functions that assist with Modify Product by setting an integer for an index.*/
    public void setProductIndex(int modIndex) {
        modifyThisProduct = modIndex;
    }
    /**Button exit push that ends app.*/
    @FXML
    void exitPushed(ActionEvent event) {

        System.exit(0);

    }





/**Part search is a text action that matches and highlights a part or errors if none exists.
 * RUNTIME ERROR java: method selectAll in class javafx.scene.control.MultipleSelectionModelBase<T> cannot be applied to given types; Tried partTable.getSelectionModel().selectAll();
 * but it selects entire table after search.
 *Attempted an array approach and array variables were not compatible with the single part output/search so
 *a loop was required as a function of search and needed an exit condition.
 * Allows user to search for a specific part or product by hitting enter in the search box.
 *FUTURE ENHANCEMENT The search box could auto suggests something to be filled in after a
 * portion of a word is typed in.*/

    @FXML
    void partSearch(ActionEvent event) {

        String searchString = partSearchBox.getText();

        if (searchString.isEmpty()) {
            //Search box is empty.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Empty");
            alert.setHeaderText("Empty");
            alert.setContentText("Search box is empty.");
            alert.showAndWait();
        } else {
            //Input in the search box.
            String x = partSearchBox.getText();
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



/**Product search is a text action that matches and highlights a product or errors if none exists.
 * RUNTIME ERROR Originally both part nad product searches had no way to catch if field was empty. If else statement was used in case of null to trigger an error.
 *Searches of partial uppercase or lowercase were not counted. Added line 367 touppercase to fix.
 *FUTURE ENHANCEMENT The search box could auto suggests something to be filled in after a
 *portion of a word is typed in.*/

    @FXML
    void productSearch(ActionEvent event) {

        String searchString = productSearchBox.getText();

        if (searchString.isEmpty()) {
            //Search box is empty.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Empty");
            alert.setHeaderText("Empty");
            alert.setContentText("Search box is empty.");
            alert.showAndWait();
        } else {
            //Input in the search box.
            String x = productSearchBox.getText();
            x = x.toUpperCase();
            productTable.getSelectionModel().clearSelection();
            productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            for (Product product : Inventory.getAllProducts()) {

                Product p = Inventory.searchProduct(x, product);
                productTable.getSelectionModel().select(p);


            }
            //No match in table.
            if ( productTable.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle("No Product");
                alert.setHeaderText("Could Not Find");
                alert.setContentText("Product does not exist.");
                alert.showAndWait();
                return;
            }
        }
    }








    public static void main(String[] args) {


     //   launch(args);
        return;
    }

}
