package si.magerl.spending.tracker.dao.query.impl;

import static si.magerl.spending.tracker.dao.common.BigQueryConstants.*;

import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryParameterValue;
import com.google.cloud.bigquery.TableId;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import si.magerl.spending.tracker.dao.query.TypedQuery;
import si.magerl.spending.tracker.dao.util.ListUtil;

@Slf4j
@RequiredArgsConstructor
public class BigQueryQuery implements TypedQuery<QueryJobConfiguration> {

    final TableId tableId;

    final List<Filter> filters;

    @Override
    public <E> BigQueryQuery filterEq(String field, E t, Class<E> type) {
        return new BigQueryQuery(
                tableId,
                ListUtil.append(
                        filters,
                        Filter.builder()
                                .field(field)
                                .operation(Operation.EQUALS)
                                .queryParameterValue(QueryParameterValue.of(t, type))
                                .build()));
    }

    public QueryJobConfiguration extractQuery() {
        String query = "%s * %s %s.%s.%s "
                .formatted(SELECT, FROM, tableId.getProject(), tableId.getDataset(), tableId.getTable());
        if (!filters.isEmpty()) {
            query += "%s ".formatted(WHERE);
        }
        query += ListUtil.join(filters, " %s ".formatted(AND));
        var queryJobConfiguration = QueryJobConfiguration.of(query);
        return queryJobConfiguration.toBuilder()
                .setQuery(query)
                .setNamedParameters(
                        filters.stream().collect(Collectors.toMap(Filter::getField, Filter::getQueryParameterValue)))
                .build();
    }

    @Value
    @Builder
    private static class Filter {
        String field;
        Operation operation;
        QueryParameterValue queryParameterValue;

        @Override
        public String toString() {
            return "%s = @%s".formatted(field, field);
        }
    }

    @AllArgsConstructor
    private enum Operation {
        EQUALS("="),
        ;

        private String symbol;
    }
}
