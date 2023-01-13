package si.magerl.spending.tracker.function;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import java.io.Writer;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named("spending-tracker")
@ApplicationScoped
public class SpendingTrackerFunction implements HttpFunction {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        Writer writer = httpResponse.getWriter();
        writer.write("Hello World!");
    }
}
