package ru.solonchev.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solonchev.backend.dto.request.AppendAddCompetenceRequestDto;
import ru.solonchev.backend.dto.request.FindAddCompetenceRequestDto;
import ru.solonchev.backend.dto.response.hard.AddCompetenceDto;
import ru.solonchev.backend.dto.response.hard.RoleWithHardSkillsDto;
import ru.solonchev.backend.dto.response.hard.RolesDto;
import ru.solonchev.backend.dto.response.hard.SuitableRoleDto;
import ru.solonchev.backend.service.HardSkillService;
import ru.solonchev.backend.utils.TermConverter;

import java.util.List;

@RestController
@RequestMapping("/specialist-profile")
@RequiredArgsConstructor
public class HardSkillController {

    private final HardSkillService hardSkillService;

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

    @GetMapping("/skills/add")
    public ResponseEntity<List<AddCompetenceDto>> getAllAddCompetences() {
        return ResponseEntity.ok(hardSkillService.findAllAddCompetence());
    }

    @PostMapping("/skills/add/suitable")
    public ResponseEntity<List<SuitableRoleDto>> getSuitableRoles(
            @RequestBody FindAddCompetenceRequestDto requestDto
    ) {
        return ResponseEntity.ok(hardSkillService.findSuitableRolesForAddCompetency(
                requestDto.competenceName()));
    }

}
