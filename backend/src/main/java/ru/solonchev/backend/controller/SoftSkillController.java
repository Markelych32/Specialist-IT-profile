package ru.solonchev.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solonchev.backend.dto.response.soft.SoftGroupWithSkillsDto;
import ru.solonchev.backend.dto.response.soft.SoftGroupsDto;
import ru.solonchev.backend.service.SoftSkillService;
import ru.solonchev.backend.utils.TermConverter;

@RestController
@RequestMapping("/specialist-profile/skills/soft")
@RequiredArgsConstructor
@CrossOrigin
public class SoftSkillController {

    private final SoftSkillService softSkillService;

    @GetMapping("/{groupName}")
    public ResponseEntity<SoftGroupWithSkillsDto> getSkillsOfGroup(@PathVariable String groupName) {
        return ResponseEntity.ok(
                softSkillService
                        .findSoftSkillsByGroupName(TermConverter.covertSoftGroupName(groupName)));
    }

    @GetMapping("/groups")
    public ResponseEntity<SoftGroupsDto> getAllSoftGroups() {
        return ResponseEntity.ok(softSkillService.findAllSoftGroups());
    }
}

