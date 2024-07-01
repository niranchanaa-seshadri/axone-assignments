package axone.Assignment7;

import java.util.Objects;

/**
 * Class to store single product or item. Key attributes stored are
 * - Item code
 * - Item name
 * - Item description
 * - Price
 */
public class Item {

    private int itemCode;
    private String itemName;
    private String itemDescription;
    private double itemPrice;

    public Item(int itemCode, String itemName, String itemDescription, double itemPrice) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
    }

    public Item(){

    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     * @param o object of Item class to verify
     * @return true if object passed contains the same attributes, else return false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemCode == item.itemCode && Double.compare(itemPrice, item.itemPrice) == 0 && Objects.equals(itemName, item.itemName) && Objects.equals(itemDescription, item.itemDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode, itemName, itemDescription, itemPrice);
    }
}
