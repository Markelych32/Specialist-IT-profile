package ru.solonchev.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solonchev.backend.dto.response.user.GeneralUserInfoDto;
import ru.solonchev.backend.dto.response.user.UserDto;
import ru.solonchev.backend.dto.response.user.UserJobInfoDto;
import ru.solonchev.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/specialist-profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:9000", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST,
                RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
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

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }
}
