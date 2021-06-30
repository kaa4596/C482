package Model;



/**
 * @author Kaitlynn Abshire
 *
 * The InHouse class inventories the constructors getters and setters for inhouse parts. It sets a private variable for only inhouse parts.
 * RUNTIME ERROR 'Part(int, java.lang.String, double, int, int, int)' in 'Model.Part' cannot be applied to '(int, java.lang.String, double, int, int, int, int)'
 * Fixed by correcting the super and the part controller to match the same order and amount of data inputs.
 * Had to remove partMach from super, did not realize it was not included in super.
 * FUTURE ENHANCEMENT Add a Machine ID column to the table so Users can see which part is with which machine
 *  or a new GUI interface to see what machines are connected to which parts.
 * This method provides a machineid variable input for when parts are selected as inhouse.
 */
public class InHouse extends Part {
    private int machineID;


/**The method sets the string of data needed for inhouse parts. It applies the machineID to the data of a normal part.
 * RUNTIME ERROR Line 16 super was listed in incorrect order.
 *  FUTURE ENHANCEMENT Use different values for inhouse vs outhouse to help differentiate parts.
 *  Also, adding an inhouse vs outhouse GUI in the future for use by needed parties.
 * The string format for InHouse parts which dictates how data can be entered into the inHouse part.
 */

    public InHouse(int id, String name, double price, int inStock, int min, int max, int machineID) {
        super(id, name, price, inStock, min, max);
        this.machineID = machineID;
    }

/**Returns a machineId for a specific part.*/
    public int getMachineID() {
        return machineID;
    }

/**Sets a new machineId for a specific part.
 * FUTURE ENHANCEMENT If the future table includes a machineID field for needed purposes then the
* machine id setter would be used.
*/
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }



}
