package si.magerl.spending.tracker.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Common generic DAO interface that defines list of requirements that needs to implemented by various DAO's
 */
public interface GenericDao<T> {

    /**
     * Retrieves an element with the given id.
     */
    Optional<T> get(String id);

    List<T> getAll();

    void save(T t);

    void update(T t, Map<String, Object> parameters);

    void delete(String id);
}
