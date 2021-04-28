package Model;

/**
 * The Outsourced class is composed of the private field companyName(String) and extends the abstract Part class.
 * It creates an Outsourced Part object which can be used to represent an outsourced part in our inventory system.
 * @author Jeremy Varkey
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * Constructor for a new instance of an Outsourced object.
     *
     * @param id the ID for the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the inventory of the part
     * @param min the minimum for the part
     * @param max the maximum for the part
     * @param companyName the company name for the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Gets company name, specific to Outsourced.
     *
     * @return Returns CompanyName.
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * Sets company name, specific to Outsourced.
     *
     * @param companyName is changed.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
