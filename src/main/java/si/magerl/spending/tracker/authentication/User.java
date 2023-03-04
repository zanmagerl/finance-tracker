package si.magerl.spending.tracker.authentication;

import java.security.Principal;
import javax.security.auth.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
public class User implements Principal {

    String name;

    @Getter
    String email;

    @Getter
    String uid;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
