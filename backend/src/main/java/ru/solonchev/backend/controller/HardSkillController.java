package ru.solonchev.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solonchev.backend.dto.request.ChangeMarkSkillRequest;
import ru.solonchev.backend.dto.request.FindAddCompetenceRequestDto;
import ru.solonchev.backend.dto.response.hard.RoleWithHardSkillsDto;
import ru.solonchev.backend.dto.response.hard.RolesDto;
import ru.solonchev.backend.dto.response.hard.SuitableRoleDto;
import ru.solonchev.backend.dto.response.mark.hard.UserHardSkillsMarksDto;
import ru.solonchev.backend.service.HardSkillService;
import ru.solonchev.backend.service.UserService;
import ru.solonchev.backend.utils.TermConverter;

import java.util.List;

@RestController
@RequestMapping("/specialist-profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:9000", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST,
                RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class HardSkillController {

    private final HardSkillService hardSkillService;
    private final UserService userService;

    @GetMapping("/roles")
    public ResponseEntity<RolesDto> getAllRoles() {
        return ResponseEntity.ok(hardSkillService.findAllRoles());
    }

    @GetMapping("/skills/hard/{roleName}")
    public ResponseEntity<RoleWithHardSkillsDto> getHardSkillsOfRole(
            @PathVariable String roleName
    ) {
        return ResponseEntity.ok(hardSkillService.findHardSkillsByRole(
                TermConverter.convertRoleName(roleName))
        );
    }

    @GetMapping("/users/{id}/marks/hard")
    public ResponseEntity<UserHardSkillsMarksDto> getHardSkillsMarksByUserId(@PathVariable int id) {
        return ResponseEntity.ok(hardSkillService.findHardSkillsWithMarksById(id));
    }

    @PostMapping("/skills/add/suitable")
    public ResponseEntity<List<SuitableRoleDto>> getSuitableRoles(
            @RequestBody FindAddCompetenceRequestDto requestDto
    ) {
        return ResponseEntity.ok(hardSkillService.findSuitableRolesForAddCompetency(
                requestDto.competenceName()));
    }

    @PutMapping("/users/{id}/marks/hard")
    public ResponseEntity<Void> changeMarkHardSkill(
            @PathVariable(name = "id") int userId,
            @RequestBody ChangeMarkSkillRequest request
    ) {
        hardSkillService.changeMarkAtUser(userId, request);
        return ResponseEntity.ok().build();
    }
}
