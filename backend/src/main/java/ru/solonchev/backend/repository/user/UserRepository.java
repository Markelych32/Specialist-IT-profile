package ru.solonchev.backend.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.model.user.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    String FIND_ALL_USERS_BY_FULLNAME = """
            SELECT u FROM User u WHERE u.firstName LIKE %?1%
            OR u.lastName LIKE %?1%
            OR u.patronymic LIKE %?1%
            """;

    @Query(FIND_ALL_USERS_BY_FULLNAME)
    List<User> findByFullName(String fullName);
}
