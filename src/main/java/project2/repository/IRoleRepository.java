package project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project2.model.Role;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select * from role where role.name_role = ?1" , nativeQuery = true)
    Role findByName(String role);

    @Query(value = "select * from role where name_role = ?1",nativeQuery = true)
    Role findByNameRole(String roleName);
}
