package axone.Assignment7;

import axone.Assignment7.Exception.InvalidDataException;
import axone.Assignment7.Exception.ItemNotFoundException;
import axone.Assignment7.Exception.ItemNotInCartException;
import axone.Assignment7.Exception.ItemOutOfStockException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Uses a collection class to store multiple items in the cart and implements functionalities like
 * - add to cart
 * - remove from cart
 * - calculate the total cost of the cart items
 * - get the contents of the cart
 * - checkout
 */
public class ShoppingCart {

    Inventory inventoryObj;
    Map<Item, Integer> shoppingCart = new HashMap<>();

    public ShoppingCart() {
        try {
            inventoryObj = new Inventory();
        } catch (InvalidDataException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * adds 1 item of the type passed in the argument to cart
     * @param it Item class object
     * @return true if item added to cart, else returns false
     * @throws ItemNotFoundException if item is not found in the catalogue
     * @throws ItemOutOfStockException if item is not in stock
     */
    public boolean addToCart(Item it) throws ItemNotFoundException, ItemOutOfStockException {
        boolean itemAdded;

        if(inventoryObj.validateItem(it)){
            if(inventoryObj.getItemStock(it)>=1){
                if(shoppingCart.containsKey(it)){
                    int quantityinCart = shoppingCart.get(it);
                    quantityinCart = quantityinCart + 1;
                    shoppingCart.replace(it, quantityinCart);
                    itemAdded = true;
                }
                else {
                    shoppingCart.put(it, 1);
                    itemAdded = true;
                }
            } else {
                throw new ItemOutOfStockException("This item is currently not in stock");
            }
        } else {
            throw new ItemNotFoundException("The item code is not present in the catalogue");
        }
        return itemAdded;
    }

    /**
     * adds a specified quantity of items of the type passed in the argument to cart
     * @param it Item class object
     * @param quantityToAdd number of items to add
     * @return true if items added to cart, else returns false
     * @throws ItemNotFoundException if item is not found in the catalogue
     * @throws ItemOutOfStockException if item is not in stock
     */
    public boolean addToCart(Item it, int quantityToAdd) throws ItemNotFoundException, ItemOutOfStockException {
        boolean itemAdded;

        if(inventoryObj.validateItem(it)){
            if(inventoryObj.getItemStock(it) >= quantityToAdd){
                if(shoppingCart.containsKey(it)){
                    int quantityinCart = shoppingCart.get(it);
                    quantityinCart = quantityinCart + quantityToAdd;
                    shoppingCart.replace(it, quantityinCart);
                    itemAdded = true;
                }
                else {
                    shoppingCart.put(it, quantityToAdd);
                    itemAdded = true;
                }
            } else {
                throw new ItemOutOfStockException("The number of items you are trying to add is currently not in stock");
            }
        } else {
            throw new ItemNotFoundException("The item code is not present in the catalogue");
        }
        return itemAdded;
    }

    /**
     * Removes the item passed in the argument from the cart
     * @param it the item object to be removed from cart
     * @return true if removed, else return false
     * @throws ItemNotInCartException if the item passed is not present in cart
     */
    public boolean removeFromCart(Item it) throws ItemNotInCartException {
        boolean itemRemoved;
        if(shoppingCart.get(it) != null){
            shoppingCart.remove(it);
            itemRemoved = true;
        }
        else {
            throw new ItemNotInCartException("The item you are trying to remove is not present in the cart");
        }

        return itemRemoved;
    }

    /**
     * Calculates the total cost of all items present in the cart
     * @return the total cost
     */
    public double calculateTotalCost(){
        double totalCost = 0.0;
        for(Item it : shoppingCart.keySet()){
            double itemPrice = it.getItemPrice();
            int quantityInCart = shoppingCart.get(it);
            totalCost =  totalCost + (itemPrice * quantityInCart);
        }
        return totalCost;
    }

    /**
     * Gets all the items present in the cart and returns it as an array
     * @return an array of items present in the cart
     */
    public Item[] getCartContents(){
        Set<Item> itemList =  shoppingCart.keySet();
        return (Item[]) itemList.toArray();
    }

    /**
     * Checks out all the items present in the shopping cart and reduces its stock in the inventory
     */
    void checkOut(){
        for(Item it : shoppingCart.keySet()){
            try{
                inventoryObj.reduceStock(it, shoppingCart.get(it));
            } catch (ItemNotFoundException infe){
                System.out.println(infe.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        ShoppingCart scobj = new ShoppingCart();
        Item it = new Item(1001, "iPhone 12", "A great all round phone from Apple", 400);

        try {
            System.out.println("Add to cart method worked? " + scobj.addToCart(it));
            scobj.checkOut();
            System.out.println("quantity in inventory after checkout = " + scobj.inventoryObj.getItemStock(it));
        } catch (ItemNotFoundException | ItemOutOfStockException e) {
            System.out.println(e.getMessage());
        }
    }

}
