package Model;

/**
 * The InHouse class is composed of the private field machineId(int) and extends the abstract Part class.
 * It creates an InHouse Part object which can be used to represent an InHouse part in our inventory system.
 * @author Jeremy Varkey
 */
public class InHouse extends Part{
    private int machineId;

    /**
     * Constructor for a new instance of an InHouse object.
     *
     * @param id the ID for the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the inventory of the part
     * @param min the minimum for the part
     * @param max the maximum for the part
     * @param machineId the machine ID for the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Gets MachineId, specific to InHouse.
     *
     * @return Returns machineId.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Sets MachineId, specific to InHouse.
     *
     * @param machineId Sets MachineId.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
