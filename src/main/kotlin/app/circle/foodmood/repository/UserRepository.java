package app.circle.foodmood.repository;

import app.circle.foodmood.security.User;
import app.circle.foodmood.utils.PrimaryRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User getByUsername(String username);

    ArrayList<User> getAllByCompanyId(Long companyId);

    ArrayList<User> getAllByCompanyIdAndPrimaryRole(Long companyId, PrimaryRole primaryRole);
}
