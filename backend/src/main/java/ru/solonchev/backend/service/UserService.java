package ru.solonchev.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solonchev.backend.dto.mark.SoftGroupWithSkillsMarksDto;
import ru.solonchev.backend.dto.mark.SoftSkillWithMarkDto;
import ru.solonchev.backend.dto.mark.UserSoftSkillsMarksDto;
import ru.solonchev.backend.dto.user.GeneralUserInfoDto;
import ru.solonchev.backend.dto.user.UserJobInfoDto;
import ru.solonchev.backend.model.mark.SoftSkillMark;
import ru.solonchev.backend.model.soft.SoftGroup;
import ru.solonchev.backend.model.soft.SoftSkill;
import ru.solonchev.backend.model.user.User;
import ru.solonchev.backend.repository.mark.HardSkillMarkRepository;
import ru.solonchev.backend.repository.mark.SoftSkillMarkRepository;
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
    private final SoftSkillMarkRepository softSkillMarkRepository;
    private final HardSkillMarkRepository hardSkillMarkRepository;

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


    private List<SoftSkillWithMarkDto> chooseBySoftGroupName(
            List<SoftSkillMark> softSkillMarks,
            SoftGroup softGroup
    ) {
        return softSkillMarks
                .stream()
                .filter(s -> s.getSoftSkill().getSoftGroup().getGroupName().equals(softGroup.getGroupName()))
                .map(x -> new SoftSkillWithMarkDto(x.getSoftSkill().getSkillName(), x.getMark()))
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
