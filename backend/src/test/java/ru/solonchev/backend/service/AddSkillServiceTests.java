package ru.solonchev.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.solonchev.backend.dto.request.ChangeMarkSkillRequest;
import ru.solonchev.backend.exception.AddCompetenceNotFoundException;
import ru.solonchev.backend.exception.UserNotFoundException;
import ru.solonchev.backend.model.user.AddCompetence;
import ru.solonchev.backend.model.user.User;
import ru.solonchev.backend.repository.hard.AddCompetenceRepository;
import ru.solonchev.backend.repository.user.UserRepository;
import ru.solonchev.backend.util.DataUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddSkillServiceTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AddCompetenceRepository addCompetenceRepository;
    @InjectMocks
    private AddSkillService serviceUnderTest;

    private User user;
    private AddCompetence addCompetence;

    @BeforeEach
    public void setup() {
        user = DataUtils.getStepanEntityPersisted();
        addCompetence = DataUtils.getAddCompetenceEntityPersisted();
    }

    @Test
    @DisplayName("Test change mark add skill functionality")
    public void givenAddCompetenceAndUser_whenChangeMark_thenMarksIsChanged() {

        int userId = 1;
        ChangeMarkSkillRequest request = new ChangeMarkSkillRequest(1, 3);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(addCompetenceRepository.findById(anyInt())).thenReturn(Optional.of(addCompetence));

        serviceUnderTest.changeMarkAtUser(1, request);

        assertEquals(3, addCompetence.getMark());
        verify(addCompetenceRepository, times(1))
                .save(ArgumentMatchers.any(AddCompetence.class));
    }

    @Test
    @DisplayName("Test change add skill mark with incorrect user id functionality")
    public void givenUserWithIncorrectId_whenUserChangeAddSkillMark_thenExceptionIsThrown() {

        int userId = 1;
        ChangeMarkSkillRequest request = new ChangeMarkSkillRequest(1, 3);
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> serviceUnderTest.changeMarkAtUser(userId, request));

        assertEquals("User not found", exception.message());
        assertEquals("404", exception.code());
        verify(addCompetenceRepository, times(0)).save(ArgumentMatchers.any(AddCompetence.class));
    }

    @Test
    @DisplayName("Test change add skill mark with incorrect skill id functionality")
    public void givenAddCompetenceWithIncorrectId_whenUserChangeAddSkillMark_thenExceptionIsThrown() {

        int userId = 1;
        ChangeMarkSkillRequest request = new ChangeMarkSkillRequest(1, 3);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(addCompetenceRepository.findById(anyInt())).thenReturn(Optional.empty());

        AddCompetenceNotFoundException exception = assertThrows(AddCompetenceNotFoundException.class,
                () -> serviceUnderTest.changeMarkAtUser(userId, request));

        assertEquals("Additional Competence not found", exception.message());
        assertEquals("404", exception.code());
        verify(addCompetenceRepository, times(0)).save(ArgumentMatchers.any(AddCompetence.class));
    }
}
