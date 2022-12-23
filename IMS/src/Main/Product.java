package Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product
{
    /** ObservableList that will store all part objects associated to a product. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /** id serves as a unique identifier for products */
    private int id;
    /** The name of the product. */
    private String name;
    /** The price of the product. */
    private double price;
    /** How much of this product is in stock. */
    private int stock;
    /** The minimum stock of this product. */
    private int min;
    /** The maximum stock of this product. */
    private int max;

    /**
     * Constructor for Product class, sets params to attributes.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets param id to attribute id.
     * @param id
     */
    public void setId(int id)
    {
        this.id  = id;
    }

    /**
     * Sets param name to attribute name.
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Sets param price to attribute price
     * @param price
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * Sets param stock to attribute stock
     * @param stock
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    /**
     * Sets param min to attribute min
     * @param min
     */
    public void setMin(int min)
    {
        this.min = min;
    }

    /**
     * Sets param max to attribute max
     * @param max
     */
    public void setMax(int max)
    {
        this.max = max;
    }

    /**
     * @return the id of the product
     */
    public int getId()
    {
        return id;
    }

    /**
     * @return the name of the product
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the price of the product
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * @return the stock of the product
     */
    public int getStock()
    {
        return stock;
    }

    /**
     * @return the minimum capacity of the product
     */
    public int getMin()
    {
        return min;
    }

    /**
     * @return the maximum capacity of the product
     */
    public int getMax()
    {
        return max;
    }

    /**
     * Appends a new part to the end of associatedParts
     * @param part
     */
    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
    }

    /**
     * Deletes given instance of part from associatedParts
     * @param selectedAssociatedPart
     * @return whether the part was deleted or not
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        boolean deleted = associatedParts.remove(selectedAssociatedPart);
        return deleted;
    }

    /**
     * @return the whole of associatedParts as an ObservableList of parts
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }
}
