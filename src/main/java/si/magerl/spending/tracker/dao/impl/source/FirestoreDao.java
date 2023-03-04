package si.magerl.spending.tracker.dao.impl.source;

import com.google.cloud.firestore.Firestore;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import si.magerl.spending.tracker.dao.query.impl.FirestoreQuery;

@Slf4j
@RequiredArgsConstructor
public class FirestoreDao<T> implements GenericSourceDao<FirestoreQuery, T> {

    final Firestore firestore;
    final String collection;
    final Class<T> type;

    @Override
    public Optional<T> get(String documentId) {
        try {
            return Optional.ofNullable(firestore
                    .collection(collection)
                    .document(documentId)
                    .get()
                    .get()
                    .toObject(type));
        } catch (InterruptedException | ExecutionException e) {
            log.error(
                    "Error while retrieving document from Firestore collection '{}' with id '{}'",
                    collection,
                    documentId);
            return Optional.empty();
        }
    }

    @Override
    public List<T> getAll() {
        try {
            return firestore.collection(collection).get().get().toObjects(type);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error while retrieving documents from Firestore collection '{}'", collection);
            return Collections.emptyList();
        }
    }

    @Override
    public void save(T t) {
        try {
            firestore.collection(collection).add(t).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error while saving document '{}' to Firestore collection '{}'", t, collection);
            throw new RuntimeException(e);
        }
    }

    public void saveWithId(String documentId, T entity) {
        try {
            firestore.collection(collection).document(documentId).set(entity).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error while saving document '{}' to Firestore collection '{}'", documentId, collection);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(T t, Map<String, Object> parameters) {
        throw new UnsupportedOperationException("Updating is not yet supported!");
    }

    @Override
    public void delete(String documentId) {
        try {
            firestore.collection(collection).document(documentId).delete().get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error while deleting document '{}' from Firestore collection '{}'", documentId, collection);
            throw new RuntimeException(e);
        }
    }

    public FirestoreQuery query() {
        return new FirestoreQuery(firestore.collection(collection));
    }

    public List<T> executeQuery(FirestoreQuery firestoreQuery) {
        try {
            return firestoreQuery.extractQuery().get().get().toObjects(type);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error while listing objects", e);
            return Collections.emptyList();
        }
    }
}
