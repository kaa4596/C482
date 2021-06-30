
package ViewControllers;

import Model.*;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import static ViewControllers.MainGUI.*;

/**@author Kaitlynn Abshire

 *Class modify product controler populates the modify product GUI, transfers data from main gui, allows associating of parts, and saves all changes to products.
 * RUNTIME ERROR - Same as encountered with parts. Lots of accidental mislabeling by capitalizing value names
 *inappropriately. Line 128 was using setAllParts instead of Associated Parts. Fixed.
 *Caused by: java.lang.NumberFormatException: empty String Line 202.
 *FUTURE ENHANCEMENT GUI can be made cleaner and code can be cleaned up more to flow better. More detailed enhacements below.*/

public class ModifyProductCtrl implements Initializable {


    Product newProduct;
    Product selectedProduct;
    int idx;

    /**Text that retrieves product id.*/
    @FXML
    private TextField ProductIdText;
    /**Text that retrieves product name.*/
    @FXML
    private TextField ProductNameText;
    /**Text that retrieves product inventory.*/
    @FXML
    private TextField ProdInventoryText;
    /**Text that retrieves product price.*/
    @FXML
    private TextField ProductPriceText;
    /**Text that retrieves product max.*/
    @FXML
    private TextField ProdMaxText;
    /**Text that retrieves product min.*/
    @FXML
    private TextField ProdMinTxt;
    /**Text that retrieves product search bar for both tables.*/
    @FXML
    private TextField SearchModProd;
    /**Tableview of all parts in index.*/
    @FXML
    private TableView<Part> partsTable;
    /**Table column that contains part id.*/
    @FXML
    private TableColumn<Part, Integer> AddPartID;
    /**Table column that contains part name.*/
    @FXML
    private TableColumn<Part, String> AddPartName;
    /**Table column that contains part inventory.*/
    @FXML
    private TableColumn<Part, Integer> AddInventoryLevel;
    /**Table column that contains part price.*/
    @FXML
    private TableColumn<Part, Double> AddPriceperUnit;
    /**Button action to add a part to associated*/
    @FXML
    private Button AddPartButton;
    /**Tableview of all assocaited parts in index.*/
    @FXML
    private TableView<Part> assocpartsTable;
    /**Table column that contains associated part id.*/
    @FXML
    private TableColumn<Part, Integer> assocPartID;
    /**Table column that contains associated part name.*/
    @FXML
    private TableColumn<Part, String> assocPartName;
    /**Table column that contains associated part inventory.*/
    @FXML
    private TableColumn<Part, Integer> assocInventoryLevel;
    /**Table column that contains associated part price.*/
    @FXML
    private TableColumn<Part, Double> assocPriceperUnit;
    /**Button that uses an action to remove associated parts.*/
    @FXML
    private Button removeAssocPart;
    /**Button that uses an action to save modifications made.*/
    @FXML
    private Button SaveProductMod;
    /**Button that uses an action to cancel all input data.*/
    @FXML
    private Button cancelProdMod;
    /**Button that uses an action to search both tables of data.*/
    @FXML
    private Button SearchModProdButton;

/**Initialize data from the main gui by using the index to insert product and part data.
 * RUNTIME ERROR Caused by: java.lang.NullPointerException Cannot just get associated part to display in bottom table because it can be empty.
 *Associated part was lagging behind clicks of modify so data would show for previous product
 *117 line moved up to help index the product correctly before information is loaded into the table.
 *FUTURE ENHANCEMENT Current list of products displayed somewhere to compare modifying product to
 *so users do not have to try to remember what was on the main GUI.*/

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        idx = getProductIndex();


        updatePartTable();

        AddPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddPriceperUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        newProduct = Inventory.getAllProducts().get(idx);

//Was used to find out why inappropriate indexing was occuring: System.out.println("modify"+ idx);

        assocpartsTable.setItems(newProduct.getAllAssociatedParts());

        assocPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceperUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            assocpartsTable.setItems(newProduct.getAllAssociatedParts());
            updatePartTable();


        } catch (NullPointerException e){
            assocpartsTable.setItems(newProduct.getAllAssociatedParts());

        }
    }

/**Button saves new product to the lower portion table to show it is linked to the product.
 * RUNTIME ERROR N/A was smoothly copied over from addproduct GUI.
 *FUTURE ENHANCEMENT Removes added part from top table so user cannot readd same part over and over.
 */
    @FXML
    void AddPart(ActionEvent event) {
        if (partsTable.getSelectionModel().isEmpty()){
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setTitle("No Selection");
            e.setContentText("There is no part selected to associate.");
            e.showAndWait();
            return;
        }
        Part singlePart = partsTable.getSelectionModel().getSelectedItem();
        newProduct.addAssociatedParts(singlePart);
    }


