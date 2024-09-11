package com.store.service;

import com.store.interfaces.PromotionInterface;
import com.store.model.Cart;
import com.store.model.Promotion;

import java.util.List;

public class PaymentService {
    private List<Promotion> promotions; // Renamed for clarity
    private PromotionService promotionService;

    public int calculateTotal(Cart cart) {
        int total = cart.getItems().stream() // Renamed getItens() to getItems() for consistency
                .mapToInt(item -> item.getPriceInCents() * item.getQuantity()) // Renamed methods for consistency
                .sum();
        int discount = calculateDiscount(cart); // Renamed method
        return total - discount;
    }

    public int calculateDiscount(Cart cart) {
        return promotions.stream() // Renamed promotions for clarity
                .mapToInt(promotion -> promotionService.apply(cart)) // Renamed method
                .sum();
    }

    public int calculateSavings(Cart cart) { // Renamed method
        return calculateDiscount(cart);
    }
}