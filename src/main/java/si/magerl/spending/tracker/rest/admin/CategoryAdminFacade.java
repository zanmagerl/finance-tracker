package si.magerl.spending.tracker.rest.admin;

import static si.magerl.spending.tracker.authentication.Role.ADMIN_ROLE;

import com.google.firebase.auth.FirebaseAuthException;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import si.magerl.spending.tracker.dao.CategoryDao;
import si.magerl.spending.tracker.entitites.Category;
import si.magerl.spending.tracker.rest.dto.CategoryDTO;
import si.magerl.spending.tracker.rest.dto.mappers.CategoryMapper;
import si.magerl.spending.tracker.services.CategoryAdminService;

@RolesAllowed(ADMIN_ROLE)
@Path("/admin/category")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CategoryAdminFacade {

    final CategoryDao categoryDao;
    final CategoryAdminService categoryAdminService;
    final CategoryMapper categoryMapper;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory(@PathParam("id") String id) {
        Optional<Category> optionalCategory = categoryDao.get(id);
        if (optionalCategory.isPresent()) {
            return Response.ok().entity(optionalCategory.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveCategory(CategoryDTO categoryDTO) {
        categoryDao.save(categoryMapper.fromDTO(categoryDTO));
    }

    @DELETE
    @Path("/id")
    public void deleteCategoryWithId(@PathParam("id") String id) {
        categoryDao.delete(id);
    }

    @DELETE
    @Path("/user/{email}/{name}")
    public void deleteUsersCategory(@PathParam("email") String email, @PathParam("name") String name)
            throws FirebaseAuthException {
        categoryAdminService.deleteUserCategoryByName(email, name);
    }
}