/**
 * *Button cancels all input information and returns to homescreen.*
 *RUNTIME ERROR Origianlly would cancel but stay on product screen. Code 197-200 added to return to home screen.
 */
    @FXML
    void CancelButton(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text, are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/ViewControllers/Main_GUI.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

/**Button action deletes associated part.*
 * RUNTIME ERROR N/A
 *FUTURE ENHANCEMENT Confirms deletion was successful and shows which was deleted.d
*/
    @FXML
    void DeletePart(ActionEvent event) {
        if (assocpartsTable.getSelectionModel().isEmpty()){
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setTitle("No Selection");
            e.setContentText("There is no associated part selected to delete.");
            e.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the associated Part, are you sure?");
        alert.setTitle("Confirmation of Deletion");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            newProduct.getAllAssociatedParts().remove(assocpartsTable.getSelectionModel().getSelectedItem());
        }

    }

/** This button saves the changes made to the product in the modification form.
 * RUNTIME ERROR Product ID would save on all products as 2. Recreated a getproductid statement that saved the correctly
 *indexed productid to a modified product and did not autoloop through the table grabbing only the last entered
 *product id number to apply to all products.

 *FUTURE ENHANCEMENT Coding could be done in a switch case instead to be more concise and less bulky in the future.*/
    @FXML
    void SaveProduct(ActionEvent event) throws IOException {

        int ID = getProductID();

        for (Product product : Inventory.getAllProducts())

            if (product.getId() == ID)

                ID = product.getId();


        try {
            String name = ProductNameText.getText();
            int stock = Integer.parseInt(ProdInventoryText.getText());
            double price = Double.parseDouble(ProductPriceText.getText());
            int max = Integer.parseInt(ProdMaxText.getText());
            int min = Integer.parseInt(ProdMinTxt.getText());


            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max cannot be lower than min.");
                alert.showAndWait();
                return;
            } else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within min and max.");
                alert.showAndWait();
                return;
            }

            Product modproducts = new Product(ID, name, price, stock, min, max);



            ArrayList<Part> assocparts = new ArrayList<>();
            assocparts.addAll(assocpartsTable.getItems());
            modproducts.arrayAssociatedParts(assocparts);
            Inventory.getAllProducts().set(idx, modproducts);


//Used system print out in attempt to find out why indexing was not working properly.
//System.out.println("NullPOinter" + Inventory.getAllProducts().get(selectedIndex).getAssociatedParts());


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
/** Allows user to search in table and highlights findings using text field.
 * RUNTIME ERROR Caused by: java.util.ConcurrentModificationException
 *Caused by: java.lang.ClassCastException: class java.util.ArrayList cannot be cast to class Model.Part
 *(java.util.ArrayList is in module java.base of loader 'bootstrap'; Model.Part is in unnamed module of loader 'app')
 *Decided not to go with array at all.
 *Search was only highlighting first item in search bar was due to setting parts as an array list on the main Part controller.
 *Fixed by returning it to a model without array.

 *FUTURE ENHANCEMENT Searchbar could suggest words based on typed in letters or numbers.*/
    @FXML
    void searchProduct(ActionEvent event) {

        String searchString = SearchModProd.getText();

        if (searchString.isEmpty()) {
            //Search box is empty.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Empty");
            alert.setHeaderText("Empty");
            alert.setContentText("Search box is empty.");
            alert.showAndWait();
            return;
        }
        else {
            //Input in the search box.
            String x = SearchModProd.getText();
            x = x.toUpperCase();
            partsTable.getSelectionModel().clearSelection();
            partsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            for (Part part : Inventory.getAllParts()) {

                Part p = Inventory.searchPart(x, part);
                partsTable.getSelectionModel().select(p);


            }
            //No match in table.
            if (partsTable.getSelectionModel().isEmpty()) {
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



/**Function to set the index of a product as an integer.
 * */
    public void setIndex(int index) {
        idx = index;
    }

    /**Function to set a new product and its new data or modified data to the index.
     * */
    public void setProduct(Product product, int index) {
        selectedProduct = product;
        idx = index;

        if (product instanceof Product) {
            Product newProduct = (Product) product;

            this.ProductNameText.setText(newProduct.getName());
            this.ProdInventoryText.setText((Integer.toString(newProduct.getStock())));
            this.ProductPriceText.setText((Double.toString(newProduct.getPrice())));
            this.ProdMinTxt.setText((Integer.toString(newProduct.getMin())));
            this.ProdMaxText.setText((Integer.toString(newProduct.getMax())));

        }
    }
    /**Function to update the changes into the inventory and index.
     * */
    public void updatePartTable() {
        partsTable.setItems(Inventory.getAllParts());

    }

}

