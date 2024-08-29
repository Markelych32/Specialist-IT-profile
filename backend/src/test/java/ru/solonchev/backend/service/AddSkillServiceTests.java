package ru.solonchev.backend.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.solonchev.backend.dto.request.AppendAddCompetenceRequestDto;
import ru.solonchev.backend.dto.request.ChangeMarkAndRoleRequestDto;
import ru.solonchev.backend.exception.add.AddCompetenceNotFoundException;
import ru.solonchev.backend.exception.user.UserNotFoundException;
import ru.solonchev.backend.model.role.Role;
import ru.solonchev.backend.model.user.AddCompetence;
import ru.solonchev.backend.model.user.User;
import ru.solonchev.backend.repository.add.AddCompetenceRepository;
import ru.solonchev.backend.repository.role.RoleRepository;
import ru.solonchev.backend.repository.user.UserRepository;
import ru.solonchev.backend.util.DataUtils;

import java.util.List;
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
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private AddSkillService serviceUnderTest;

    private User user;
    private AddCompetence addCompetence;

    @BeforeEach
    public void setup() {
        user = DataUtils.getStepanEntityPersisted();
        addCompetence = DataUtils.getAddKotlinCompetenceEntityPersisted();
    }

    @Test
    @DisplayName("Test change mark add skill functionality")
    public void givenAddCompetenceAndUser_whenChangeMarkAndRole_thenMarkAndRoleAreChanged() {

        ChangeMarkAndRoleRequestDto request = new ChangeMarkAndRoleRequestDto(1, 3, 2);
        Role role = Role.builder().id(2).build();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(addCompetenceRepository.findByIdAndUser(ArgumentMatchers.anyInt(),
                ArgumentMatchers.any(User.class))).thenReturn(Optional.of(addCompetence));
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));

        serviceUnderTest.changeMarkAtUser(1, request);

        assertEquals(3, addCompetence.getMark());
        assertEquals(2, addCompetence.getRole().getId());
        verify(addCompetenceRepository, times(1))
                .save(ArgumentMatchers.any(AddCompetence.class));
    }

    @Test
    @DisplayName("Test change add skill mark with incorrect user userId functionality")
    public void givenUserWithIncorrectId_whenUserChangeAddSkillMark_thenExceptionIsThrown() {

        int userId = 1;
        ChangeMarkAndRoleRequestDto request = new ChangeMarkAndRoleRequestDto(1, 3, 2);
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> serviceUnderTest.changeMarkAtUser(userId, request));

        assertEquals("User not found", exception.message());
        assertEquals("404", exception.code());
        verify(addCompetenceRepository, times(0)).save(ArgumentMatchers.any(AddCompetence.class));
    }

    @Test
    @DisplayName("Test change add skill mark with incorrect skill userId functionality")
    public void givenAddCompetenceWithIncorrectId_whenUserChangeAddSkillMark_thenExceptionIsThrown() {

        int userId = 1;
        ChangeMarkAndRoleRequestDto request = new ChangeMarkAndRoleRequestDto(1, 3, 2);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(addCompetenceRepository.findByIdAndUser(ArgumentMatchers.anyInt(),
                ArgumentMatchers.any(User.class))).thenReturn(Optional.empty());

        AddCompetenceNotFoundException exception = assertThrows(AddCompetenceNotFoundException.class,
                () -> serviceUnderTest.changeMarkAtUser(userId, request));

        assertEquals("Additional Competence not found", exception.message());
        assertEquals("404", exception.code());
        verify(addCompetenceRepository, times(0)).save(ArgumentMatchers.any(AddCompetence.class));
    }

    @Test
    @DisplayName("Test append new add skill to user with incorrect userId functionality -> exception")
    public void givenUserWithIncorrectId_whenAppendNewAddCompetence_thenExceptionIsThrown() {

        int userId = 1;
        AppendAddCompetenceRequestDto request = new AppendAddCompetenceRequestDto("skill_name", 1);
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        var exception = assertThrows(UserNotFoundException.class,
                () -> serviceUnderTest.addNewSkillToUser(userId, request));

        assertEquals("User not found", exception.message());
        assertEquals("404", exception.code());
        verify(userRepository, times(0)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    @DisplayName("Test append new add skill to user functionality -> success")
    public void givenNewAddCompetence_whenAppendNewAddCompetence_thenNewSkillIsAdded() {

        int userId = 1;
        Role role = Role.builder().id(1).roleName("Developer").build();
        User user = DataUtils.getStepanEntityPersisted();
        AppendAddCompetenceRequestDto request = new AppendAddCompetenceRequestDto("skill_name", 1);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));

        serviceUnderTest.addNewSkillToUser(userId, request);
        var result = user.getAddCompetences().get(0);

        assertEquals(1, user.getAddCompetences().size());
        assertEquals("skill_name", result.getName());
        assertEquals(user, result.getUser());
        assertEquals(role, result.getRole());
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Test get all add competences of absent user functionality -> exception")
    public void givenUserWithIncorrectId_whenGetAllAddCompetencesOfUser_thenExceptionIsThrown() {

        int userId = 1;
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        var exception = assertThrows(UserNotFoundException.class,
                () -> serviceUnderTest.getAddCompetencesOfUserById(userId));

        assertEquals("User not found", exception.message());
        assertEquals("404", exception.code());
    }

    @Test
    @DisplayName("Test get all add competences of user functionality -> success")
    public void givenUserWithTwoAddCompetences_whenGetAllAddCompetencesOfUser_thenDtoIsReturned() {

        int userId = 1;
        Role role = Role.builder().roleName("Developer").build();
        AddCompetence addCompetence1 = AddCompetence.builder()
                .id(2)
                .name("Java Programming")
                .role(role)
                .mark(1)
                .build();
        AddCompetence addCompetence2 = AddCompetence.builder()
                .id(3)
                .name("SQL Database")
                .role(role)
                .mark(1)
                .build();
        User user = User.builder()
                .id(userId)
                .addCompetences(List.of(addCompetence1, addCompetence2))
                .build();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        var result = serviceUnderTest.getAddCompetencesOfUserById(userId);

        Assertions.assertNotNull(result);
        assertEquals(userId, result.id());
        assertEquals(2, result.addCompetences().size());
        assertEquals("Java Programming", result.addCompetences().get(0).name());
        assertEquals(1, result.addCompetences().get(0).mark());
        assertEquals("SQL Database", result.addCompetences().get(1).name());
        assertEquals(1, result.addCompetences().get(1).mark());
    }

    @Test
    @DisplayName("Test delete add competence of absent user functionality -> exception")
    public void givenUserWithIncorrectId_whenDeleteAddCompetence_thenExceptionIsThrown() {

        int userId = 1;
        int skillId = 3;
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        var exception = assertThrows(UserNotFoundException.class,
                () -> serviceUnderTest.deleteAddCompetenceAtUser(userId, skillId));

        assertEquals("User not found", exception.message());
        assertEquals("404", exception.code());
    }

    @Test
    @DisplayName("Test delete absent add competence of user functionality -> exception")
    public void givenAddCompWithIncorrectId_whenDeleteAddCompetence_thenExceptionIsThrown() {

        int userId = 1;
        int skillId = 3;
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(
                DataUtils.getStepanEntityPersisted()
        ));
        when(addCompetenceRepository.findByIdAndUser(ArgumentMatchers.anyInt(),
                ArgumentMatchers.any(User.class))).thenReturn(
                Optional.empty()
        );

        var exception = assertThrows(AddCompetenceNotFoundException.class,
                () -> serviceUnderTest.deleteAddCompetenceAtUser(userId, skillId));

        assertEquals("Additional Competence not found", exception.message());
        assertEquals("404", exception.code());
    }
}
