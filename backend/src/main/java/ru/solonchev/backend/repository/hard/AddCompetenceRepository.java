package ru.solonchev.backend.repository.hard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.dto.response.hard.AddCompetenceDto;
import ru.solonchev.backend.model.user.AddCompetence;

@Repository
public interface AddCompetenceRepository extends JpaRepository<AddCompetence, Integer> {
}
