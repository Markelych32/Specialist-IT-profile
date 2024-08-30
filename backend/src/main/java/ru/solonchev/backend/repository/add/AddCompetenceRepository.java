package ru.solonchev.backend.repository.add;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.model.user.AddCompetence;
import ru.solonchev.backend.model.user.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface AddCompetenceRepository extends JpaRepository<AddCompetence, Integer> {
    List<AddCompetence> findByNameContainingIgnoreCase(String partName);

    Optional<AddCompetence> findByIdAndUser(int id, User user);

    Optional<AddCompetence> findByNameIgnoreCaseAndUser(String name, User user);
}
