package Model;

import java.util.ArrayList;
import java.util.function.Function;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.Part;
import Model.Product;

/**@author Kaitlynn Abshire

* The inventory class created indexes and methods for all parts and products. It allows lists and generic funcitons
 * needed for the document to be made.
 * RUNTIME ERROR - Originally attempted to initialize class Inventory, but later realized no initialize was
* needed in the methods used. Function getAllParts was not working due to a missing closed bracket "}" fixed.
*FUTURE ENHANCEMENT - In the future adding usernames and passwords to create unique users to see which fields
* are added by whom and timestamped would be efficient. Timestamping changes would help users identify
*when parts were changed or when errors were made.*/


    public class Inventory {
/**Retrieves entire list of products
 * RUNTIME ERROR none
*FUTURE ENHANCEMENT Creating designated part and product arrays to enable other function in the app.
* Having the arrays ready to be called on would provide efficiency in future changes.
*Used to return/call the lists.*/
        public static ObservableList<Product> allProducts = FXCollections.observableArrayList();

/**Retrieves entire list of parts */

        private static ObservableList<Part> allParts = FXCollections.observableArrayList();

/** List size can be used to help set the next part ID.
 * RUNTIME ERROR none
*FUTURE ENHANCEMENT Add a part list that pops up during the add part or modify part screen so users
*can compare what they are adding/modifying to what already exists.
*/
        public int partListSize() {
            return allParts.size();
        }


/**Retrieves current list of data and inserts new part to list.*/
        public static void addPart(Part part) {
            allParts.add(part);
        }
/**Retrieves current list of data and inserts new product to list.*/
        public static void addProduct(Product product) {
            allProducts.add(product);
        }

/**Function to look up a specific part in the list of all parts.
 * RUNTIME ERROR Would not look up parts that had various case sizes. Added ignore case to bypass this on line 46.
*/
        public static Part lookupPartName(String searchPartName) {
            for (Part part : allParts) {
                if (part.getName().equalsIgnoreCase(searchPartName)) {
                    return part;
                }
            }
            return null;
        }

/**Function to lookup a specific part by ID in the list of all parts.*/
        public static Part lookupPartId(int searchPartId) {
            for (Part part : allParts) {
                if (part.getId() == (searchPartId)) {
                    return part;
                }
            }
            return null;
        }

/**Function to lookup product ID from a list of all products.
*FUTURE ENHANCEMENT Calling this function in the controllers instead of creating new variables within the controllers
*for the same purpose to reduce redundancy.*/
        public static Product lookupProduct(int id) {
            return null;
        }

/**Function used to look up a product by name in the list of products.
*FUTURE ENHANCEMENT Create a catch that does not allow integers in the lookup tool if that is requested/ marked
* as a project requirement. In this instance it was not.*/
        public static ObservableList<Product> lookupProduct(String productName) {
            return null;
        }

/**Function to give a part an assigned index which helps log it making it available to associate parts to and
* to push to other scences.*/
        public static void updatePart(int index, Part newPart) {

        }

/**Function used to update a product with the changes made to it in text fields.
*FUTURE ENHANCEMENTS use variable productTextFieldValues to link updateProduct to other controllers. Instead a
*variable used in conjunction with a super (int, name etc) was used to assign new values to products that were modified.*/
        public static void updateProduct(Product selectedProduct, Product productTextFieldValues) {

        }

/**A boolean used to deleteParts if the statement returns true.*/
        public static boolean deletePart(Part associatedPart) {

            return true;
        }

/**A boolean used to delete products if the statement finds no ID for it.*/
        public static boolean deleteProduct(int productID) {
            return false;
        }

/** The static method to retrieve the list of all parts in the data bank.
*FUTURE ENHANCEMENT A method used to ensure parts or products that are the exact
* same are not entered twice into the database.*/
        public static ObservableList<Part> getAllParts() {
            return allParts;
        }
/** The static method to retrieve the list of all products in the data bank.*/
        public static ObservableList<Product> getAllProducts() { return allProducts; }


/**Method to find a specific part in the data list.
 * RUNTIME ERROR: Originally used for loop to attempt to search data, but only
*pulled up the first matching item. Switched code on line 123 to pull up all returned matching
* parts in the table searched. Originally was in Part controller, but moved to more appropriate spot.
*/
        public static Part searchPart(String lookName, Part p) {
            String tempName = p.getName();
            tempName = tempName.toUpperCase();

            if (tempName.contains(lookName) || Integer.toString(p.getId()).equals(lookName)) return p;
            return null;
        }
/**Function used to pull up specific parts by name or id.
 * RUNTIME ERROR: Originally used for loop to attempt to search data, but only
*pulled up the first matching item. Switched code on line 133 to pull up all returned matching
*products in the table searched. Originally was in Product controller, but moved to more appropriate spot.*/
        public static Product searchProduct(String lookName, Product p) {
            String tempName = p.getName();
            tempName = tempName.toUpperCase();

            if (tempName.contains(lookName) || Integer.toString(p.getId()).equals(lookName)) return p;
            return null;
        }
    }




