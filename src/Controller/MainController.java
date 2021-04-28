package Controller;

import Model.*;
import com.sun.glass.ui.Application;
import com.sun.webkit.ThemeClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodTextRun;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.InputMethodEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * MainController implements Initializable and is the main logic center and home screen for the Inventory Management application.
 * It links to MainScene.fxml
 *
 * @RUNTIME_ERROR: There are 6 main cases where we can have exceptions: deletePart, deleteProduct, toModifyPartScene, toModifyProduct, searchPart, searchProduct
 * The errors for methods that require selection come from when a selection is not made, and is caught. The errors for search derive from parseInt, and the try catch
 * is used to determine if the user has inputted a String or an int.
 *
 * @FUTURE_ENHANCEMENT: The biggest future enhancement would be to expand the column set to see the Part level details like MachineID and CompanyName
 * @author Jeremy Varkey
 */
public class MainController implements Initializable {

    public TableView partTable;
    public TableColumn partId;
    public TableColumn partName;
    public TableColumn partInventory;
    public TableColumn partPrice;

    public TableView productTable;
    public TableColumn productId;
    public TableColumn productName;
    public TableColumn productInventory;
    public TableColumn productPrice;

    public Button addPart;
    public Button modifyPart;
    public Button deletePart;

    public Button addProduct;
    public Button modifyProduct;
    public Button deleteProduct;

    public Button exit;
    public TextField partSearchField;
    public TextField productSearchField;

    public static Part partToModify;
    public static Product productToModify;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productTable.setItems(Inventory.getAllProducts());
        partTable.setItems(Inventory.getAllParts());

        productId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        partId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    /**
     * Goes to add InHouse part scene.
     *
     * @param actionEvent action input
     */
    public void toPartScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/PartScene.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,800);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Goes to modify InHouse part scene.
     *
     * @param actionEvent action input
     */
    public void toModifyPartScene(ActionEvent actionEvent) throws IOException {
        try {
            ObservableList<Part> p = partTable.getSelectionModel().getSelectedItems();
            this.partToModify = p.get(0);
            if (partToModify instanceof InHouse) {
                Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPartScene.fxml"));
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 800,800);
                stage.setScene(scene);
                stage.show();
            } else {
                Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyOutsourcedPartScene.fxml"));
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 800,800);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("No Data Selected");
            alert.setContentText("No part selected to modify.");
            alert.showAndWait();
        }
    }

    /**
     * Gets selected part to modify from parts table.
     *
     * @return Returns selected part to modify
     */
    public static Part getPartToModify() {
        return partToModify;
    }

    /**
     * Sets the part to modify in a field that can store the part.
     *
     * @param p part to modify
     */
    public static void setParttoModify(Part p) {
        partToModify = p;
    }

    /**
     * Gets selected product to modify from product table.
     *
     * @return Returns the selected product to modify
     */
    public static Product getProductToModify() {
        return productToModify;
    }

    /**
     * Goes to modify product scene.
     *
     * @param actionEvent action input
     */
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        try {
            ObservableList<Product> p = productTable.getSelectionModel().getSelectedItems();
            this.productToModify = p.get(0);
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800,800);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("No Data Selected");
            alert.setContentText("No product selected to modify.");
            alert.showAndWait();
        }

    }

    /**
     * Goes to add product scene.
     *
     * @param actionEvent action input
     * @throws IOException
     */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,800);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Searches part from part table.
     *
     * @param actionEvent action input
     * @throws IOException
     */
    public void searchPart(ActionEvent actionEvent) {
        try {
            Part tempPart = Inventory.lookupPart(Integer.parseInt(this.partSearchField.getText()));
            ObservableList<Part> tempList = FXCollections.observableArrayList();
            tempList.add(tempPart);
            partTable.setItems(tempList);
            if (tempList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Empty set.");
                alert.setContentText("No items returned.");
                alert.showAndWait();
            }
        } catch (NumberFormatException nfe) {
            partTable.setItems(Inventory.lookupPart(this.partSearchField.getText()));
            if (Inventory.lookupPart(this.partSearchField.getText()).isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Empty set.");
                alert.setContentText("No items returned.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Searches product form product table.
     *
     * @param actionEvent action input
     */
    public void searchProduct(ActionEvent actionEvent) {
        try {
            Product tempProd = Inventory.lookupProduct(Integer.parseInt(this.productSearchField.getText()));
            ObservableList<Product> tempList = FXCollections.observableArrayList();
            tempList.add(tempProd);
            productTable.setItems(tempList);
            if (tempList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Empty set.");
                alert.setContentText("No items returned.");
                alert.showAndWait();
            }
        } catch (NumberFormatException nfe) {
            productTable.setItems(Inventory.lookupProduct(this.productSearchField.getText()));
            if (Inventory.lookupProduct(this.productSearchField.getText()).isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Empty set.");
                alert.setContentText("No items returned.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Deletes part from part table and Inventory system.
     *
     * @param actionEvent action input
     */
    public void deletePart(ActionEvent actionEvent) {
        try {
            ObservableList<Part> p = partTable.getSelectionModel().getSelectedItems();
            this.partToModify = p.get(0);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Part is permanent.");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(partToModify);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("No Data Selected");
            alert.setContentText("No part selected to remove.");
            alert.showAndWait();
        }
    }

    /**
     * Deletes product from product table and inventory system.
     *
     * @param actionEvent action input
     */
    public void deleteProduct(ActionEvent actionEvent) {
        try {
            ObservableList<Product> p = productTable.getSelectionModel().getSelectedItems();
            this.productToModify = p.get(0);
            if (!productToModify.getAllAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Cannot delete product.");
                alert.setContentText("There are associated parts to the selected product that must be removed.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Delete product is permanent.");
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(productToModify);
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("No Data Selected");
            alert.setContentText("No product selected to remove.");
            alert.showAndWait();
        }
    }

    /**
     * Ends inventory management application.
     *
     * @param actionEvent action input
     * @throws IOException
     */
    public void end (ActionEvent actionEvent) throws IOException {
        System.exit(0);
    }

}
