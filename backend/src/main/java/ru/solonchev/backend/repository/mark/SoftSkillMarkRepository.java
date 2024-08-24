package ru.solonchev.backend.repository.mark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.model.mark.SoftSkillMark;
import ru.solonchev.backend.model.soft.SoftSkill;

import java.util.Optional;

@Repository
public interface SoftSkillMarkRepository extends JpaRepository<SoftSkillMark, Integer> {
    Optional<SoftSkillMark> findBySoftSkill(SoftSkill softSkill);
}
