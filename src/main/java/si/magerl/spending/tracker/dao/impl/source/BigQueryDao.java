package si.magerl.spending.tracker.dao.impl.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.bigquery.*;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import si.magerl.spending.tracker.dao.query.impl.BigQueryQuery;

@Slf4j
@RequiredArgsConstructor
public class BigQueryDao<T> implements GenericSourceDao<BigQueryQuery, T> {

    private static final int MAX_RESULT_LIMIT = 1000;

    final BigQuery bigQuery;

    final TableId tableId;

    final ObjectMapper objectMapper;

    final Class<T> type;

    @Override
    public Optional<T> get(String id) {
        return Optional.empty();
    }

    @Override
    public List<T> getAll() {
        TableResult result = bigQuery.listTableData(tableId, BigQuery.TableDataListOption.pageSize(MAX_RESULT_LIMIT));
        return extractResults(result);
    }

    @Override
    public void save(T t) {
        Map<String, Object> row = fromEntity(t);
        log.info("Record: {}", row);
        InsertAllResponse response = bigQuery.insertAll(
                InsertAllRequest.newBuilder(tableId).addRow(row).build());

        if (response.hasErrors()) {
            for (Map.Entry<Long, List<BigQueryError>> entry :
                    response.getInsertErrors().entrySet()) {
                log.error("Response error: {}", entry.getValue());
            }
            return;
        }
        log.info("Rows successfully inserted {} into database", row);
    }

    @Override
    public void update(T t, Map<String, Object> parameters) {}

    @Override
    public void delete(String id) {}

    @Override
    public void saveWithId(String id, T t) {
        throw new UnsupportedOperationException("BigQuery does not support inserting by id");
    }

    public BigQueryQuery query() {
        return new BigQueryQuery(tableId, List.of());
    }

    public List<T> executeQuery(BigQueryQuery query) {
        try {
            TableResult result = bigQuery.query(query.extractQuery());
            return extractResults(result);
        } catch (InterruptedException e) {
            log.error("Error while executing query '{}'", query);
            return Collections.emptyList();
        }
    }

    private List<T> extractResults(TableResult result) {
        List<T> fetchedRows = new LinkedList<>();
        Schema schema = result.getSchema();
        result.iterateAll().forEach(row -> {
            Map<String, Object> propertyMap = new HashMap<>();
            schema.getFields().forEach(field -> {
                if (field.getType() == LegacySQLTypeName.TIMESTAMP) {
                    propertyMap.put(field.getName(), row.get(field.getName()).getTimestampValue() / 1000_000L);
                } else {
                    propertyMap.put(field.getName(), row.get(field.getName()).getValue());
                }
            });
            log.info("Property map: {}", propertyMap);
            fetchedRows.add(toEntity(propertyMap));
        });
        return fetchedRows;
    }

    private Map<String, Object> fromEntity(T t) {
        return objectMapper.convertValue(t, Map.class);
    }

    private T toEntity(Map<String, Object> map) {
        return objectMapper.convertValue(map, type);
    }
}
