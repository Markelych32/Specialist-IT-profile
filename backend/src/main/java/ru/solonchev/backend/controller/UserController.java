package ru.solonchev.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solonchev.backend.dto.request.AppendAddCompetenceRequestDto;
import ru.solonchev.backend.dto.request.ChangeMarkHardSkillRequest;
import ru.solonchev.backend.dto.response.mark.add.UserAddCompetencesWithMarksDto;
import ru.solonchev.backend.dto.response.mark.hard.UserHardSkillsMarksDto;
import ru.solonchev.backend.dto.response.mark.soft.UserSoftSkillsMarksDto;
import ru.solonchev.backend.dto.response.user.GeneralUserInfoDto;
import ru.solonchev.backend.dto.response.user.UserDto;
import ru.solonchev.backend.dto.response.user.UserJobInfoDto;
import ru.solonchev.backend.service.HardSkillService;
import ru.solonchev.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/specialist-profile")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final HardSkillService hardSkillService;

    @GetMapping("/users/general/{id}")
    public ResponseEntity<GeneralUserInfoDto> getGeneralInfoByUserId(@PathVariable int id) {
        return ResponseEntity.ok(userService.findGeneralInfoById(id));
    }

    @GetMapping("/users/working/{id}")
    public ResponseEntity<UserJobInfoDto> getJobInfoByUserId(@PathVariable int id) {
        return ResponseEntity.ok(userService.findJobInfoById(id));
    }

    @GetMapping("/users/{id}/marks/soft")
    public ResponseEntity<UserSoftSkillsMarksDto> getSoftSkillsMarksByUserId(@PathVariable int id) {
        return ResponseEntity.ok(userService.findSoftSkillsWithMarksById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/users/{id}/marks/hard")
    public ResponseEntity<UserHardSkillsMarksDto> getHardSkillsMarksByUserId(@PathVariable int id) {
        return ResponseEntity.ok(userService.findHardSkillsWithMarksById(id));
    }

    @GetMapping("/users/{id}/marks/add")
    public ResponseEntity<UserAddCompetencesWithMarksDto> getAddSkillsMarksByUserId(@PathVariable int id) {
        return ResponseEntity.ok(userService.getAddCompetencesOfUserById(id));
    }

    @PostMapping("/users/{id}/skills/add")
    public ResponseEntity<Void> appendAddSkillToUser(
            @PathVariable(name = "id") int userId,
            @RequestBody AppendAddCompetenceRequestDto requestDto
    ) {
        hardSkillService.addNewSkillToUser(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}/skills/hard")
    public ResponseEntity<Void> changeMarkHardSkill(
            @PathVariable(name = "id") int userId,
            @RequestBody ChangeMarkHardSkillRequest request
    ) {
        hardSkillService.changeMarkAtUser(userId, request);
        return ResponseEntity.ok().build();
    }
}
