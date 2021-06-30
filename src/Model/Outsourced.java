package Model;
/**@author Kaitlynn Abshire

*This method provides a companyName variable input for when parts are selected as outsourced.
 * RUNTIME ERROR = 'Part(int, java.lang.String, double, int, int, int)' in 'Model.Part' cannot be applied
* to '(int, java.lang.String, double, int, int, int, java.lang.String)'
*Fixed by correcting the super and the part controller to match the same order and amount of data inputs.
*Same error as on InHouse had to remove companyName from super.
*/
public class Outsourced extends Part {
    private String companyName;

/**Dictates how the string is formed for outsourced products and future data must be entered in this order.*/
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

/**Returns the companyName for a specific part.
 * RUNTIME ERROR N/A
*FUTURE ENHANCEMENT A company name column or separate GUI with company name vs mach ID would
*help users access those pieces of information as well. They currently are only indexed and
*are not displayed.
*/
    public String getCompanyName() {
        return companyName;
    }

/**Sets a new companyName for a specific part.
*FUTURE ENHANCEMENT Would be used to display the company name in a tableview column in future GUI's.*/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}