package si.magerl.spending.tracker.dao.common;

import static si.magerl.spending.tracker.dao.common.FirestoreConstants.CATEGORY_COLLECTION;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.TableId;
import com.google.cloud.firestore.Firestore;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import si.magerl.spending.tracker.dao.impl.source.BigQueryDao;
import si.magerl.spending.tracker.dao.impl.source.FirestoreDao;
import si.magerl.spending.tracker.entitites.Category;
import si.magerl.spending.tracker.entitites.Expense;

public class GenericBeanConfiguration {

    @ApplicationScoped
    public BigQueryDao<Expense> spendingRecordBigQueryDao(
            BigQuery bigQuery,
            @ConfigProperty(name = "spending.gcp.project-id") String projectId,
            @ConfigProperty(name = "spending.bigquery.dataset.name") String datasetName,
            @ConfigProperty(name = "spending.bigquery.table.name") String tableName,
            ObjectMapper objectMapper) {
        return new BigQueryDao(bigQuery, TableId.of(projectId, datasetName, tableName), objectMapper, Expense.class);
    }

    @ApplicationScoped
    public FirestoreDao<Category> firestoreCategoryDao(Firestore firestore) {
        return new FirestoreDao(firestore, CATEGORY_COLLECTION, Category.class);
    }
}
