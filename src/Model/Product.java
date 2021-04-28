package Model;

import javafx.collections.*;

/**
 * The Product class creates a product object which can be used to represent a product in our inventory system.
 * It is composed of the private fields id(int), name(String), price(double), stock(int), min(int), max(int) and associatedParts(ObservableList).
 * @author Jeremy Varkey
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Constructor for a new instance of a Product object.
     *
     * @param id the ID for the product
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the inventory of the product
     * @param min the minimum for the product
     * @param max the maximum for the product
     */
    public Product (int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets id of the product.
     *
     * @param id the id of the product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets name of the product.
     *
     * @param name the name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets price of the product.
     *
     * @param price the price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets inventory of the product.
     *
     * @param stock the inventory of the product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets minimum inventory of the product.
     *
     * @param min the minimum inventory of the product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets maximum inventory of the product.
     *
     * @param max the maximum inventory of the product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets id of the product.
     *
     * @return Returns id of the product
     */
    public int getId(){
        return id;
    }

    /**
     * Gets name of the product.
     *
     * @return Returns name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Gets price of the product.
     *
     * @return Returns price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets inventory of the product.
     *
     * @return Returns stock of the product
     */
    public int getStock() {
        return stock;
    }

    /**
     * Gets minimum inventory of the product.
     *
     * @return Returns min stock of the product
     */
    public int getMin() {
        return min;
    }

    /**
     * Gets maximum inventory of the product.
     *
     * @return Returns max stock of the product
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets associated parts of the product.
     *
     * @param  part Parts that are associated to the product
     */
    public void addAssociatedPart (Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes associated parts of the product.
     *
     * @return Returns true or false of deletion of associated part of the product
     * @param selectedAssociatedPart the associated part that is to be removed.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Gets all associated parts of the product.
     *
     * @return Returns ObservableList of all associated parts of the product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
