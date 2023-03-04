package si.magerl.spending.tracker.dao.query;

public interface TypedQuery<T> extends GenericQuery<T> {

    <E> TypedQuery<T> filterEq(String field, E t, Class<E> type);
}
