package axone.Assignment7;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import axone.Assignment7.Exception.InvalidDataException;
import axone.Assignment7.Exception.ItemNotFoundException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

/**
 * This class represents stock levels for each item.
 * The stock is stored as a map with Item and corresponding quantities.
 * map<Item it, Integer quantity> stock
 */
public class Inventory {

    private Map<Item, Integer> stock = new HashMap<>();

    public Inventory() throws InvalidDataException {
        load();
    }

    /**
     * Reads the csv file located in src/main/resources and populates the map
     * @throws InvalidDataException if the csv file contains invalid data
     */
    private void load() throws InvalidDataException{
        String fileName = "src/main/resources/stock.csv";

        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            reader.skip(1);
            List<String[]> lines = reader.readAll();

            for (String[] lineValues : lines) {
                Item obj = new Item();
                obj.setItemCode(Integer.parseInt(lineValues[0].trim()));
                obj.setItemName(lineValues[1].trim());
                obj.setItemDescription(lineValues[2].trim());
                obj.setItemPrice(Integer.parseInt(lineValues[3].trim()));
                stock.put(obj, Integer.parseInt(lineValues[4].trim()));
            }
        } catch(CsvException e){
            throw new InvalidDataException("Invalid data in the csv file.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reduces the quantity of items in stock
     * @param it Item class object
     * @param quantity number of items to reduce from stock
     * @return true if the stock was reduced successfully, else return false
     * @throws ItemNotFoundException if the item object does not match any Item object in stock
     */
    public boolean reduceStock(Item it, Integer quantity) throws ItemNotFoundException {
        int existingQuantity;
        int replacedQuantity;
        boolean stockQuantityReduced = false;

        if(stock.containsKey(it)){
            existingQuantity = stock.get(it);
            replacedQuantity = existingQuantity - quantity;
            stockQuantityReduced = stock.replace(it, existingQuantity, replacedQuantity);
        }

        if(!stockQuantityReduced) {
            throw new ItemNotFoundException("The item passed does not match any products in the list.");
        }
        return stockQuantityReduced;
    }

    /**
     * Checks the item object passed is present in stock and returns the number present
     * @param it Item class object
     * @return the number of items in stock
     * @throws ItemNotFoundException if the Item object passed is not in stock
     */
    public int getItemStock(Item it) throws ItemNotFoundException {
        int itemStock;
        if(stock.containsKey(it)){
            itemStock = stock.get(it);
        }
        else {
            throw new ItemNotFoundException("The item passed does not match any products in the list.");
        }

        return itemStock;
    }

    /**
     * Validates if the item passed is present in stock
     * @param it Item class object
     * @return true if the item passed is in stock
     * @throws ItemNotFoundException if the item passed is not in stock
     */
    public boolean validateItem(Item it) throws ItemNotFoundException {
        boolean itemPresent;

        if(stock.containsKey(it))
            itemPresent = true;
        else
            throw new ItemNotFoundException("The item passed does not match any products in the list.");

        return itemPresent;
    }

    /**
     * Returns a map of item codes and item names present in stock
     * @return a map of item codes and item names
     */
    public Map<Integer, String> getItemCatalogue(){
        Map<Integer, String> itemCatalogue = new HashMap<>();
        for (Item it : stock.keySet()){
            itemCatalogue.put(it.getItemCode(), it.getItemName());
        }

        return itemCatalogue;
    }

    public static void main(String[] args) {
        try {
            Inventory obj = new Inventory();
            for(Map.Entry<Item, Integer> entry : obj.stock.entrySet()){
                System.out.println("Item code: " + entry.getKey().getItemCode());
                System.out.println("Item name: " + entry.getKey().getItemName());
                System.out.println("Item description: " + entry.getKey().getItemDescription());
                System.out.println("Item price: " + entry.getKey().getItemPrice());
                System.out.println("Item quantity: " + entry.getValue());
            }
            Map<Integer, String> catalogue = obj.getItemCatalogue();
            for(Integer key : catalogue.keySet()){
                System.out.println("*************RECEIVED CATALOGUE***************");
                System.out.println("Item code: "+key);
                System.out.println("Item name: "+catalogue.get(key));
            }

        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }

    }
}
