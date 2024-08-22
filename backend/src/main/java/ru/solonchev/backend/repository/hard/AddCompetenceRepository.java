package ru.solonchev.backend.repository.hard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.model.user.AddCompetence;

import java.util.List;


@Repository
public interface AddCompetenceRepository extends JpaRepository<AddCompetence, Integer> {
    List<AddCompetence> findByNameContainingIgnoreCase(String partName);
}
