package ru.solonchev.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solonchev.backend.dto.soft.SoftGroupWithSkillsDto;
import ru.solonchev.backend.dto.soft.SoftGroupsDto;
import ru.solonchev.backend.service.SoftSkillService;
import ru.solonchev.backend.utils.SkillGroupNameConverter;

@RestController
@RequestMapping("/specialist-profile/skills/soft")
@RequiredArgsConstructor
@Slf4j
public class SoftSkillController {

    private final SoftSkillService softSkillService;

    @GetMapping("/{groupName}")
    public ResponseEntity<SoftGroupWithSkillsDto> getSkillsOfGroup(@PathVariable String groupName) {
        return ResponseEntity.ok(
                softSkillService
                        .getSoftSkillsByGroupName(SkillGroupNameConverter.covertFromEnglishTermToRussian(groupName)));
    }

    @GetMapping("/")
    public ResponseEntity<SoftGroupsDto> getAllSoftGroups() {
        return ResponseEntity.ok(softSkillService.getAllSoftGroups());
    }
}
