package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * OutsourcedPartSceneController implements Initializable and is the logic unit for OutsourcedPartScene fxml.
 * It allows the user to add an Outsourced Part to Inventory
 *
 * @RUNTIME_ERROR: There is no runtime issues with this class
 *
 * @FUTURE_ENHANCEMENT: The biggest future improvement would be to combine InHouse and Outsourced Parts add into one controller and fxml
 * I felt that it would have been easier to separate out add and modify into individual Controllers and fxml, but this actually added
 * more complexity to the program, and was not a good design choice.
 *  @author Jeremy Varkey
 */
public class OutsourcedPartSceneController implements Initializable {
    public TextField name;
    public TextField inv;
    public TextField price;
    public TextField max;
    public TextField companyName;
    public TextField min;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Sends screen to Main scene.
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
     * Sends screen to InHouse part add scene.
     *
     * @param actionEvent input action
     * @throws IOException
     */
    public void toInHousePartScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/PartScene.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,800);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Saves Outsourced part to Inventory system.
     *
     * @param actionEvent input action
     * @throws IOException
     */
    public void save(ActionEvent actionEvent) throws IOException {
        try {
            String partName = this.name.getText().trim();
            double partPrice = Double.parseDouble(this.price.getText().trim());
            int partInv = Integer.parseInt(this.inv.getText().trim());
            int partMin = Integer.parseInt(this.min.getText().trim());
            int partMax = Integer.parseInt(this.max.getText().trim());
            String compName = this.companyName.getText().trim();

            if ((partMin >= partMax) || (partInv < partMin || partInv > partMax)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Inputs are illegal.");
                alert.setContentText("Min must be less than max, and inventory must be between both min and max.");

                alert.showAndWait();
            } else {
                Inventory.addPart(new Outsourced(Inventory.newPartId(), partName, partPrice, partInv, partMin, partMax, compName));
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
