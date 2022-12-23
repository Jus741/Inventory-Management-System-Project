package Main;

import Controller.ModifyProduct;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * RUNTIME ERROR {@link ModifyProduct#initialize(URL, ResourceBundle)   HERE}<br>
 * -PROBLEM: Data from the ModifyProduct form would carry over to the master list even when the cancel button was pressed<br>
 * -SOLUTION: Use the copy method from FXCollections to make a new list so that associatedParts doesn't become a reference to the actual data<br>
 * FUTURE ENHANCEMENT<br>
 * -Parts and products should be more interconnected i.e.(tethering product stock to part stock in a way that only allows X amount of product to be made so long as there is enough parts)<br>
 */
public class Main extends Application
{

    /**
     * Loads the main menu
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/MainMenu.fxml"));
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Javadocs are located at "/IMS/Javadoc/..." <br>
     * Starts the program
     * @param args
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
