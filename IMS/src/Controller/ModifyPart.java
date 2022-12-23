package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Main.*;

import javax.swing.*;

public class ModifyPart implements Initializable
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
    public TextField idNumber;
    public static Part selectedPart;
    public RadioButton InHouse;
    public RadioButton Outsourced;

    /**
     * Fills TextFields with the data from the part that was set in the call
     * Also checks the given's class to determine which radio button to start on
     * @param url
     * @param resourceBundle
     * @throws NullPointerException
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) throws NullPointerException
    {
        idNumber.setText(Integer.toString(selectedPart.getId()));
        partName.setText(selectedPart.getName());
        partStock.setText(Integer.toString(selectedPart.getStock()));
        partPrice.setText(Double.toString(selectedPart.getPrice()));
        partMax.setText(Integer.toString(selectedPart.getMax()));
        partMin.setText(Integer.toString(selectedPart.getMin()));

        if(selectedPart.getClass().equals(InHouse.class))
        {
            InHouse.setSelected(true);
            InHouse temp = (InHouse)selectedPart;
            definingVar.setText(Integer.toString(temp.getMachineId()));
            partSwitch = true;
        }
        else
        {
            Outsourced.setSelected(true);
            Outsourced temp = (Outsourced)selectedPart;
            definingVar.setText(temp.getCompanyName());
            partSwitch = false;
        }
    }

    /**
     * Checks for input errors and shows dialog boxes accordingly
     * Otherwise constructs a new part with the taken data and updates the old one
     * Then loads the main menu
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
                partID = selectedPart.getId();
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
                valid = true;
                if (partSwitch)
                {
                    try
                    {
                        inputMachineID = Integer.parseInt(definingVar.getText());
                    }
                    catch (NumberFormatException i)
                    {
                        JOptionPane.showMessageDialog(null, "Wrong input for this part type");
                        break;
                    }

                    InHouse temp = new InHouse(partID, inputName, inputPrice, inputStock, inputMin, inputMax, inputMachineID);
                    for(int i = 0; i < Inventory.getAllParts().size(); i++)
                    {
                        if (selectedPart.getId() == Inventory.getAllParts().get(i).getId())
                        {
                            Inventory.updatePart(i,temp);
                        }
                    }
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
                    for(int i = 0; i < Inventory.getAllParts().size(); i++)
                    {
                        if (selectedPart.getId() == Inventory.getAllParts().get(i).getId())
                        {
                            Inventory.updatePart(i,temp);
                        }
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
     * Changes text to Machine ID on actionEvent
     * @param actionEvent
     */
    public void inHousePart(ActionEvent actionEvent)
    {
        changeText.setText("Machine ID");
        partSwitch = true;
    }

    /**
     * Changes text to Company Name on actionEvent
     * @param actionEvent
     */
    public void outsourcedPart(ActionEvent actionEvent)
    {
        changeText.setText("Company Name");
        partSwitch = false;
    }
}
