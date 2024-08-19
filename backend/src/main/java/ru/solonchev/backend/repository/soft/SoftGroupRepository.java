package ru.solonchev.backend.repository.soft;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.model.soft.SoftGroup;

import java.util.Optional;

@Repository
public interface SoftGroupRepository extends JpaRepository<SoftGroup, Integer> {
    Optional<SoftGroup> findSoftGroupByGroupName(String groupName);
}
