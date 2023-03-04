package si.magerl.spending.tracker.rest.client;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import lombok.RequiredArgsConstructor;
import si.magerl.spending.tracker.authentication.UserSecurityContext;
import si.magerl.spending.tracker.dao.CategoryDao;
import si.magerl.spending.tracker.entitites.Category;
import si.magerl.spending.tracker.rest.dto.CategoryDTO;
import si.magerl.spending.tracker.rest.dto.mappers.CategoryMapper;

@Path("/category")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CategoryFacade {

    final CategoryDao categoryDao;
    final CategoryMapper categoryMapper;

    @GET
    public List<CategoryDTO> getUsersCategories(@Context SecurityContext securityContext) {
        List<Category> categories =
                categoryDao.getUsersCategories(((UserSecurityContext) securityContext).getUserPrincipal());
        return categories.stream().map(categoryMapper::toDTO).toList();
    }
}
