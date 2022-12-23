package Main;

public class Outsourced extends Part
{
    /** Name of the company that sourced this type of part */
    private String companyName;

    /**
     * This constructor will call the parent Part constructor first to set inherited attributes and then set the attribute specific to this class
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the param companyName to attribute companyName
     * @param companyName
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
     * @return the name of the company
     */
    public String getCompanyName()
    {
        return this.companyName;
    }
}
