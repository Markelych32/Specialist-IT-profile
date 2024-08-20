package ru.solonchev.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solonchev.backend.dto.mark.UserSoftSkillsMarksDto;
import ru.solonchev.backend.dto.user.GeneralUserInfoDto;
import ru.solonchev.backend.dto.user.UserDto;
import ru.solonchev.backend.dto.user.UserJobInfoDto;
import ru.solonchev.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/specialist-profile")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/general/{id}")
    public ResponseEntity<GeneralUserInfoDto> getGeneralInfoByUserId(@PathVariable int id) {
        return ResponseEntity.ok(userService.findGeneralInfoById(id));
    }

    @GetMapping("/users/working/{id}")
    public ResponseEntity<UserJobInfoDto> getJobInfoByUserId(@PathVariable int id) {
        return ResponseEntity.ok(userService.findJobInfoById(id));
    }

    @GetMapping("/users/marks/soft/{id}")
    public ResponseEntity<UserSoftSkillsMarksDto> getSoftSkillsMarksByUserId(@PathVariable int id) {
        return ResponseEntity.ok(userService.findSoftSkillsWithMarksById(id));
    }

    @GetMapping("/users/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

}
