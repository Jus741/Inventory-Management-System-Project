package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Main.*;

import javax.swing.*;

public class AddPart implements Initializable
{
    public boolean partSwitch;
    public int partID;
    public TextField partName;
    public String inputName;
    public TextField partStock;
    public int inputStock;
    public TextField partPrice;
    public double inputPrice;
    public TextField partMax;
    public int inputMax;
    public TextField definingVar;
    public int inputMachineID;
    public String inputCompanyName;
    public TextField partMin;
    public int inputMin;
    public Text changeText;

    /**
     * Sets part type to InHouse at startup by default
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        partSwitch = true;
    }

    /**
     * Takes in form data to create a new part and adds it to the master list
     * Then laods the main menu
     * @param actionEvent
     * @throws IOException
     */
    public void saveButton(ActionEvent actionEvent) throws IOException
    {
        boolean valid = false;
        while(!valid)
        {
            if(partName.getText().equals("")||partPrice.getText().equals("")||partStock.getText().equals("")||partMin.getText().equals("")||partMax.getText().equals("")||definingVar.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"Fields are required");
                break;
            }
            else
            {
                inputName = partName.getText();
                try
                {
                    inputPrice = Double.parseDouble(partPrice.getText());
                }
                catch (NumberFormatException i)
                {
                    JOptionPane.showMessageDialog(null, "Check price for type mismatch");
                    break;
                }
                try
                {
                    inputStock = Integer.parseInt(partStock.getText());
                }
                catch (NumberFormatException i)
                {
                    JOptionPane.showMessageDialog(null, "Check inv for type mismatch");
                    break;
                }
                try
                {
                    inputMin = Integer.parseInt(partMin.getText());
                }
                catch (NumberFormatException i)
                {
                    JOptionPane.showMessageDialog(null, "Check min for type mismatch");
                    break;
                }
                try
                {
                    inputMax = Integer.parseInt(partMax.getText());
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
                partID = Inventory.generatePartID();
                valid = true;
                if (partSwitch)
                {
                    try
                    {
                        inputMachineID = Integer.parseInt(definingVar.getText());
                    }
                    catch (NumberFormatException i)
                    {
                        JOptionPane.showMessageDialog(null, "Wrong input for this type of part");
                        break;
                    }

                    InHouse temp = new InHouse(partID, inputName, inputPrice, inputStock, inputMin, inputMax, inputMachineID);
                    Inventory.addPart(temp);
                }
                else
                {
                    try
                    {
                        inputCompanyName = definingVar.getText();
                    }
                    catch (NumberFormatException i)
                    {
                        JOptionPane.showMessageDialog(null, "Wrong input for this part type");
                        break;
                    }

                    Outsourced temp = new Outsourced(partID, inputName, inputPrice, inputStock, inputMin, inputMax, inputCompanyName);
                    Inventory.addPart(temp);
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
     * Sets part type to InHouse and changes menu text accordingly
     * @param actionEvent
     */
    public void inHousePart(ActionEvent actionEvent)
    {
        changeText.setText("Machine ID");
        partSwitch = true;
    }

    /**
     * Sets part type to Outsourced and changes menu text accordingly
     * @param actionEvent
     */
    public void outsourcedPart(ActionEvent actionEvent)
    {
        changeText.setText("Company Name");
        partSwitch = false;
    }
}
