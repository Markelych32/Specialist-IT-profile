package ru.solonchev.backend.repository.mark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.model.mark.HardSkillMark;

@Repository
public interface HardSkillMarkRepository extends JpaRepository<HardSkillMark, Integer> {
}
