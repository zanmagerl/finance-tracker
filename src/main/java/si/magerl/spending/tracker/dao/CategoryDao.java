package si.magerl.spending.tracker.dao;

import java.util.List;
import si.magerl.spending.tracker.authentication.User;
import si.magerl.spending.tracker.entitites.Category;

public interface CategoryDao extends GenericDao<Category> {

    List<Category> getUsersCategories(User user);

    Category createNewUserCategory(User user, String name);

    List<Category> getUserCategoriesByName(User user, String name);
}
