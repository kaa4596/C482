package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/** @author Kaitlynn Abshire
*Class product is used to create all product variables and setup getters as well as setters to call funcitons to products.
 * */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

/***Constructor for products.
 * RUNTIME ERROR N/A
*/

    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


/**Retrieves a list of all associated parts.
* RUNTIME ERROR Incompatible types. Found: 'java.util.ArrayList<Model.Part>',
* required: 'javafx.collections.ObservableList<Model.Part>'; Had originally
* tried to set getAllAssoc as an array but it did not match UML and did not work in
* code being used.*/

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

/**Adds an associated part.*/
    public void addAssociatedParts(Part part) {
        associatedParts.add(part);
    }
/**Creates an array of the associated parts to be called into functions*/
    public void arrayAssociatedParts(ArrayList<Part> assocparts) {
        for (Part parts : assocparts) {
            associatedParts.add(parts);
        }
    }
/** Boolean used to ensure that associated part meets requirements determined by a boolean before being deleted.*/
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

/**Retrieves product id.
 * RUNTIME ERROR Getters and Setters were not capitalized properly on line 67 and 71. getid to getId.
*FUTURE ENHANCEMENT Create variable that differ from parts so when calling getID etc it is more
*clear if it is a part or product.*/

    public int getId() {
        return id;
    }

/**Sets new product id.*/
    public void setId(int id) {
        this.id = id;
    }
    /**Retrieves product name*/
    public String getName() {
        return name;
    }
    /**Sets new product name.*/
    public void setName(String name) {
        this.name = name;
    }
    /**Retrieves product price.*/
    public double getPrice() {
        return price;
    }
    /**Sets new product price.*/
    public void setPrice(double price) {
        this.price = price;
    }
    /**Retrieves product inventory.*/
    public int getStock() {
        return stock;
    }
    /**Sets a new product inventory.*/
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**Retrieves product min.*/
    public int getMin() {
        return min;
    }
    /**Sets a new product min.*/
    public void setMin(int min) {
        this.min = min;
    }
    /**Retrieves product max*/
    public int getMax() {
        return max;
    }
    /**Sets a new product max.*/
    public void setMax(int max) {
        this.max = max;
    }
}




