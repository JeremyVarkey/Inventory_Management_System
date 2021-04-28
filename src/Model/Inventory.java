package Model;
import javafx.collections.*;

/**
 * The Inventory class creates a static Inventory object which can be used to represent all the current entries in our Inventory system.
 *  It is composed of the private fields allParts(ObservableList), allProducts(ObservableList),partId(int) and productId(int).
 * @author Jeremy Varkey
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partId = 1;
    private static int productId = 1000;

    /**
     * Add part to inventory system.
     *
     * @param newPart the part that is to be added
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Add product to inventory system.
     *
     * @param newProduct the product that is to be added
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Looks up part to inventory system.
     *
     * @param partId the part id that is to be located
     * @return Returns Part object to be found
     */
    public static Part lookupPart(int partId) {
        if (!allParts.isEmpty()) {
            for (Part allPart : allParts) {
                if (allPart.getId() == partId) {
                    return allPart;
                }
            }
        }
        return null;
    }

    /**
     * Looks up product in inventory system.
     *
     * @param productId the product id that is to be located
     * @return Returns Product object to be found
     */
    public static Product lookupProduct(int productId) {
        if (!allProducts.isEmpty()) {
            for (Product allProduct: allProducts) {
                if (allProduct.getId() == productId) {
                    return allProduct;
                }
            }
        }
        return null;
    }

    /**
     * Looks up parts to inventory system.
     *
     * @param partName the part name that is to be located
     * @return Returns Part objects to be found
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> parts = FXCollections.observableArrayList();

        for (Part part: allParts) {
            if (part.getName().contains(partName)) {
                parts.add(part);
            }
        }

        return parts;
    }

    /**
     * Looks up products to inventory system.
     *
     * @param productName the product name that is to be located
     * @return Returns Product object that is to be found
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> products = FXCollections.observableArrayList();

        for (Product product: allProducts) {
            if (product.getName().contains(productName)) {
                products.add(product);
            }
        }

        return products;
    }

    /**
     * Updates part in the inventory system.
     *
     * @param index the index of the part that is to be located within the ObservableList
     * @param newPart the part to fill the index
     */
    public static void updatePart(int index, Part newPart) {
        allParts.set(index, newPart);
    }

    /**
     * Updates up product in inventory system.
     *
     * @param index the index of the product that is to be located within the ObservableList
     * @param newProduct the product that fills the index
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes the selected part from the inventory system.
     *
     * @param selectedPart the part that is to be deleted
     * @return Returns a boolean on if the part was successfully deleted
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Deletes the selected product from the inventory system.
     *
     * @param selectedProduct the product that is to be deleted
     * @return Returns a boolean on if the product was successfully deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Gets all parts in the inventory system.
     *
     * @return Returns an ObservableList of all parts in the inventory system.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets all products in the inventory system.
     *
     * @return Returns an ObservableList of all products in the inventory system.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Gets a new part id.
     *
     * @return Returns an int for a unique part id
     */
    public static int newPartId() {
        return partId++;
    }

    /**
     * Gets a new product id.
     *
     * @return Returns an int for a unique product id
     */
    public static int newProductId() {
        return productId++;
    }

}
