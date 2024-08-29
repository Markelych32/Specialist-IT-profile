package ru.solonchev.backend.repository.hard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.model.hard.HardSkill;

import java.util.Optional;

@Repository
public interface HardSkillRepository extends JpaRepository<HardSkill, Integer> {
    Optional<HardSkill> findBySkillNameIgnoreCase(String skillName);
}
