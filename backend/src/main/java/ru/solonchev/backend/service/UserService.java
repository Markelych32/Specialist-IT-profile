package ru.solonchev.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solonchev.backend.dto.mark.add.AddCompetenceDto;
import ru.solonchev.backend.dto.mark.add.UserAddCompetencesWithMarksDto;
import ru.solonchev.backend.dto.mark.hard.HardSkillWithMarkDto;
import ru.solonchev.backend.dto.mark.hard.RoleWithSkillsMarksDto;
import ru.solonchev.backend.dto.mark.hard.UserHardSkillsMarksDto;
import ru.solonchev.backend.dto.mark.soft.SoftGroupWithSkillsMarksDto;
import ru.solonchev.backend.dto.mark.soft.SoftSkillWithMarkDto;
import ru.solonchev.backend.dto.mark.soft.UserSoftSkillsMarksDto;
import ru.solonchev.backend.dto.user.GeneralUserInfoDto;
import ru.solonchev.backend.dto.user.UserDto;
import ru.solonchev.backend.dto.user.UserJobInfoDto;
import ru.solonchev.backend.model.hard.HardIndicator;
import ru.solonchev.backend.model.mark.HardSkillMark;
import ru.solonchev.backend.model.mark.SoftSkillMark;
import ru.solonchev.backend.model.role.Role;
import ru.solonchev.backend.model.soft.SoftGroup;
import ru.solonchev.backend.model.user.User;
import ru.solonchev.backend.repository.role.RoleRepository;
import ru.solonchev.backend.repository.soft.SoftGroupRepository;
import ru.solonchev.backend.repository.user.UserRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SoftGroupRepository softGroupRepository;
    private final RoleRepository roleRepository;

    public GeneralUserInfoDto findGeneralInfoById(int id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        int age = calculateAge(user.getDateOfBirth());
        String ageString = getAgeString(age);
        return new GeneralUserInfoDto(
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth(),
                age + " " + ageString,
                user.getGender(),
                user.getLocation()
        );
    }

    public UserJobInfoDto findJobInfoById(int id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserJobInfoDto(
                user.getPost().getPostName(),
                user.getRole().getRoleName(),
                user.getSpecialization()
        );
    }

    public UserSoftSkillsMarksDto findSoftSkillsWithMarksById(int id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<SoftSkillMark> softSkillMarks = user.getSoftSkillMarks();
        List<SoftGroupWithSkillsMarksDto> softMarks = new LinkedList<>();
        for (SoftGroup softGroup : softGroupRepository.findAll()) {
            String softGroupName = softGroup.getGroupName();
            softMarks.add(
                    new SoftGroupWithSkillsMarksDto(
                            softGroupName,
                            chooseBySoftGroupName(softSkillMarks, softGroup)
                    )
            );
        }
        return new UserSoftSkillsMarksDto(user.getId(), softMarks);
    }

    public UserHardSkillsMarksDto findHardSkillsWithMarksById(int id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
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

    public List<UserDto> findAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPatronymic(),
                        user.getDateOfBirth(),
                        user.getGender(),
                        user.getLocation(),
                        user.getPost().getPostName(),
                        user.getRole().getRoleName(),
                        user.getSpecialization()
                ))
                .toList();
    }

    public UserAddCompetencesWithMarksDto getAddCompetencesOfUserById(int id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserAddCompetencesWithMarksDto(
                user.getId(),
                user.getAddCompetences()
                        .stream()
                        .map(addCompetence -> new AddCompetenceDto(
                                addCompetence.getId(),
                                addCompetence.getName(),
                                addCompetence.getRole().getRoleName(),
                                addCompetence.getMark()
                        ))
                        .sorted((s1, s2) -> s2.mark() - s1.mark())
                        .toList()
        );
    }


    private List<SoftSkillWithMarkDto> chooseBySoftGroupName(
            List<SoftSkillMark> softSkillMarks,
            SoftGroup softGroup
    ) {
        return softSkillMarks
                .stream()
                .filter(s -> s.getSoftSkill()
                        .getSoftGroup()
                        .getGroupName()
                        .equals(softGroup.getGroupName()))
                .map(x -> new SoftSkillWithMarkDto(
                        x.getSoftSkill().getId(),
                        x.getSoftSkill().getSkillName(),
                        x.getMark()))
                .sorted((s1, s2) -> s2.mark() - s1.mark())
                .toList();

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


    private int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    private String getAgeString(int age) {
        if (age % 10 == 1 && age % 100 != 11) {
            return "год";
        } else if ((age % 10 >= 2 && age % 10 <= 4) && (age % 100 < 10 || age % 100 >= 20)) {
            return "года";
        } else {
            return "лет";
        }
    }
}
