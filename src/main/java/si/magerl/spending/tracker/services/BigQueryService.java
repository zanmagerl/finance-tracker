package si.magerl.spending.tracker.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.bigquery.*;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import si.magerl.spending.tracker.entitites.SpendingRecord;

@Slf4j
@ApplicationScoped
public class BigQueryService {

    @ConfigProperty(name = "google.dataset.name")
    String datasetName;

    @ConfigProperty(name = "google.table.name")
    String tableName;

    @Inject
    BigQuery bigQuery;

    TableId tableId;

    @PostConstruct
    void setUp() {
        tableId = TableId.of(datasetName, tableName);
    }

    public void insertRow(SpendingRecord spendingRecord) {
        InsertAllResponse response = bigQuery.insertAll(InsertAllRequest.newBuilder(tableId)
                .addRow(fromSpendingRecord(spendingRecord))
                .build());

        if (response.hasErrors()) {
            for (Map.Entry<Long, List<BigQueryError>> entry :
                    response.getInsertErrors().entrySet()) {
                log.error("Response error: {}", entry.getValue());
            }
        }
        log.info("Rows successfully inserted {} into database", spendingRecord);
    }

    private Map<String, Object> fromSpendingRecord(SpendingRecord spendingRecord) {
        return (Map<String, Object>) new ObjectMapper().convertValue(spendingRecord, Map.class);
    }
}
