package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.*;

/**
 * This is the main file for the Inventory Management System.
 * It is pre-populated with some parts and products.
 * Javadoc:file:/Inventory_Management_JeremyVarkey/JavaDoc
 * Index file: /Inventory_Management_JeremyVarkey/index.html
 * @author Jeremy Varkey
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainScene.fxml"));
        primaryStage.setTitle("Inventory Management Tool");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.show();
    }

    /**
     * Starts Inventory Management program and populates with sample data.
     *
     */
    public static void main(String[] args) {
        //Add sample parts
        InHouse fastBrakes = new InHouse(Inventory.newPartId(), "Fast Brakes", 50.00,3,0,6,101);
        InHouse slowBrakes = new InHouse(Inventory.newPartId(), "Slow Brakes", 25.00, 3, 0, 6, 101);
        InHouse seat = new InHouse(Inventory.newPartId(), "Seat", 75.00, 4, 1, 7, 101);
        Outsourced gears = new Outsourced(Inventory.newPartId(), "Gear", 100.00, 4, 1, 7, "Trek");
        Inventory.addPart(fastBrakes);
        Inventory.addPart(slowBrakes);
        Inventory.addPart(seat);
        Inventory.addPart(gears);

        //Add sample products
        Product fastBike = new Product(Inventory.newProductId(),"Fast Bike", 300.00, 1, 0,6);
        fastBike.addAssociatedPart(fastBrakes);
        fastBike.addAssociatedPart(seat);
        fastBike.addAssociatedPart(gears);

        Product tricycle = new Product(Inventory.newProductId(), "Tricycle",250.00,1, 0, 6);
        tricycle.addAssociatedPart(slowBrakes);
        tricycle.addAssociatedPart(seat);
        tricycle.addAssociatedPart(gears);

        Inventory.addProduct(fastBike);
        Inventory.addProduct(tricycle);

        launch(args);

    }
}
