package org.thor.integration.discounts;

import org.thor.model.ItemList;

/**
 * A discount implementation.
 */
public class ItemDiscount implements Discount {
    ItemList items = ItemList.getInstance();
    String itemId;
    double percentage;

    /**
     * Constructor of item discount.
     *
     * @param id         a string representing a specific item.
     * @param percentage the discount percentage on a specific item.
     */
    public ItemDiscount(String id, double percentage) {
        this.itemId = id;
        this.percentage = percentage;
    }

    /**
     * Applies the discount if available to price objects in ItemList.
     *
     * @param items represents items in the ItemList .
     */
    @Override
    public void applyDiscount() {
        if (isDiscountValid()) {
            items.getItemTypeById(this.itemId).getTotalPrice().setDiscountByPercentage(this.percentage);
        }
    }

    /**
     * Represents the the discount in text form.
     *
     * @param items contains itemTypes that will define witch if the discount is available.
     * @return a string representation of the particular discount.
     */
    @Override
    public String textRepresentation() {
        String s = null;
        if (isDiscountValid()) {
            s = String.format("%.0f %% discount on %20s:    -%.2f kr.", percentage, items.getItemTypeById(itemId).getDescription(),
                    (items.getItemTypeById(itemId).getTotalPrice().getPrice() * percentage / 100));
        }
        return s;
    }

    private boolean isDiscountValid() {
        return items.isItemTypeInList(itemId);
    }
}
