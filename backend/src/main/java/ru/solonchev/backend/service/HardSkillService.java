package ru.solonchev.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solonchev.backend.dto.request.ChangeMarkSkillRequest;
import ru.solonchev.backend.dto.response.hard.*;
import ru.solonchev.backend.dto.response.mark.hard.HardSkillWithMarkDto;
import ru.solonchev.backend.dto.response.mark.hard.RoleWithSkillsMarksDto;
import ru.solonchev.backend.dto.response.mark.hard.UserHardSkillsMarksDto;
import ru.solonchev.backend.exception.hard.HardSkillMarkNotFoundException;
import ru.solonchev.backend.exception.hard.HardSkillNotFoundException;
import ru.solonchev.backend.exception.hard.RoleNotFoundException;
import ru.solonchev.backend.exception.user.UserNotFoundException;
import ru.solonchev.backend.model.hard.HardIndicator;
import ru.solonchev.backend.model.mark.HardSkillMark;
import ru.solonchev.backend.model.role.Role;
import ru.solonchev.backend.model.user.User;
import ru.solonchev.backend.repository.add.AddCompetenceRepository;
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


    public void changeMarkAtUser(int userId, ChangeMarkSkillRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        HardSkillMark hardSkillMark = hardSkillMarkRepository.findByHardSkillAndUser(
                hardSkillRepository.findById(request.skillId()).orElseThrow(HardSkillNotFoundException::new), user
        ).orElseThrow(HardSkillMarkNotFoundException::new);

        hardSkillMark.setMark(request.mark());
        hardSkillMarkRepository.save(hardSkillMark);
    }

    public UserHardSkillsMarksDto findHardSkillsWithMarksById(int id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
        List<HardSkillMark> hardSkillMarks = user.getHardSkillMarks();
        List<RoleWithSkillsMarksDto> hardMarks = new LinkedList<>();
        for (Role role : roleRepository.findAll()) {
            String roleName = role.getRoleName();
            hardMarks.add(
                    new RoleWithSkillsMarksDto(
                            roleName,
                            chooseByRoleName(hardSkillMarks, role)
                    )
            );
        }
        return new UserHardSkillsMarksDto(user.getId(), hardMarks);
    }

    private List<HardSkillWithMarkDto> chooseByRoleName(
            List<HardSkillMark> hardSkillMarks,
            Role role
    ) {
        return hardSkillMarks
                .stream()
                .filter(s -> s.getHardSkill().getRoles()
                        .stream().anyMatch(x -> x.getRoleName().equals(role.getRoleName())))
                .map(x -> new HardSkillWithMarkDto(
                        x.getHardSkill().getId(),
                        x.getHardSkill().getSkillName(),
                        x.getHardSkill().getGradeMethod(),
                        x.getHardSkill().getDevelopMethod(),
                        x.getMark(),
                        x.getHardSkill().getIndicators()
                                .stream()
                                .map(HardIndicator::getIndicatorName).toList()))
                .sorted((s1, s2) -> s2.mark() - s1.mark())
                .toList();
    }
}
