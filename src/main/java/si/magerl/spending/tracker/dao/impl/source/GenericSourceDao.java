package si.magerl.spending.tracker.dao.impl.source;

import java.util.List;
import si.magerl.spending.tracker.dao.GenericDao;
import si.magerl.spending.tracker.dao.query.GenericQuery;

public interface GenericSourceDao<Q extends GenericQuery, T> extends GenericDao<T> {

    void saveWithId(String id, T t);

    Q query();

    List<T> executeQuery(Q query);
}
