package si.magerl.spending.tracker.authentication;

import java.util.Set;
import javax.ws.rs.core.SecurityContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserSecurityContext implements SecurityContext {
    User user;
    Set<String> claims;

    @Override
    public User getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String role) {
        return claims.contains(role);
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
