package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.model.Role;
import io.mountblue.reddit.redditClone.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleManager implements RoleService{

    private RoleRepository roleRepository;

    @Override
    public Role findRoleById(Long l) {
        Role role = roleRepository.findById(l).orElseThrow();
        System.out.println(role);
        return role;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }
}
