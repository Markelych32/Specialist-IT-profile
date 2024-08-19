package ru.solonchev.backend.repository.hard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.model.hard.HardIndicator;

@Repository
public interface HardIndicatorRepository extends JpaRepository<HardIndicator, Integer> {
}
