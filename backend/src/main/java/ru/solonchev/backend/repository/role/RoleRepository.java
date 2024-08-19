package ru.solonchev.backend.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.model.role.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
