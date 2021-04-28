package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * AddProduct implements Initializable and is the logic unit for AddProduct fxml.
 *
 * @RUNTIME_ERROR: Exceptions can occur when we search the Parts tables or delete/add parts. In search, since the user can either search by int or String,
 * the exception comes from parseInt, but is caught and used to implement that multi-search functionality.
 * For delete/add, the user must select a part. If no part is selected, it could be an exception, so try/catch is implemented.
 *
 * @FUTURE_ENHANCEMENT: When a part is used in a product, inventory should decrease. This would reflect a dynamic inventory system.
 * @author Jeremy Varkey
 */
public class AddProduct implements Initializable {
    public TextField name;
    public TextField inv;
    public TextField price;
    public TextField max;
    public TextField min;
    public TextField search;
    public Button addPart;
    public Button removePart;
    public TableView allParts;
    public TableColumn partId;
    public TableColumn partName;
    public TableColumn partInventory;
    public TableView selectedParts;
    public TableColumn partPrice;
    public TableColumn sPartId;
    public TableColumn sPartName;
    public TableColumn sPartInventory;
    public TableColumn sPartPrice;

    public ObservableList<Part> partsList = FXCollections.observableArrayList();
    public static Part partToAdd;
    public static Part partToDelete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allParts.setItems(Inventory.getAllParts());

        partId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        selectedParts.setItems(partsList);

        sPartId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        sPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        sPartInventory.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        sPartPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

    }

    /**
     * Send screen to the main screen.
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
     * Searches all parts table.
     *
     * @param actionEvent input action
     */
    public void search (ActionEvent actionEvent) {
        try {
            Part tempPart = Inventory.lookupPart(Integer.parseInt(this.search.getText()));
            ObservableList<Part> tempList = FXCollections.observableArrayList();
            tempList.add(tempPart);
            allParts.setItems(tempList);
        } catch (NumberFormatException nfe) {
            allParts.setItems(Inventory.lookupPart(this.search.getText()));
        }
    }

    /**
     * Adds part from all parts to selected parts.
     *
     * @param actionEvent input action
     */
    public void add(ActionEvent actionEvent) {
        try {
            ObservableList<Part> p = allParts.getSelectionModel().getSelectedItems();
            this.partToAdd = p.get(0);
            partsList.add(partToAdd);
        } catch (Exception e) {

        }

    }

    /**
     * Removes part from selected parts.
     *
     * @param actionEvent input action
     */
    public void remove(ActionEvent actionEvent) {
        try {
            ObservableList<Part> p = selectedParts.getSelectionModel().getSelectedItems();
            this.partToDelete = p.get(0);
            partsList.remove(partToDelete);
        } catch (Exception e) {

        }
    }

    /**
     * Saves the additional product into Inventory management system.
     *
     * @param actionEvent input action
     * @throws IOException
     */
    public void save(ActionEvent actionEvent) throws IOException{
        try {
            String prodName = this.name.getText().trim();
            double prodPrice = Double.parseDouble(this.price.getText().trim());
            int prodInv = Integer.parseInt(this.inv.getText().trim());
            int prodMin = Integer.parseInt(this.min.getText().trim());
            int prodMax = Integer.parseInt(this.max.getText().trim());

            if ((prodMin >= prodMax) || (prodInv < prodMin || prodInv > prodMax)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Inputs are illegal.");
                alert.setContentText("Min must be less than max, and inventory must be between both min and max.");

                alert.showAndWait();
            } else {
                int tempId = Inventory.newProductId();
                Inventory.addProduct(new Product(tempId, prodName, prodPrice, prodInv, prodMin, prodMax));
                for (Part p: partsList) {
                    Inventory.lookupProduct(tempId).addAssociatedPart(p);
                }
                toMainScene(actionEvent);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Inputs are illegal.");
            alert.setContentText("All fields must have valid data.");
            alert.showAndWait();
        }


    }
    
}
