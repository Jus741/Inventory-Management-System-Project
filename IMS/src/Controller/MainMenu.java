package Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import Main.*;

import javax.swing.*;

public class MainMenu implements Initializable
{
    public TableView partTable;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryLevelColumn;
    public TableColumn partPricePer;
    public TextField partQuery;

    public TableView productTable;
    public TableColumn productIDColumn;
    public TableColumn productNameColumn;
    public TableColumn productInventoryLevelColumn;
    public TableColumn productPricePer;
    public TextField productQuery;


    private static boolean first = true;
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    /**
     * Adds test data for both master lists
     */
    private void addTestData()
    {
        if(!first)
        {
            return;
        }
        first = false;

        InHouse axel = new InHouse(1, "Axel", 4.50,4,1,10,85);
        Inventory.addPart(axel);
        Outsourced wheel = new Outsourced(2,"Wheel", 5.75, 8,1,10,"testCompany");
        Inventory.addPart(wheel);
        InHouse gear = new InHouse(3, "Gear", 2.25,9,1,10,85);
        Inventory.addPart(gear);
        Outsourced frame = new Outsourced(4, "Frame", 15.00,1,1,10,"bestCompany");
        Inventory.addPart(frame);

        Product car = new Product(1001,"Car",124.99,1,1,10);
        Inventory.addProduct(car);
    }

    /**
     * Calls addTestData() and sets master lists
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        addTestData();

        partTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePer.setCellValueFactory(new PropertyValueFactory<>("price"));
        partPricePer.setCellFactory(tableColumn -> new TableCell<Part, Double>()
        {
            @Override
            protected void updateItem(Double price, boolean empty)
            {
                super.updateItem(price, empty);
                if(empty)
                {
                    setText(null);
                }
                else
                {
                    setText(currencyFormat.format(price));
                }
            }
        });

        productTable.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPricePer.setCellValueFactory(new PropertyValueFactory<>("price"));
        productPricePer.setCellFactory(tableColumn -> new TableCell<Product, Double>()
        {
            @Override
            protected void updateItem(Double price, boolean empty)
            {
                super.updateItem(price, empty);
                if(empty)
                {
                    setText(null);
                }
                else
                {
                    setText(currencyFormat.format(price));
                }
            }
        });
    }

    /**
     * Exits program
     * @param actionEvent
     */
    public void exitButton(ActionEvent actionEvent)
    {
        System.exit(0);
    }

    /**
     * Loads AddPart
     * @param actionEvent
     * @throws IOException
     */
    public void partAddButton(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/AddPart.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setTitle("Add Part");

        stage.setScene(scene);

        stage.show();
    }

    /**
     * Loads ModifyPart
     * @param actionEvent
     * @throws IOException
     */
    public void partModifyButton(ActionEvent actionEvent) throws IOException
    {
        ModifyPart.selectedPart = (Part)partTable.getSelectionModel().getSelectedItem();
        if(!(ModifyPart.selectedPart == null))
        {
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/ModifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Searches part list using either name or ID
     * @param actionEvent
     */
    public void getPartResults(ActionEvent actionEvent)
    {
        String q = partQuery.getText();
        int id;

        ObservableList<Part> results = Inventory.lookupPart(q);
        if(results.size() == 0)
        {
            try
            {
                id = Integer.parseInt(q);
                Part temp = Inventory.lookupPart(id);
                if (!(q.equals("")) && temp == null)
                {
                    JOptionPane.showMessageDialog(null, "No part found with that ID");
                    partTable.setItems(results);
                    partQuery.setText("");
                    return;
                }
                else
                {
                    results.add(temp);
                }
            }
            catch(NumberFormatException e)
            {

            }
        }

        if(q.equals(""))
        {
            results = Inventory.getAllParts();
        }
        else if (!(q.equals("")) && results.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "No part found with that Name");
        }

        partTable.setItems(results);
        partQuery.setText("");
    }

    /**
     * Show confirm dialog
     * If yes then delete matching part
     * @param actionEvent
     */
    public void partDeleteButton(ActionEvent actionEvent)
    {
        Part selected = (Part)partTable.getSelectionModel().getSelectedItem();
        if(selected == null)
            return;
        int input = JOptionPane.showConfirmDialog(null, "Delete selected part?","Delete Part",JOptionPane.YES_NO_OPTION);
        if(input == 0)
            Inventory.deletePart(selected);
        else
            return;
    }

    //PRODUCT TABLE FUNCTIONS

    /**
     * Loads AddProduct
     * @param actionEvent
     * @throws IOException
     */
    public void productAddButton(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/AddProduct.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setTitle("Add Product");

        stage.setScene(scene);

        stage.show();
    }

    /**
     * Loads ModifyProduct
     * @param actionEvent
     * @throws IOException
     */
    public void productModifyButton(ActionEvent actionEvent) throws IOException
    {
        ModifyProduct.selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();
        if(!(ModifyProduct.selectedProduct == null))
        {
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Searches product list using either name or ID
     * @param actionEvent
     */
    public void getProductResults(ActionEvent actionEvent)
    {
        String q = productQuery.getText();
        int id;

        ObservableList<Product> results = Inventory.lookupProduct(q);
        if(results.size() == 0)
        {
            try
            {
                id = Integer.parseInt(q);
                Product temp = Inventory.lookupProduct(id);
                if (!(q.equals("")) && temp == null)
                {
                    JOptionPane.showMessageDialog(null, "No product found with that ID");
                    productTable.setItems(results);
                    productQuery.setText("");
                    return;
                }
                else
                {
                    results.add(temp);
                }
            }
            catch(NumberFormatException e)
            {

            }
        }

        if(q.equals(""))
        {
            results = Inventory.getAllProducts();
        }
        else if (!(q.equals("")) && results.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "No product found with that Name");
        }

        productTable.setItems(results);
        productQuery.setText("");
    }

    /**
     * Check if the selected product has any associated parts
     * If there are then show cannot delete message dialog
     * Otherwise show delete confirm dialog
     * If yes then delete the selected product
     * @param actionEvent
     */
    public void productDeleteButton(ActionEvent actionEvent)
    {
        Product selected = (Product)productTable.getSelectionModel().getSelectedItem();
        if(selected == null)
            return;
        else if (!(selected.getAllAssociatedParts().isEmpty()))
        {
            JOptionPane.showMessageDialog(null, "Cannot delete product because it still has parts associated");
            return;
        }
        int input = JOptionPane.showConfirmDialog(null, "Delete selected product?","Delete Product",JOptionPane.YES_NO_OPTION);
        if(input == 0)
            Inventory.deleteProduct(selected);
        else
            return;
    }


}
