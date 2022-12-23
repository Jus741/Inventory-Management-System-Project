package Controller;

import Main.*;
import javafx.collections.FXCollections;
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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class ModifyProduct implements Initializable
{
    public static Product selectedProduct;
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public TextField productID;
    public int productGenID;
    public TextField productName;
    public String inputName;
    public TextField productStock;
    public int inputStock;
    public TextField productPrice;
    public double inputPrice;
    public TextField productMax;
    public int inputMax;
    public TextField productMin;
    public int inputMin;
    public TableView allTable;
    public TableColumn allID;
    public TableColumn allName;
    public TableColumn allStock;
    public TableColumn allPrice;
    public TableView productTable;
    public TableColumn productsID;
    public TableColumn productsName;
    public TableColumn productsStock;
    public TableColumn productsPrice;
    public TextField partSearchQuery;
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    /**
     * Sets tables using master part list and the selected product's given data
     * Also fills TextFields with the data from the product that was set in the call
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        for(int i = 0; i < selectedProduct.getAllAssociatedParts().size(); i++)
        {
            associatedParts.add(null);
        }
        FXCollections.copy(associatedParts,selectedProduct.getAllAssociatedParts());

        allTable.setItems(Inventory.getAllParts());
        allID.setCellValueFactory(new PropertyValueFactory<>("id"));
        allName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        allPrice.setCellFactory(tableColumn -> new TableCell<Part, Double>()
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

        productTable.setItems(associatedParts);
        productsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsPrice.setCellFactory(tableColumn -> new TableCell<Part, Double>()
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

        productID.setText(Integer.toString(selectedProduct.getId()));
        productName.setText(selectedProduct.getName());
        productStock.setText(Integer.toString(selectedProduct.getStock()));
        productPrice.setText(Double.toString(selectedProduct.getPrice()));
        productMax.setText(Integer.toString(selectedProduct.getMax()));
        productMin.setText(Integer.toString(selectedProduct.getMin()));
    }

    /**
     * Checks for input errors and shows dialog boxes accordingly
     * Otherwise constructs a new product with the taken data and updates the old one
     * Then loads the main menu
     * @param actionEvent
     * @throws IOException
     */
    public void saveButton(ActionEvent actionEvent) throws IOException
    {
        boolean valid = false;
        while(!valid)
        {
            if(productName.getText().equals("")||productPrice.getText().equals("")||productStock.getText().equals("")||productMin.getText().equals("")||productMax.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"Fields are required");
                break;
            }
            else
            {
                inputName = productName.getText();
                try
                {
                    inputPrice = Double.parseDouble(productPrice.getText());
                }
                catch (NumberFormatException i)
                {
                    JOptionPane.showMessageDialog(null, "Check price for type mismatch");
                    break;
                }
                try
                {
                    inputStock = Integer.parseInt(productStock.getText());
                }
                catch (NumberFormatException i)
                {
                    JOptionPane.showMessageDialog(null, "Check inv for type mismatch");
                    break;
                }
                try
                {
                    inputMin = Integer.parseInt(productMin.getText());
                }
                catch (NumberFormatException i)
                {
                    JOptionPane.showMessageDialog(null, "Check min for type mismatch");
                    break;
                }
                try
                {
                    inputMax = Integer.parseInt(productMax.getText());
                }
                catch (NumberFormatException i)
                {
                    JOptionPane.showMessageDialog(null, "Check max for type mismatch");
                    break;
                }
            }
            if(inputMin > inputMax)
            {
                JOptionPane.showMessageDialog(null,"Minimum cannot be higher than maximum");
                break;
            }
            else if(inputStock > inputMax || inputStock < inputMin)
            {
                JOptionPane.showMessageDialog(null,"Stock exceeds capacity boundaries");
                break;
            }
            else
            {
                productGenID = selectedProduct.getId();
                valid = true;
                Product input = new Product(productGenID,inputName,inputPrice,inputStock,inputMin,inputMax);
                for(int i = 0; i < associatedParts.size(); i++)
                {
                    input.addAssociatedPart(associatedParts.get(i));
                }
                for(int i = 0; i < Inventory.getAllProducts().size(); i++)
                {
                    if(selectedProduct.getId() == Inventory.getAllProducts().get(i).getId())
                    {
                        Inventory.updateProduct(i, input);
                    }
                }

                Parent root = FXMLLoader.load(getClass().getResource("/FXML/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    /**
     * Forgoes all data fields and loads the main menu
     * @param actionEvent
     * @throws IOException
     */
    public void cancelButton(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/MainMenu.fxml"));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");

        stage.setScene(scene);

        stage.show();
    }

    /**
     * Removes given part from this menu's associatedParts list
     * @param actionEvent
     */
    public void removePartButton(ActionEvent actionEvent)
    {
        Part selected = (Part)productTable.getSelectionModel().getSelectedItem();
        if(selected == null)
            return;
        associatedParts.remove(selected);
    }

    /**
     * Adds given part to this menu's associatedParts list
     * @param actionEvent
     */
    public void addPartButton(ActionEvent actionEvent)
    {
        Part selected = (Part)allTable.getSelectionModel().getSelectedItem();
        if(selected == null)
            return;
        associatedParts.add(selected);
    }

    /**
     * Searches part list using either name or ID
     * @param actionEvent
     */
    public void getProductResults(ActionEvent actionEvent)
    {
        String q = partSearchQuery.getText();
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
                    allTable.setItems(results);
                    partSearchQuery.setText("");
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

        allTable.setItems(results);
        partSearchQuery.setText("");
    }
}
