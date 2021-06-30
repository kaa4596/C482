package AbshireC482;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**@author Kaitlynn Abshire
*JavaDoc Files located in AbshireC482Software1Project/JavaDoc. JavaDoc Folder.

*This class is the main intialization of the entire java app. It sets the main gui fxml and calls it to scene.
* RUNTIME ERROR - Line 22 Caused by: java.lang.NullPointerException: Location is required. Loader error had spelled fxml document name incorrectly. Fixed by adding "_".
*FUTURE Enhancement - In some coding instances I use Stage primary stage in others I used Stage stage,
*consistency could benefit in future coding and look cleaner.

 */

public class Abshirec482 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ViewControllers/Main_GUI.fxml"));
        primaryStage.setTitle("GUI Mock-Up");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


/** This class is used to populate the parts and products into the main gui screen. It is required with or without data inserted.
 * RUNTIME ERROR - java: incompatible types: java.lang.String cannot be converted to int. Accidentally
 *had data in wrong order in entry versus in data storage. Fixed by rearranging variables.
 *FUTURE Enhancement - A way to screen who can enter data versus not using passwords and usernames should be implemented so not everyone can alter data.
 *This provides consistent reliable data.
 *RUNTIME ERROR Had data in inappropriate order so it could not be displayed in the table due to a NumberFormat Exception.
 *FUTURE ENHANCEMENT Tried various methods of adding data to the table, however this method seemed to be the best option
 *after searching many different coding options this also appeared the cleanest option with less clutter. Possibly
 *a cleaner format available.

 */
    public static void main(String[] args) {


        Part tire = new InHouse(1, "Tire", 50.00, 10, 1, 27, 1);
        Inventory.addPart(tire);

        Part steeringwheel = new InHouse(2, "Wheel", 15.00, 20, 10, 30, 4);
        Inventory.addPart(steeringwheel);

        Part brakes = new InHouse(3, "Wheel", 40.00, 11, 0, 120, 5);
        Inventory.addPart(brakes);

        Product Truck = new Product(1, "Truck", 50000.0, 10, 1, 50);
        Inventory.addProduct(Truck);

        Product Car = new Product(2, "Car", 10000.0, 20, 1, 50);
        Inventory.addProduct(Car);





        launch(args);
    }

}