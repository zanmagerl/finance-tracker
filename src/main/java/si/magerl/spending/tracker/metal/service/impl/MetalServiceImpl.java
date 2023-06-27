package si.magerl.spending.tracker.metal.service.impl;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import si.magerl.spending.tracker.metal.client.MetalApiClient;
import si.magerl.spending.tracker.metal.service.MetalService;

@ApplicationScoped
public class MetalServiceImpl implements MetalService {

    private static final String EUR_SYMBOL = "EUR";

    @RestClient
    MetalApiClient metalApiClient;

    @Override
    public Double convertGoldToEur(Double ounces) {
        return metalApiClient.getGoldPrice().rates().get(EUR_SYMBOL) * ounces;
    }
}
