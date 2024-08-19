package ru.solonchev.backend.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
