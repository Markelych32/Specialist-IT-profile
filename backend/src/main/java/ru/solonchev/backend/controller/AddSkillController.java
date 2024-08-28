package ru.solonchev.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solonchev.backend.dto.request.AppendAddCompetenceRequestDto;
import ru.solonchev.backend.dto.request.ChangeMarkAndRoleRequestDto;
import ru.solonchev.backend.dto.response.mark.add.UserAddCompetencesWithMarksDto;
import ru.solonchev.backend.service.AddSkillService;

@RestController
@RequestMapping("/specialist-profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://10.4.56.68:9000", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST,
                RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class AddSkillController {

    private final AddSkillService addSkillService;

    @PutMapping("/users/{id}/marks/add")
    public ResponseEntity<Void> changeMarkAddSkill(
            @PathVariable(name = "id") int userId,
            @RequestBody ChangeMarkAndRoleRequestDto request
    ) {
        addSkillService.changeMarkAtUser(userId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}/marks/add")
    public ResponseEntity<UserAddCompetencesWithMarksDto> getAddSkillsMarksByUserId(@PathVariable int id) {
        return ResponseEntity.ok(addSkillService.getAddCompetencesOfUserById(id));
    }

    @PostMapping("/users/{id}/skills/add")
    public ResponseEntity<Void> appendAddSkillToUser(
            @PathVariable(name = "id") int userId,
            @RequestBody AppendAddCompetenceRequestDto requestDto
    ) {
        addSkillService.addNewSkillToUser(userId, requestDto);
        return ResponseEntity.ok().build();
    }
}
