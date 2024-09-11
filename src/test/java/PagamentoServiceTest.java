import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.store.model.Cart;
import com.store.model.Item;
import com.store.service.CartService;
import com.store.service.PaymentService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;


public class PaymentServiceTest {

    private WireMockServer wireMockServer;

    @Before
    public void setup() {

        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();

        wireMockServer.stubFor(get(urlEqualTo("/api/prices/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"name\": \"Rice\", \"priceInCents\": 500}"))); // Updated field names
    }

    @After
    public void teardown() {

        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    public void testCalculateTotalWithDiscount() {
        CartService cartService = new CartService();
        PaymentService paymentService = new PaymentService();

        Cart cart = cartService.createNewCart();

        Item item = new Item("1", "Rice", 500, 2);
        cartService.addItem(cart.getId(), item);

        int total = paymentService.calculateTotal(cart);

        assertEquals(1000, total);
    }
}
