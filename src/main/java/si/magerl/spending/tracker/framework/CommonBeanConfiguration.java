package si.magerl.spending.tracker.framework;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.gson.Gson;
import javax.inject.Singleton;

public class CommonBeanConfiguration {

    @Singleton
    public BigQuery bigQuery() {
        return BigQueryOptions.getDefaultInstance().getService();
    }

    @Singleton
    public Gson gson() {
        return new Gson();
    }
}
