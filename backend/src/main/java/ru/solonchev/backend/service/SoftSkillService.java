package ru.solonchev.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solonchev.backend.dto.soft.SoftGroupWithSkillsDto;
import ru.solonchev.backend.dto.soft.SoftGroupsDto;
import ru.solonchev.backend.model.soft.SoftGroup;
import ru.solonchev.backend.model.soft.SoftSkill;
import ru.solonchev.backend.repository.soft.SoftGroupRepository;
import ru.solonchev.backend.repository.soft.SoftSkillRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SoftSkillService {

    private final SoftGroupRepository softGroupRepository;
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
}
