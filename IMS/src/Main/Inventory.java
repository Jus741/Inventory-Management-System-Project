package Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory
{
    /** The master list used to store and display all parts */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /** The base increment value to assign unique ids for parts */
    private static int partID = 4;
    /** The master list used to store and display all products */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /** The base increment value to assign unique ids for parts */
    private static int productID = 1001;

    /**
     * Increments partID by one to ensure no two partIDs will be the same
     * @return a new unique partID
     */
    public static int generatePartID()
    {
        partID++;
        return partID;
    }

    /**
     * Increments productID by one to ensure no two productIDs will be the same
     * @return a new unique productID
     */
    public static int generateProductID()
    {
        productID++;
        return productID;
    }

    /**
     * Appends a new given part to the master list
     * @param newPart
     */
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }

    /**
     * Appends a new given product to the master list
     * @param newProduct
     */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /**
     * Iterates through master list and calls getID() for each part to check for matching ID
     * @param partId
     * @return the matching part or null if no match is found
     */
    public static Part lookupPart(int partId)
    {
        Part temp = null;
        for(int i = 0; i < allParts.size(); i++)
        {
            if(allParts.get(i).getId() == partId)
            {
                temp = allParts.get(i);
            }
        }
        return temp;
    }

    /**
     * Iterates through master list and calls getID() for each product to check for matching ID
     * @param productId
     * @return the matching products or null if no match is found
     */
    public static Product lookupProduct(int productId)
    {
        Product temp = null;
        for(int i = 0; i < allProducts.size(); i++)
        {
            if(allProducts.get(i).getId() == productId)
            {
                temp = allProducts.get(i);
            }
        }
        return temp;
    }

    /**
     * Iterates through master list and calls getName().contains() for each part to check for partial match, adding any to a secondary list
     * @param name
     * @return an ObservableList of parts containing all partial matches of the given param
     */
    public static ObservableList<Part> lookupPart(String name)
    {
        ObservableList<Part> temp = FXCollections.observableArrayList();
        for(int i = 0; i < allParts.size(); i++)
        {
            if(allParts.get(i).getName().contains(name))
            {
                temp.add(allParts.get(i));
            }
        }
        return temp;
    }

    /**
     * Iterates through master list and calls getName().contains() for each product to check for partial match, adding any to a secondary list
     * @param name
     * @return an ObservableList of products containing all partial matches of the given param
     */
    public static ObservableList<Product> lookupProduct(String name)
    {
        ObservableList<Product> temp = FXCollections.observableArrayList();
        for(int i = 0; i < allProducts.size(); i++)
        {
            if(allProducts.get(i).getName().contains(name))
            {
                temp.add(allProducts.get(i));
            }
        }
        return temp;
    }

    /**
     * Replaces old part at index with new part containing updated information
     * @param index
     * @param newPart
     */
    public static void updatePart(int index, Part newPart)
    {
        allParts.set(index, newPart);
    }

    /**
     * Replaces old product at index with new product containing updated information
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes a matching part from the master list
     * @param selectedPart
     * @return whether the part was deleted or not
     */
    public static boolean deletePart(Part selectedPart)
    {
        boolean deleted = allParts.remove(selectedPart);
        return deleted;
    }

    /**
     * Deletes a matching product from the master list
     * @param selectedProduct
     * @return whether the product was deleted or not
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        boolean deleted = allProducts.remove(selectedProduct);
        return deleted;
    }

    /**
     * Gets master part list
     * @return the master list of parts
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**
     * Gets master product list
     * @return the master list of products
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
}
