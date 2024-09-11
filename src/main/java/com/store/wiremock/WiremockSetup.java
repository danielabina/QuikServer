package com.store.wiremock;


import com.github.tomakehurst.wiremock.WireMockServer;


import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WiremockSetup {
  private WireMockServer wireMockServer;

  public class WireMockSetup {

    private WireMockServer wireMockServer;

    // Start the WireMock server and configure stubs
    public void startWireMockServer() {
      wireMockServer = new WireMockServer(options().port(8080));
      wireMockServer.start();

      // Stub to fetch price for item with ID 1
      wireMockServer.stubFor(
              get(urlEqualTo("/api/prices/1"))
                      .willReturn(
                              aResponse()
                                      .withHeader("Content-Type", "application/json")
                                      .withBody("{\"name\": \"Rice\", \"priceInCents\": 500}"))); // Updated field names

      // Stub to fetch price for item with ID 2
      wireMockServer.stubFor(
              get(urlEqualTo("/api/prices/2"))
                      .willReturn(
                              aResponse()
                                      .withHeader("Content-Type", "application/json")
                                      .withBody("{\"name\": \"Beans\", \"priceInCents\": 700}"))); // Updated field names
    }

    // Stop the WireMock server
    public void stopWireMockServer() {
      if (wireMockServer != null && wireMockServer.isRunning()) {
        wireMockServer.stop();
      }
    }
  }
    }
