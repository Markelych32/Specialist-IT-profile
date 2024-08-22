package ru.solonchev.backend.repository.mark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.model.hard.HardSkill;
import ru.solonchev.backend.model.mark.HardSkillMark;

import java.util.Optional;

@Repository
public interface HardSkillMarkRepository extends JpaRepository<HardSkillMark, Integer> {
    Optional<HardSkillMark> findByHardSkill(HardSkill hardSkill);
}
