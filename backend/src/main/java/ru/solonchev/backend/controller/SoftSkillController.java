package ru.solonchev.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solonchev.backend.dto.request.ChangeMarkSkillRequest;
import ru.solonchev.backend.dto.response.soft.SoftGroupWithSkillsDto;
import ru.solonchev.backend.dto.response.soft.SoftGroupsDto;
import ru.solonchev.backend.service.SoftSkillService;
import ru.solonchev.backend.utils.TermConverter;

@RestController
@RequestMapping("/specialist-profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:9000", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST,
                RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class SoftSkillController {

    private final SoftSkillService softSkillService;

    @GetMapping("/skills/soft/{groupName}")
    public ResponseEntity<SoftGroupWithSkillsDto> getSkillsOfGroup(@PathVariable String groupName) {
        return ResponseEntity.ok(
                softSkillService
                        .findSoftSkillsByGroupName(TermConverter.covertSoftGroupName(groupName)));
    }

    @GetMapping("/skills/soft/groups")
    public ResponseEntity<SoftGroupsDto> getAllSoftGroups() {
        return ResponseEntity.ok(softSkillService.findAllSoftGroups());
    }

    @PutMapping("users/{id}/marks/soft")
    public ResponseEntity<Void> changeMarkSoftSkill(
            @PathVariable(name = "id") int userId,
            @RequestBody ChangeMarkSkillRequest request
    ) {
        softSkillService.changeMarkAtUser(userId, request);
        return ResponseEntity.ok().build();
    }
}

