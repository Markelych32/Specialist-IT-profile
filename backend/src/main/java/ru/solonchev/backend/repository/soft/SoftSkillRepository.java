package ru.solonchev.backend.repository.soft;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.model.soft.SoftSkill;

@Repository
public interface SoftSkillRepository extends JpaRepository<SoftSkill, Integer> {
}
