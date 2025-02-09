package ch.fhnw.webec.wishlist;

import ch.fhnw.webec.wishlist.db.UserRepository;
import ch.fhnw.webec.wishlist.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Set;

import static java.util.Collections.emptySet;

@Component
public class InitialUsersAdder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitialUsersAdder.class);

    private final UserRepository userRepo;
    @Value("${wishlist.editor_password:#{null}}")
    private String editorPassword;
    @Value("${wishlist.user_password:#{null}}")
    private String userPassword;

    public InitialUsersAdder(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) {
        addInitialUsers();
    }

    public void addInitialUsers() {
        if (userRepo.findAll().isEmpty()) {
            logger.info("Adding initial users 'editor' and 'user'");
            var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

            addUser(encoder, "editor", editorPassword, Set.of("EDITOR"));
            addUser(encoder, "user", userPassword, emptySet());
        }
    }

    private void addUser(PasswordEncoder encoder, String username, String password, Set<String> roles) {
        if (password == null) {
            password = generatePassword();
            System.out.println("Password for '" + username + "': " + password);
        }
        var user = new User(username, encoder.encode(password), roles);
        userRepo.save(user);
    }

    private String generatePassword() {
        var random = new SecureRandom();
        return new BigInteger(128, random).toString(32);
    }

}
