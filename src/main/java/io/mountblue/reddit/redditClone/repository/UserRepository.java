package io.mountblue.reddit.redditClone.repository;

import io.mountblue.reddit.redditClone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
    User findByUsername(String username);

    Optional<User> findUserByUsername(String username);
}
