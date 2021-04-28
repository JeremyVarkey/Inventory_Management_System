package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import com.sun.webkit.ThemeClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * PartSceneController implements Initializable and is the logic unit for PartScene fxml.
 * It allows the user to add an InHouse Part to Inventory
 *
 * @RUNTIME_ERROR: There is no runtime issues with this class
 *
 * @FUTURE_ENHANCEMENT: The biggest future improvement would be to combine InHouse and Outsourced Parts add into one controller and fxml
 * I felt that it would have been easier to separate out add and modify into individual Controllers and fxml, but this actually added
 * more complexity to the program, and was not a good design choice.
 *  @author Jeremy Varkey
 */
public class PartSceneController implements Initializable{

    public TextField name;
    public TextField inv;
    public TextField price;
    public TextField max;
    public TextField MachineId;
    public TextField min;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Sends screen to main screen.
     *
     * @param actionEvent input action
     * @throws IOException
     */
    public void toMainScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScene.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,800);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sends screen to Outsourced part add scene.
     *
     * @param actionEvent input action
     * @throws IOException
     */
    public void toOutsourcedPartScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/OutsourcedPartScene.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,800);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Saves the new InHouse part to the Inventory system.
     *
     * @param actionEvent input action
     * @throws IOException
     */
    public void save(ActionEvent actionEvent) throws IOException{
        try {
            String partName = this.name.getText().trim();
            double partPrice = Double.parseDouble(this.price.getText().trim());
            int partInv = Integer.parseInt(this.inv.getText().trim());
            int partMin = Integer.parseInt(this.min.getText().trim());
            int partMax = Integer.parseInt(this.max.getText().trim());
            int partMachineId = Integer.parseInt(this.MachineId.getText().trim());

            if ((partMin >= partMax) || (partInv < partMin || partInv > partMax)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Inputs are illegal.");
                alert.setContentText("Min must be less than max, and inventory must be between both min and max.");

                alert.showAndWait();
            } else {
                Inventory.addPart(new InHouse(Inventory.newPartId(), partName, partPrice, partInv, partMin, partMax, partMachineId));
                toMainScene(actionEvent);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Inputs are illegal.");
            alert.setContentText("All fields must have legal data.");
            alert.showAndWait();
        }

    }

}
