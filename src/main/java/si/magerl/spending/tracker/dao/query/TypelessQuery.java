package si.magerl.spending.tracker.dao.query;

public interface TypelessQuery<T> extends GenericQuery<T> {

    <E> TypelessQuery<T> filterEq(String field, E t);
}
