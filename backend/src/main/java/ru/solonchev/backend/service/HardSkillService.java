package ru.solonchev.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solonchev.backend.dto.request.AppendAddCompetenceRequestDto;
import ru.solonchev.backend.dto.request.ChangeMarkSkillRequest;
import ru.solonchev.backend.dto.response.hard.*;
import ru.solonchev.backend.exception.HardSkillMarkNotFoundException;
import ru.solonchev.backend.exception.HardSkillNotFoundException;
import ru.solonchev.backend.exception.RoleNotFoundException;
import ru.solonchev.backend.exception.UserNotFoundException;
import ru.solonchev.backend.model.hard.HardIndicator;
import ru.solonchev.backend.model.mark.HardSkillMark;
import ru.solonchev.backend.model.role.Role;
import ru.solonchev.backend.model.user.AddCompetence;
import ru.solonchev.backend.model.user.User;
import ru.solonchev.backend.repository.hard.AddCompetenceRepository;
import ru.solonchev.backend.repository.hard.HardSkillRepository;
import ru.solonchev.backend.repository.mark.HardSkillMarkRepository;
import ru.solonchev.backend.repository.role.RoleRepository;
import ru.solonchev.backend.repository.user.UserRepository;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HardSkillService {

    private final RoleRepository roleRepository;
    private final AddCompetenceRepository addCompetenceRepository;
    private final UserRepository userRepository;
    private final HardSkillMarkRepository hardSkillMarkRepository;
    private final HardSkillRepository hardSkillRepository;


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
        Role role = roleRepository.findRoleByRoleName(roleName).orElseThrow(RoleNotFoundException::new);
        List<HardSkillDto> hardSkillDtos = new LinkedList<>();
        role.getHardSkills().forEach((s) -> hardSkillDtos
                .add(new HardSkillDto(s.getSkillName(),
                        s.getIndicators()
                                .stream()
                                .map(HardIndicator::getIndicatorName).toList())));
        return new RoleWithHardSkillsDto(roleName, hardSkillDtos);
    }

    public List<AddCompetenceDto> findAllAddCompetence() {
        return addCompetenceRepository
                .findAll()
                .stream()
                .map(c -> new AddCompetenceDto(
                        c.getId(),
                        c.getName(),
                        c.getRole().getRoleName()
                )).toList();
    }

    public List<SuitableRoleDto> findSuitableRolesForAddCompetency(String partName) {
        return addCompetenceRepository
                .findByNameContainingIgnoreCase(partName)
                .stream()
                .map(c -> new SuitableRoleDto(
                        c.getId(),
                        c.getName(),
                        c.getRole().getRoleName()
                )).toList();
    }

    public void addNewSkillToUser(
            int userId,
            AppendAddCompetenceRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        AddCompetence newAddCompetence = AddCompetence.builder()
                .name(requestDto.name())
                .user(user)
                .role(roleRepository.findById(requestDto.roleId()).orElseThrow((() -> new RuntimeException("Role not found"))))
                .build();
        user.getAddCompetences().add(newAddCompetence);
        userRepository.save(user);
    }

    public void changeMarkAtUser(int userId, ChangeMarkSkillRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        HardSkillMark hardSkillMark = hardSkillMarkRepository.findByHardSkillAndUser(
                hardSkillRepository.findById(request.skillId()).orElseThrow(HardSkillNotFoundException::new), user
        ).orElseThrow(HardSkillMarkNotFoundException::new);

        hardSkillMark.setMark(request.mark());
        hardSkillMarkRepository.save(hardSkillMark);
    }
}
