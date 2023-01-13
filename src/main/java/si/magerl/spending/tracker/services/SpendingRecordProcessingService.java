package si.magerl.spending.tracker.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import si.magerl.spending.tracker.entitites.SpendingRecord;

@Slf4j
@ApplicationScoped
public class SpendingRecordProcessingService {

    @Inject
    BigQueryService bigQueryService;

    public void processSpendingRecord(SpendingRecord spendingRecord) {
        bigQueryService.insertRow(spendingRecord);
    }
}
