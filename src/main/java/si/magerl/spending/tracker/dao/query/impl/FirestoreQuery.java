package si.magerl.spending.tracker.dao.query.impl;

import com.google.cloud.firestore.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import si.magerl.spending.tracker.dao.query.TypelessQuery;

@Slf4j
@RequiredArgsConstructor
public class FirestoreQuery implements TypelessQuery<Query> {

    final com.google.cloud.firestore.Query query;

    @Override
    public <E> FirestoreQuery filterEq(String field, E value) {
        return new FirestoreQuery(query.whereEqualTo(field, value));
    }

    public FirestoreQuery limit(int limit) {
        return new FirestoreQuery(query.limit(limit));
    }

    @Override
    public com.google.cloud.firestore.Query extractQuery() {
        return query;
    }
}
