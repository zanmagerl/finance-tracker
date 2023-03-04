package si.magerl.spending.tracker.authentication;

import com.google.firebase.auth.FirebaseToken;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
@PreMatching
public class AuthorizationFilter implements ContainerRequestFilter {

    @Inject
    AuthenticationService authenticationService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        Optional<String> bearerToken =
                Optional.ofNullable(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION));
        if (bearerToken.isEmpty()) {
            containerRequestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        try {
            log.info(bearerToken.get());

            FirebaseToken token =
                    authenticationService.verifyUser(bearerToken.get().split(" ")[1]);
            User user = new User(token.getName(), token.getEmail(), token.getUid());
            Set<String> userClaims = authenticationService.getUserClaims(user.uid);
            log.info("[SECURITY CONTEXT] email: {}, claims: {}", user.getEmail(), userClaims);
            containerRequestContext.setSecurityContext(new UserSecurityContext(user, userClaims));
        } catch (Exception firebaseAuthException) {
            containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity(firebaseAuthException.getMessage())
                    .build());
        }
    }
}
