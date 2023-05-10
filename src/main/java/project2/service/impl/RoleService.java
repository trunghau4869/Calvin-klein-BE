package project2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project2.model.Account;
import project2.model.Role;
import project2.repository.IRoleRepository;
import project2.service.IRoleService;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository iRoleRepository;

    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public Iterable<Role> saveAll(Iterable<Role> roles) {
        return null;
    }

    @Override
    public Role findById(Long id) {
        return iRoleRepository.findById(id).orElse(null);
    }


    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public Role findByName(String nameRole) {
        return iRoleRepository.findByNameRole(nameRole);
    }


    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delele(Role role) {

    }
}
