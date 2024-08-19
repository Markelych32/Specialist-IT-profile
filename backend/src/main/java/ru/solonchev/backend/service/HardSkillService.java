package ru.solonchev.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solonchev.backend.dto.hard.HardSkillDto;
import ru.solonchev.backend.dto.hard.RoleWithHardSkillsDto;
import ru.solonchev.backend.dto.hard.RolesDto;
import ru.solonchev.backend.model.hard.HardIndicator;
import ru.solonchev.backend.model.role.Role;
import ru.solonchev.backend.repository.hard.HardIndicatorRepository;
import ru.solonchev.backend.repository.hard.HardSkillRepository;
import ru.solonchev.backend.repository.role.RoleRepository;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HardSkillService {
    private final HardSkillRepository hardSkillRepository;
    private final RoleRepository roleRepository;
    private final HardIndicatorRepository hardIndicatorRepository;

    public RolesDto findAllRoles() {
        return new RolesDto(
                roleRepository
                        .findAll()
                        .stream()
                        .map(Role::getRoleName)
                        .toList()
        );
    }

    public RoleWithHardSkillsDto findHardSkillsByRole(String roleName) {
        Role role = roleRepository.findRoleByRoleName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
        List<HardSkillDto> hardSkillDtos = new LinkedList<>();
        role.getHardSkills().forEach((s) -> hardSkillDtos
                .add(new HardSkillDto(s.getSkillName(),
                        s.getIndicators()
                                .stream()
                                .map(HardIndicator::getIndicatorName).toList())));
        return new RoleWithHardSkillsDto(roleName, hardSkillDtos);
    }
}
