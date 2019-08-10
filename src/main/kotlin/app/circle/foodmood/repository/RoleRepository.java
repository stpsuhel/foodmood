package app.circle.foodmood.repository;


import app.circle.foodmood.security.Role;
import app.circle.foodmood.security.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);

    ArrayList<Role> getAllByNameInOrderByNameAsc(ArrayList<RoleName> roleNames);
    ArrayList<Role> getAllByIdIn(ArrayList<Long> ids);



}
