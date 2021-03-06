package org.thor.model;

import org.thor.integration.InventoryCatalog;

/**
 * Represents an sale.
 */
public class Sale {

    private final InventoryCatalog inventoryCatalog = new InventoryCatalog();
    private ItemList itemList = ItemList.getInstance();

    /**
     * Creates a new instance, representing a sale.
     */
    public Sale() {
    }

    /**
     * Enters item to sale, item is checked against inventory.
     *
     * @param itemId   specific item identifier.
     * @param quantity quantity of specific item.
     * @return return if the item is in the list of not.
     */
    public boolean enterItem(String itemId, int quantity) throws InvalidItemIdException, DatabaseFailureException {
        boolean isItemInList = itemList.isItemTypeInList(itemId);
        if (isItemInList)
            itemList.addItemByQuantity(itemId, quantity);
        else {
            try{
                itemList.addItemType(inventoryCatalog.getItemById(itemId, quantity));
            }catch (InvalidItemIdException e){
                throw new InvalidItemIdException("The ItemId \"" + itemId + "\" is invalid, please check spelling and item ID." );
            } catch (DatabaseFailureException e){
                throw new DatabaseFailureException("The database can not be accessed. Try to reboot system.");
            }
        }
        return isItemInList;
    }

    /**
     * Ends the sale by generating an receipt
     *
     * @param payment an object containing the information of the payment.
     */
    public Receipt endSale(Payment payment) {
        return new Receipt(itemList, payment);
    }

    /**
     * Get methods
     */
    public ItemList getItemList() {
        return itemList;
    }

    public ItemType getItemById(String itemId) {
        return itemList.getItemTypeById(itemId);
    }

    public double getRunningTotal() {
        return itemList.getPrice().getPrice();
    }

    public String getScannedItems() {
        return itemList.getScannedItems();
    }

}
