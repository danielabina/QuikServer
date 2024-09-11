package com.store.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.store.model.Cart;
import com.store.model.Item;
import com.store.service.CartService;
import com.store.service.PaymentService;
import org.json.JSONObject;

public class PaymentController {

    private CartService cartService;
    private PaymentService paymentService;

    // Constructor
    public PaymentController(CartService cartService, PaymentService paymentService) {
        this.cartService = cartService;
        this.paymentService = paymentService;

        WireMock.configureFor("localhost", 8080);
    }

    // Create a new cart
    public Cart createNewCart() {
        return cartService.createNewCart();
    }

    // Add an item to the cart
    public void addItemToCart(String cartId, String itemId) {
        String priceResponse = fetchItemPrice(itemId);

        if (priceResponse != null) {
            JSONObject jsonResponse = new JSONObject(priceResponse);
            String name = jsonResponse.getString("name");
            int priceInCents = jsonResponse.getInt("priceInCents");

            Item item = new Item(itemId, name, priceInCents, 1);
            cartService.addItem(cartId, item);
        } else {
            System.out.println("Item not found.");
        }
    }

    // Remove an item from the cart
    public void removeItemFromCart(String cartId, String itemId) {
        cartService.removeItem(cartId, itemId);
    }

    // Finalize the payment
    public int finalizePayment(String cartId) {
        Cart cart = cartService.getCartById(cartId);
        return paymentService.calculateTotal(cart);
    }

    // Get savings
    public int getSavings(String cartId) {
        Cart cart = cartService.getCartById(cartId);
        return paymentService.calculateSavings(cart);
    }

    // Fetch item price from an external service
    private String fetchItemPrice(String itemId) {
        try {
            WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/prices/" + itemId))
                    .willReturn(WireMock.aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"name\":\"Sample Item\",\"priceInCents\":500}")));

            WireMockClient wireMockClient = new WireMockClient("localhost", 8080);
            return wireMockClient.get("/api/prices/" + itemId).getBodyAsString();
        } catch (Exception e) {
            System.out.println("Error fetching item price: " + e.getMessage());
            return null;
        }
    }
}
