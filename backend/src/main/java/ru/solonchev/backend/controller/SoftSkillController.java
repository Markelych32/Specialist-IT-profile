package ru.solonchev.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solonchev.backend.dto.request.ChangeMarkSkillRequest;
import ru.solonchev.backend.dto.response.mark.soft.UserSoftSkillsMarksDto;
import ru.solonchev.backend.dto.response.soft.SoftGroupWithSkillsDto;
import ru.solonchev.backend.dto.response.soft.SoftGroupsDto;
import ru.solonchev.backend.service.SoftSkillService;
import ru.solonchev.backend.service.UserService;
import ru.solonchev.backend.utils.TermConverter;

@RestController
@RequestMapping("/specialist-profile")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://10.4.56.68:9000", "http://10.4.56.60:8081"
        , "http://85.192.63.64:1194"}, allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST,
                RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class SoftSkillController {

    private final SoftSkillService softSkillService;
    private final UserService userService;

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

    @GetMapping("/users/{id}/marks/soft")
    public ResponseEntity<UserSoftSkillsMarksDto> getSoftSkillsMarksByUserId(@PathVariable int id) {
        return ResponseEntity.ok(softSkillService.findSoftSkillsWithMarksById(id));
    }
}

