package com.store.service;

import com.store.interfaces.PromotionInterface;
import com.store.model.Cart;
import com.store.model.Promotion;

public class PromotionService implements PromotionInterface {

    private Promotion promotionModel;
    
    public PromotionService(Promotion promotionModel) {
        this.promotionModel = promotionModel;
    }

    private int calculateDiscount(Cart cart) {
        return cart.getItems().stream()
                .filter(item -> item.getName().equals(promotionModel.getItemName())
                        && item.getQuantity() >= promotionModel.getRequiredQuantity())
                .mapToInt(item -> (item.getQuantity() / promotionModel.getRequiredQuantity()) * promotionModel.getDiscountInCents())
                .sum();
    }

    @Override
    public int apply(Cart cart) {
        return calculateDiscount(cart);
    }
}