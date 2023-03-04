package si.magerl.spending.tracker.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import java.util.function.Supplier;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;

public class CommonBeanConfiguration {

    @Singleton
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @ApplicationScoped
    public Supplier<String> idGenerator() {
        return () -> UUID.randomUUID().toString();
    }
}
