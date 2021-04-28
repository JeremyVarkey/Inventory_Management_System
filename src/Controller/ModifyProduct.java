package Controller;

import Main.Main;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Controller.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * ModifyProduct implements Initializable and is the logic unit for ModifyProduct fxml.
 *
 * @RUNTIME_ERROR: Exceptions can occur when we search the Parts tables or delete/add parts. In search, since the user can either search by int or String,
 * the exception comes from parseInt, but is caught and used to implement that multi-search functionality.
 * For delete/add, the user must select a part. If no part is selected, it could be an exception, so try/catch is implemented.
 *
 * @FUTURE_ENHANCEMENT: When a part is used in a product, inventory should decrease. This would reflect a dynamic inventory system.
 * @author Jeremy Varkey
 */
public class ModifyProduct implements Initializable {
    public TextField id;
    public TextField name;
    public TextField inv;
    public TextField price;
    public TextField max;
    public TextField min;
    public TableColumn partId;
    public TableColumn partName;
    public TableColumn partInventory;
    public TableColumn partPrice;
    public TableColumn sPartId;
    public TableColumn sPartName;
    public TableColumn sPartInventory;
    public TableColumn sPartPrice;
    public TextField search;
    public TableView allParts;
    public TableView selectedParts;

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

        for (Part p: MainController.getProductToModify().getAllAssociatedParts()) {
            partsList.add(p);
        }

        selectedParts.setItems(partsList);

        sPartId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        sPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        sPartInventory.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        sPartPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        Product p = MainController.getProductToModify();
        id.setText(String.valueOf(p.getId()));
        name.setText(String.valueOf(p.getName()));
        inv.setText(String.valueOf(p.getStock()));
        price.setText(String.valueOf(p.getPrice()));
        max.setText(String.valueOf(p.getMax()));
        min.setText(String.valueOf(p.getMin()));
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
     * Saves modified product data.
     *
     * @param actionEvent input action
     * @throws IOException
     */
    public void save(ActionEvent actionEvent) throws IOException {
        try {
            Inventory.deleteProduct(MainController.getProductToModify());

            int prodId = Integer.parseInt(id.getText().trim());
            String prodName = name.getText().trim();
            double prodPrice = Double.parseDouble(price.getText().trim());
            int prodInv = Integer.parseInt(inv.getText().trim());
            int prodMin = Integer.parseInt(min.getText().trim());
            int prodMax = Integer.parseInt(max.getText().trim());

            if ((prodMin >= prodMax) || (prodInv < prodMin || prodInv > prodMax) || prodName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Inputs are illegal.");
                alert.setContentText("Min must be less than max, and inventory must be between both min and max.");

                alert.showAndWait();
            } else {
                Inventory.addProduct(new Product(prodId,prodName,prodPrice,prodInv,prodMin,prodMax));

                for (Part p: partsList) {
                    Inventory.lookupProduct(prodId).addAssociatedPart(p);
                }

                toMainScene(actionEvent);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Inputs are illegal.");
            alert.setContentText("All fields must contain legal values.");
            alert.showAndWait();
        }


    }

    /**
     * Searches all part data.
     *
     * @param actionEvent input action
     */
    public void search(ActionEvent actionEvent) {
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
     * Deletes part from associated parts selected parts.
     *
     * @param actionEvent input action
     */
    public void delete(ActionEvent actionEvent) {
        try {
            ObservableList<Part> p = selectedParts.getSelectionModel().getSelectedItems();
            this.partToDelete = p.get(0);
            partsList.remove(partToDelete);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("No selection made.");
            alert.setContentText("Please select a part to delete.");

            alert.showAndWait();
        }
    }

    /**
     * Adds part to selected parts.
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
}
