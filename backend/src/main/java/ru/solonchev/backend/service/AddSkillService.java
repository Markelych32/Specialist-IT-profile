package ru.solonchev.backend.service;

import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solonchev.backend.dto.request.ChangeMarkSkillRequest;
import ru.solonchev.backend.model.user.AddCompetence;
import ru.solonchev.backend.model.user.User;
import ru.solonchev.backend.repository.hard.AddCompetenceRepository;
import ru.solonchev.backend.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class AddSkillService {

    private final UserRepository userRepository;
    private final AddCompetenceRepository addCompetenceRepository;

    public void changeMarkAtUser(int userId, ChangeMarkSkillRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        AddCompetence addCompetence = addCompetenceRepository.findById(request.skillId())
                .orElseThrow(() -> new RuntimeException("Add Competence not found"));
        addCompetence.setMark(request.mark());
        addCompetenceRepository.save(addCompetence);
    }
}
