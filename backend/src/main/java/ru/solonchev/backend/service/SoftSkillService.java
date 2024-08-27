package ru.solonchev.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solonchev.backend.dto.request.ChangeMarkSkillRequest;
import ru.solonchev.backend.dto.response.mark.soft.SoftGroupWithSkillsMarksDto;
import ru.solonchev.backend.dto.response.mark.soft.SoftSkillWithMarkDto;
import ru.solonchev.backend.dto.response.mark.soft.UserSoftSkillsMarksDto;
import ru.solonchev.backend.dto.response.soft.SoftGroupWithSkillsDto;
import ru.solonchev.backend.dto.response.soft.SoftGroupsDto;
import ru.solonchev.backend.exception.soft.SoftGroupNotFoundException;
import ru.solonchev.backend.exception.soft.SoftSkillMarkNotFoundException;
import ru.solonchev.backend.exception.soft.SoftSkillNotFoundException;
import ru.solonchev.backend.exception.user.UserNotFoundException;
import ru.solonchev.backend.model.mark.SoftSkillMark;
import ru.solonchev.backend.model.soft.SoftGroup;
import ru.solonchev.backend.model.soft.SoftSkill;
import ru.solonchev.backend.model.user.User;
import ru.solonchev.backend.repository.mark.SoftSkillMarkRepository;
import ru.solonchev.backend.repository.soft.SoftGroupRepository;
import ru.solonchev.backend.repository.soft.SoftSkillRepository;
import ru.solonchev.backend.repository.user.UserRepository;


import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SoftSkillService {

    private final SoftGroupRepository softGroupRepository;
    private final UserRepository userRepository;
    private final SoftSkillMarkRepository softSkillMarkRepository;
    private final SoftSkillRepository softSkillRepository;


    public SoftGroupsDto findAllSoftGroups() {
        return new SoftGroupsDto(
                softGroupRepository
                        .findAll()
                        .stream()
                        .map(SoftGroup::getGroupName)
                        .toList()
        );
    }

    public SoftGroupWithSkillsDto findSoftSkillsByGroupName(String groupName) {
        SoftGroup softGroup = softGroupRepository.findSoftGroupByGroupName(groupName)
                .orElseThrow(SoftGroupNotFoundException::new);
        List<String> softSkillsName = softGroup
                .getSkills()
                .stream()
                .map(SoftSkill::getSkillName)
                .toList();
        return new SoftGroupWithSkillsDto(groupName, softSkillsName);
    }

    public void changeMarkAtUser(int userId, ChangeMarkSkillRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        SoftSkillMark softSkillMark = softSkillMarkRepository.findBySoftSkillAndUser(
                softSkillRepository.findById(request.skillId())
                        .orElseThrow(SoftSkillNotFoundException::new), user
        ).orElseThrow(SoftSkillMarkNotFoundException::new);
        softSkillMark.setMark(request.mark());
        softSkillMarkRepository.save(softSkillMark);
    }

    public UserSoftSkillsMarksDto findSoftSkillsWithMarksById(int userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
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
        return new UserSoftSkillsMarksDto(userId, softMarks);
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
}
