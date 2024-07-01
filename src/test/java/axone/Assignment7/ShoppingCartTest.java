package axone.Assignment7;

import axone.Assignment7.Exception.ItemNotFoundException;
import axone.Assignment7.Exception.ItemOutOfStockException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShoppingCartTest {

    Item it;
    ShoppingCart scObj;
    @BeforeMethod
    public void initialiseItem(){
        it = new Item(1001, "iPhone 12", "A great all round phone from Apple", 400);
        scObj = new ShoppingCart();
    }


    /**
     * Adds a product to the cart and calculates the cost of cart items
     */
    @Test
    public void checkCartProductPrice(){
        try{
            scObj.addToCart(it, 5);
            Assert.assertEquals(scObj.calculateTotalCost(), 2000);
        } catch(ItemOutOfStockException | ItemNotFoundException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Adds a product to cart, checks out the cart and asserts reduction in stock
     */
    @Test
    public void checkStockReduction(){
        try{
            scObj.addToCart(it, 5);
            scObj.checkOut();
            Assert.assertEquals(scObj.inventoryObj.getItemStock(it), 15);
        } catch(ItemOutOfStockException | ItemNotFoundException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Adds an invalid item to cart and checks if appropriate exception is thrown
     */
    @Test
    public void addInvalidItem(){
        Item invalidItem = new Item(1004, "Nokia 1100", "The first ever classic", 30);
        try{
            scObj.addToCart(invalidItem, 5);
        } catch(ItemOutOfStockException | ItemNotFoundException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Adds an item that is out of stock and checks if appropriate exception is thrown
     */
    @Test
    public void addOutOfStockItem(){
        Item outOfStockItem = new Item(1003, "Nokia 6600", "An unbreakable fossil", 50);
        try{
            scObj.addToCart(outOfStockItem, 5);
        } catch(ItemOutOfStockException | ItemNotFoundException exception){
            System.out.println(exception.getMessage());
        }
    }

}
