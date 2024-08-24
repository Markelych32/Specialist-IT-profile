package ru.solonchev.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solonchev.backend.dto.request.ChangeMarkSkillRequest;
import ru.solonchev.backend.dto.response.soft.SoftGroupWithSkillsDto;
import ru.solonchev.backend.dto.response.soft.SoftGroupsDto;
import ru.solonchev.backend.model.mark.HardSkillMark;
import ru.solonchev.backend.model.mark.SoftSkillMark;
import ru.solonchev.backend.model.soft.SoftGroup;
import ru.solonchev.backend.model.soft.SoftSkill;
import ru.solonchev.backend.model.user.User;
import ru.solonchev.backend.repository.mark.SoftSkillMarkRepository;
import ru.solonchev.backend.repository.soft.SoftGroupRepository;
import ru.solonchev.backend.repository.soft.SoftSkillRepository;
import ru.solonchev.backend.repository.user.UserRepository;

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
        SoftGroup softGroup = softGroupRepository.findSoftGroupByGroupName(groupName).orElseThrow(() -> new RuntimeException("Soft group not found"));
        List<String> softSkillsName = softGroup
                .getSkills()
                .stream()
                .map(SoftSkill::getSkillName)
                .toList();
        return new SoftGroupWithSkillsDto(groupName, softSkillsName);
    }

    public void changeMarkAtUser(int userId, ChangeMarkSkillRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SoftSkillMark softSkillMark = softSkillMarkRepository.findBySoftSkill(
                softSkillRepository.findById(request.skillId()).orElseThrow(() -> new RuntimeException("Soft Skill not found"))
        ).orElseThrow(() -> new RuntimeException("Soft Skill Mark not found"));
        softSkillMark.setMark(request.mark());
        softSkillMarkRepository.save(softSkillMark);
    }
}
