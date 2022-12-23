package Main;

public class InHouse extends Part
{
    /** id of the machine used to make the part */
    private int machineId;

    /**
     * This constructor will call the parent Part constructor first to set inherited attributes and then set the attribute specific to this class
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets the param machineId to the attribute machineId
     * @param machineId
     */
    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }

    /**
     * @return the id of the machine
     */
    public int getMachineId()
    {
        return this.machineId;
    }
}
