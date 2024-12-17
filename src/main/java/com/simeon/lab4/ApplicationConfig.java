package com.simeon.lab4;

import com.simeon.lab4.entities.Group;
import com.simeon.lab4.entities.User;
import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.math.BigInteger;
import java.util.Map;

import static java.math.BigInteger.ONE;

//@ApplicationScoped
//@BasicAuthenticationMechanismDefinition(
//        realmName = "basicAuth"
//)
//@DatabaseIdentityStoreDefinition(
//        callerQuery = "select password from basic_auth_user where username = ?",
//        groupsQuery = "select name from basic_auth_group where username = ?",
//        hashAlgorithmParameters = {
//                "Pbkdf2PasswordHash.Iterations=3072",
//                "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512",
//                "Pbkdf2PasswordHash.SaltSizeBytes=64"
//        }
//)
//@DeclareRoles("user")
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    /**
     * Id of the one and only user we populate in out DB.
     */
    private static final BigInteger USER_ID = ONE;

    /**
     * Id of the one and only group we populate in out DB.
     */
    private static final BigInteger GROUP_ID = ONE;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @Transactional
    public void onStart(@Observes @Initialized(ApplicationScoped.class) Object applicationContext) {
        passwordHash.initialize(Map.of(
                "Pbkdf2PasswordHash.Iterations", "3072",
                "Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512",
                "Pbkdf2PasswordHash.SaltSizeBytes", "64"));

        if (entityManager.find(User.class, USER_ID) == null) {
            var user = new User();
            user.setId(USER_ID);
            user.setUsername("john");
            user.setPassword(passwordHash.generate("secret1".toCharArray()));
            entityManager.persist(user);
        }

        if (entityManager.find(Group.class, GROUP_ID) == null) {
            var group = new Group();
            group.setId(GROUP_ID);
            group.setName("user");
            group.setUsername("john");
            entityManager.persist(group);
        }
    }

}