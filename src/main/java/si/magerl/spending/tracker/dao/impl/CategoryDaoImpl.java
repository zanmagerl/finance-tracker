package si.magerl.spending.tracker.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import si.magerl.spending.tracker.authentication.User;
import si.magerl.spending.tracker.dao.CategoryDao;
import si.magerl.spending.tracker.dao.impl.source.FirestoreDao;
import si.magerl.spending.tracker.entitites.Category;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CategoryDaoImpl implements CategoryDao {

    private final FirestoreDao<Category> firestoreDao;

    private final Supplier<String> idGenerator;

    @Override
    public List<Category> getUsersCategories(User user) {
        return firestoreDao.executeQuery(firestoreDao.query().filterEq("creatorUid", user.getUid()));
    }

    @Override
    public Category createNewUserCategory(User user, String name) {
        Category category = Category.builder()
                .id(idGenerator.get())
                .creatorUid(user.getUid())
                .name(name)
                .build();
        firestoreDao.saveWithId(category.getId(), category);
        return category;
    }

    @Override
    public List<Category> getUserCategoriesByName(User user, String name) {
        return firestoreDao.executeQuery(
                firestoreDao.query().filterEq("creatorUid", user.getUid()).filterEq("name", name));
    }

    @Override
    public Optional<Category> get(String id) {
        return firestoreDao.get(id);
    }

    @Override
    public List<Category> getAll() {
        return firestoreDao.getAll();
    }

    @Override
    public void save(Category category) {
        firestoreDao.save(category);
    }

    @Override
    public void update(Category category, Map<String, Object> parameters) {
        firestoreDao.update(category, parameters);
    }

    @Override
    public void delete(String id) {
        firestoreDao.delete(id);
    }
}
