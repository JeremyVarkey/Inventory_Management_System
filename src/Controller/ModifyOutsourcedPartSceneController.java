package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * ModifyOutsourcedPartSceneController implements Initializable and is the logic unit for ModifyOutsourcedPartScene fxml.
 * It allows the user to modify an existing Outsourced part.
 *
 * @RUNTIME_ERROR: When the user switches from OutsourcedPart to InHouse, it converts CompanyName(String) to MachineId(int), which results in
 * a Number Format Exception. This Exception is caught, but the initial entry in CompanyName is replaced with a -1 as a replacement MachineId
 *
 * @FUTURE_ENHANCEMENT: Similar with PartSceneController and OutsourcedPartSceneController, ModifyPartSceneController should have been combined with ModifyOutsourcedPartSceneController
 * This oversight added complexity to my program, especially when switching from Modifying an OutsourcedPart to an InHouse Part.
 * @author Jeremy Varkey
 */
public class ModifyOutsourcedPartSceneController implements Initializable {
    public TextField id;
    public TextField name;
    public TextField inv;
    public TextField price;
    public TextField max;
    public TextField companyName;
    public TextField min;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Part p = MainController.getPartToModify();
        if (p instanceof Outsourced) {
            companyName.setText(String.valueOf(((Outsourced) p).getCompanyName()));
        } else {
            companyName.setText(String.valueOf(((InHouse) p).getMachineId()));
        }
        id.setText(String.valueOf(p.getId()));
        name.setText(String.valueOf(p.getName()));
        inv.setText(String.valueOf(p.getStock()));
        price.setText(String.valueOf(p.getPrice()));
        max.setText(String.valueOf(p.getMax()));
        min.setText(String.valueOf(p.getMin()));
    }

    /**
     * Sends screen to Main screen.
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
     * Sends screen to Modify InHouse part scene.
     *
     * @param actionEvent input action
     * @throws IOException
     */
    public void toModifyInHousePartScene(ActionEvent actionEvent) throws IOException {
        String partName = this.name.getText().trim();
        int partId = Integer.parseInt(id.getText().trim());
        double partPrice = Double.parseDouble(this.price.getText().trim());
        int partInv = Integer.parseInt(this.inv.getText().trim());
        int partMin = Integer.parseInt(this.min.getText().trim());
        int partMax = Integer.parseInt(this.max.getText().trim());
        int machineId;

        try {
            machineId= Integer.parseInt(this.companyName.getText().trim());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Outsourced to InHouse Part");
            alert.setContentText("The default machineId for an InHouse part will be -1. If you'd like to retain the original part, please cancel and click modify again.");
            alert.showAndWait();

            machineId = -1;
        }


        MainController.setParttoModify(new InHouse(partId, partName, partPrice, partInv, partMin, partMax, machineId));
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPartScene.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,800);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Saves the modified Outsourced part into Inventory system.
     *
     * @param actionEvent input action
     * @throws IOException
     */
    public void save(ActionEvent actionEvent) throws IOException {
        try {
            String partName = this.name.getText().trim();
            int partId = Integer.parseInt(this.id.getText().trim());
            double partPrice = Double.parseDouble(this.price.getText().trim());
            int partInv = Integer.parseInt(this.inv.getText().trim());
            int partMin = Integer.parseInt(this.min.getText().trim());
            int partMax = Integer.parseInt(this.max.getText().trim());
            String companyName = this.companyName.getText().trim();

            if ((partMin >= partMax) || (partInv < partMin || partInv > partMax) || partName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Inputs are illegal.");
                alert.setContentText("Min must be less than max, and inventory must be between both min and max. Name cannot be empty");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Modification will be permanent.");
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    MainController.setParttoModify(new Outsourced(partId, partName, partPrice, partInv, partMin, partMax, companyName));
                    Inventory.deletePart(Inventory.lookupPart(MainController.getPartToModify().getId()));
                    Inventory.addPart(MainController.getPartToModify());

                    toMainScene(actionEvent);
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Inputs are illegal.");
            alert.setContentText("All fields must be non empty and legal values.");
            alert.showAndWait();
        }
    }
}
