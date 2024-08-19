package ru.solonchev.backend.repository.hard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.model.hard.Indicator;

@Repository
public interface IndicatorRepository extends JpaRepository<Indicator, Integer> {
}
