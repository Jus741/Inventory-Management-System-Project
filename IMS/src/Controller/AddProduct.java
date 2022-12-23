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

public class AddProduct implements Initializable
{
    public ObservableList<Part> productPartList = FXCollections.observableArrayList();
    public int productID;
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
     * Sets up one table with master part list and the other with an empty list
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
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

        productTable.setItems(productPartList);
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
    }

    /**
     * Takes in form data to create a new product and adds it to the master list
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
                productID = Inventory.generateProductID();
                valid = true;
                Product input = new Product(productID,inputName,inputPrice,inputStock,inputMin,inputMax);

                for(int i = 0; i < productPartList.size(); i++)
                {
                    input.addAssociatedPart(productPartList.get(i));
                }
                Inventory.addProduct(input);

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
     * Removes given part from this menu's productPartList
     * @param actionEvent
     */
    public void removePartButton(ActionEvent actionEvent)
    {
        Part selected = (Part)productTable.getSelectionModel().getSelectedItem();
        if(selected == null)
            return;
        productPartList.remove(selected);
    }

    /**
     * Adds given part to this menu's productPartList
     * @param actionEvent
     */
    public void addPartButton(ActionEvent actionEvent)
    {
        Part selected = (Part)allTable.getSelectionModel().getSelectedItem();
        if(selected == null)
            return;
        productPartList.add(selected);
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
