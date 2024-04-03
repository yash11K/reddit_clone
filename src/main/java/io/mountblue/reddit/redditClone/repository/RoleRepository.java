package io.mountblue.reddit.redditClone.repository;

import io.mountblue.reddit.redditClone.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
