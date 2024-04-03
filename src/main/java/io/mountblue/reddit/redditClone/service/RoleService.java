package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.model.Role;

public interface RoleService {
    Role findRoleById(Long l);

    void save(Role role);
}
