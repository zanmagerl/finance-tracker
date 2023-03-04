package si.magerl.spending.tracker.services;

import com.google.firebase.auth.FirebaseAuthException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import si.magerl.spending.tracker.authentication.AuthenticationService;
import si.magerl.spending.tracker.authentication.User;
import si.magerl.spending.tracker.dao.CategoryDao;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CategoryAdminService {

    final AuthenticationService authenticationService;
    final CategoryDao categoryDao;

    public void deleteUserCategoryByName(String email, String name) throws FirebaseAuthException {
        User user = authenticationService.getUserByEmail(email);
        categoryDao.getUserCategoriesByName(user, name).forEach(category -> categoryDao.delete(category.getId()));
    }
}
