package si.magerl.spending.tracker.metal.client;

import io.quarkus.rest.client.reactive.ClientQueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import si.magerl.spending.tracker.metal.dto.MetalAPIResponse;

@RegisterRestClient(baseUri = "https://api.metalpriceapi.com/v1")
public interface MetalApiClient {

    @GET
    @Path("/latest")
    @ClientQueryParam(name = "base", value = "XAU")
    @ClientQueryParam(name = "currencies", value = "EUR")
    // TODO: Connect to GCP's Secret Manager to retrieve API token
    @ClientQueryParam(name = "api_key", value = "value-from-secret-manager")
    MetalAPIResponse getGoldPrice();
}
