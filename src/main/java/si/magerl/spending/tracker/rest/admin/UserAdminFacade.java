package si.magerl.spending.tracker.rest.admin;

import static si.magerl.spending.tracker.authentication.Role.ADMIN_ROLE;

import com.google.firebase.auth.FirebaseAuthException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import si.magerl.spending.tracker.authentication.AuthenticationService;
import si.magerl.spending.tracker.authentication.User;

@Path("/admin/user")
@RolesAllowed(ADMIN_ROLE)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UserAdminFacade {

    private final AuthenticationService authenticationService;

    @GET
    public List<User> getUsers() throws FirebaseAuthException {
        return authenticationService.getUsers();
    }

    @PUT
    @Path("/promote/{uid}")
    public void promoteUser(@PathParam("uid") String uid) throws FirebaseAuthException {
        authenticationService.promoteUser(uid);
    }
}
