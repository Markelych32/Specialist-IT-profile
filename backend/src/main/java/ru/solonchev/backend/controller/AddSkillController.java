package ru.solonchev.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solonchev.backend.dto.request.ChangeMarkSkillRequest;
import ru.solonchev.backend.dto.response.hard.AddCompetenceDto;
import ru.solonchev.backend.service.AddSkillService;
import ru.solonchev.backend.service.HardSkillService;

import java.util.List;

@RestController
@RequestMapping("/specialist-profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:9000", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST,
                RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class AddSkillController {

    private final AddSkillService addSkillService;

    @PutMapping("/users/{id}/marks/add")
    public ResponseEntity<Void> changeMarkAddSkill(
            @PathVariable(name = "id") int userId,
            @RequestBody ChangeMarkSkillRequest request
    ) {
        addSkillService.changeMarkAtUser(userId, request);
        return ResponseEntity.ok().build();
    }
}
