package ru.solonchev.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solonchev.backend.dto.hard.RoleWithHardSkillsDto;
import ru.solonchev.backend.dto.hard.RolesDto;
import ru.solonchev.backend.service.HardSkillService;
import ru.solonchev.backend.utils.TermConverter;

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
}
