package ru.solonchev.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solonchev.backend.dto.request.AppendAddCompetenceRequestDto;
import ru.solonchev.backend.dto.request.ChangeMarkSkillRequest;
import ru.solonchev.backend.dto.response.mark.add.AddCompetenceWithMarkDto;
import ru.solonchev.backend.dto.response.mark.add.UserAddCompetencesWithMarksDto;
import ru.solonchev.backend.exception.add.AddCompetenceNotFoundException;
import ru.solonchev.backend.exception.user.UserNotFoundException;
import ru.solonchev.backend.model.user.AddCompetence;
import ru.solonchev.backend.model.user.User;
import ru.solonchev.backend.repository.hard.AddCompetenceRepository;
import ru.solonchev.backend.repository.role.RoleRepository;
import ru.solonchev.backend.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class AddSkillService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddCompetenceRepository addCompetenceRepository;

    public void changeMarkAtUser(int userId, ChangeMarkSkillRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        AddCompetence addCompetence = addCompetenceRepository.findById(request.skillId())
                .orElseThrow(AddCompetenceNotFoundException::new);
        addCompetence.setMark(request.mark());
        addCompetenceRepository.save(addCompetence);
    }

    public void addNewSkillToUser(
            int userId,
            AppendAddCompetenceRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        AddCompetence newAddCompetence = AddCompetence.builder()
                .name(requestDto.name())
                .user(user)
                .role(roleRepository.findById(requestDto.roleId()).orElseThrow((() -> new RuntimeException("Role not found"))))
                .build();
        user.getAddCompetences().add(newAddCompetence);
        userRepository.save(user);
    }

    public UserAddCompetencesWithMarksDto getAddCompetencesOfUserById(int id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
        return new UserAddCompetencesWithMarksDto(
                user.getId(),
                user.getAddCompetences()
                        .stream()
                        .map(addCompetence -> new AddCompetenceWithMarkDto(
                                addCompetence.getId(),
                                addCompetence.getName(),
                                addCompetence.getRole().getRoleName(),
                                addCompetence.getMark()
                        ))
                        .sorted((s1, s2) -> s2.mark() - s1.mark())
                        .toList()
        );
    }
}
