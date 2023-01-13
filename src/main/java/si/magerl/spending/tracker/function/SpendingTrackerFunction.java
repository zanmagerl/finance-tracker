package si.magerl.spending.tracker.function;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import java.io.Writer;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import si.magerl.spending.tracker.entitites.SpendingRecord;
import si.magerl.spending.tracker.services.SpendingRecordProcessingService;

@Named("spending-tracker")
@ApplicationScoped
public class SpendingTrackerFunction implements HttpFunction {

    @Inject
    SpendingRecordProcessingService spendingRecordProcessingService;

    @Inject
    Gson gson;

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        Writer writer = httpResponse.getWriter();
        SpendingRecord body = gson.fromJson(httpRequest.getReader(), SpendingRecord.class);
        spendingRecordProcessingService.processSpendingRecord(body);
        writer.write("Hello World!");
    }
}
