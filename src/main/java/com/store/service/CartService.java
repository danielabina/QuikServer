package com.store.service;

import com.store.model.Cart;
import com.store.model.Item;

import java.util.HashMap;
import java.util.Map;

public class CartService {

    private Map<String, Cart> carts = new HashMap<>();

    public Cart createNewCart() {
        Cart cart = new Cart();
        carts.put(cart.getId(), cart);
        return cart;
    }

    public void addItem(String cartId, Item item) {
        Cart cart = carts.get(cartId);
        if (cart != null) {
            cart.getItens().add(item);
        }
    }

    public Cart getCartById(String cartId) {
        return carts.get(cartId);
    }

    public void removeItem(String cartId, String itemId) {
        Cart cart = carts.get(cartId);
        if (cart != null) {
            cart.getItens().removeIf(item -> item.getId().equals(itemId));
        }
    }
}
