package si.magerl.spending.tracker.authentication;

import static si.magerl.spending.tracker.authentication.Role.ADMIN_ROLE;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class AuthenticationService {

    final FirebaseAuth firebaseAuth;

    public FirebaseToken verifyUser(String token) throws FirebaseAuthException {
        return firebaseAuth.verifyIdToken(token);
    }

    public User getUserByEmail(String email) throws FirebaseAuthException {
        UserRecord user = firebaseAuth.getUserByEmail(email);
        return User.builder()
                .email(user.getEmail())
                .name(user.getDisplayName())
                .uid(user.getUid())
                .build();
    }

    public Set<String> getUserClaims(String uid) throws FirebaseAuthException {
        return firebaseAuth.getUser(uid).getCustomClaims().keySet();
    }

    public List<User> getUsers() throws FirebaseAuthException {
        List<User> users = new LinkedList<>();
        firebaseAuth.listUsers(null).getValues().iterator().forEachRemaining(this::fromUserRecordToUser);
        return users;
    }

    public void promoteUser(String uid) throws FirebaseAuthException {
        firebaseAuth.setCustomUserClaims(uid, Map.of(ADMIN_ROLE, true));
    }

    private User fromUserRecordToUser(UserRecord userRecord) {
        return User.builder()
                .uid(userRecord.getUid())
                .name(userRecord.getDisplayName())
                .email(userRecord.getEmail())
                .build();
    }
}
