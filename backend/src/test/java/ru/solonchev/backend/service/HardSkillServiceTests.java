package ru.solonchev.backend.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.solonchev.backend.dto.request.ChangeMarkSkillRequest;
import ru.solonchev.backend.exception.hard.HardSkillMarkNotFoundException;
import ru.solonchev.backend.exception.hard.HardSkillNotFoundException;
import ru.solonchev.backend.exception.hard.RoleNotFoundException;
import ru.solonchev.backend.exception.user.UserNotFoundException;
import ru.solonchev.backend.model.hard.HardIndicator;
import ru.solonchev.backend.model.hard.HardSkill;
import ru.solonchev.backend.model.mark.HardSkillMark;
import ru.solonchev.backend.model.role.Role;
import ru.solonchev.backend.model.user.AddCompetence;
import ru.solonchev.backend.model.user.User;
import ru.solonchev.backend.repository.hard.AddCompetenceRepository;
import ru.solonchev.backend.repository.hard.HardSkillRepository;
import ru.solonchev.backend.repository.mark.HardSkillMarkRepository;
import ru.solonchev.backend.repository.role.RoleRepository;
import ru.solonchev.backend.repository.user.UserRepository;
import ru.solonchev.backend.util.DataUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HardSkillServiceTests {
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private AddCompetenceRepository addCompetenceRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private HardSkillMarkRepository hardSkillMarkRepository;
    @Mock
    private HardSkillRepository hardSkillRepository;
    @InjectMocks
    private HardSkillService serviceUnderTest;

    @Test
    @DisplayName("Test get all roles functionality -> success")
    public void givenTwoRoles_whenGetAll_thenRolesNamesAreReturned() {

        Role role1 = Role.builder().roleName("Backender").build();
        Role role2 = Role.builder().roleName("Frontender").build();
        when(roleRepository.findAll()).thenReturn(List.of(role1, role2));

        List<String> result = serviceUnderTest.findAllRoles().roles();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Backender", result.get(0));
        assertEquals("Frontender", result.get(1));
    }

    @Test
    @DisplayName("Test get all hard skills of incorrect role functionality -> exception")
    public void givenIncorrectRoleName_whenGetHardSkillsOfRole_thenExceptionIsThrown() {

        String roleName = "Test Role name";
        when(roleRepository.findRoleByRoleName(anyString())).thenReturn(Optional.empty());

        var exception = assertThrows(RoleNotFoundException.class,
                () -> serviceUnderTest.findHardSkillsByRole(roleName));

        assertEquals("Role not found", exception.message());
        assertEquals("404", exception.code());
    }

    @Test
    @DisplayName("Test get all hard skills of correct role functionality -> exception")
    public void givenCorrectRole_whenGetAllHardSkillsOfRole_thenHardSkillsAreReturned() {

        String roleName = "Developer";
        HardSkill hardSkill1 = HardSkill.builder()
                .skillName("Java")
                .indicators(List.of(
                        HardIndicator.builder().indicatorName("Expert").build(),
                        HardIndicator.builder().indicatorName("Intermediate").build()
                ))
                .build();
        HardSkill hardSkill2 = HardSkill.builder()
                .skillName("SQL")
                .indicators(List.of(
                        HardIndicator.builder().indicatorName("Advanced").build()
                ))
                .build();
        Role role = Role.builder()
                .hardSkills(List.of(hardSkill1, hardSkill2))
                .build();

        when(roleRepository.findRoleByRoleName(anyString())).thenReturn(Optional.of(role));
        var result = serviceUnderTest.findHardSkillsByRole(roleName);

        assertEquals(roleName, result.roleName());
        assertEquals(2, result.hardSkills().size());

        assertEquals("Java", result.hardSkills().get(0).hardSkill());
        assertEquals(List.of("Expert", "Intermediate"), result.hardSkills().get(0).indicators());

        assertEquals("SQL", result.hardSkills().get(1).hardSkill());
        assertEquals(List.of("Advanced"), result.hardSkills().get(1).indicators());
    }

    @Test
    @DisplayName("Test give all add competences when empty list functionality -> empty list")
    public void givenNoOneAddCompetences_whenGetAll_thenEmptyListIsReturned() {

        when(addCompetenceRepository.findAll()).thenReturn(Collections.emptyList());

        var result = serviceUnderTest.findAllAddCompetence();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Test give all add competences functionality -> success")
    public void givenTwoAddCompetences_whenGetAll_thenTwoCompetencesAreReturned() {

        Role role = Role.builder().roleName("Developer").build();

        AddCompetence addCompetence1 = AddCompetence.builder()
                .id(1)
                .name("Java Programming")
                .role(role)
                .build();
        AddCompetence addCompetence2 = AddCompetence.builder()
                .id(2)
                .name("SQL Database")
                .role(role)
                .build();
        List<AddCompetence> addCompetences = List.of(addCompetence1, addCompetence2);
        when(addCompetenceRepository.findAll()).thenReturn(addCompetences);

        var result = serviceUnderTest.findAllAddCompetence();

        assertEquals(2, result.size());

        assertEquals(1, result.get(0).id());
        assertEquals("Java Programming", result.get(0).name());
        assertEquals("Developer", result.get(0).role());

        assertEquals(2, result.get(1).id());
        assertEquals("SQL Database", result.get(1).name());
        assertEquals("Developer", result.get(1).role());
    }

    @Test
    @DisplayName("Test find all suitable add skills when they are absent functionality -> empty list")
    public void givenStringRequest_whenFindSuitableAddRolesAreAbsent_thenEmptyListIsReturned() {

        String partName = "Pascal";
        when(addCompetenceRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(Collections.emptyList());

        var result = serviceUnderTest.findSuitableRolesForAddCompetency(partName);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Test find all suitable add skills functionality -> success")
    public void givenStringRequest_whenFindSuitableAddRoles_thenListIsReturned() {

        String partName = "Pascal";
        Role role = Role.builder().roleName("Developer").build();

        AddCompetence addCompetence1 = AddCompetence.builder()
                .id(1)
                .name("Pascal Programming")
                .role(role)
                .build();
        AddCompetence addCompetence2 = AddCompetence.builder()
                .id(2)
                .name("Advanced Pascal")
                .role(role)
                .build();
        List<AddCompetence> competences = List.of(addCompetence1, addCompetence2);
        when(addCompetenceRepository.findByNameContainingIgnoreCase(anyString()))
                .thenReturn(competences);

        var result = serviceUnderTest.findSuitableRolesForAddCompetency(partName);

        assertEquals(2, result.size(), "Expected two suitable roles");

        assertEquals(1L, result.get(0).id());
        assertEquals("Pascal Programming", result.get(0).skillName());
        assertEquals("Developer", result.get(0).roleName());

        assertEquals(2L, result.get(1).id());
        assertEquals("Advanced Pascal", result.get(1).skillName());
        assertEquals("Developer", result.get(1).roleName());
    }

    @Test
    @DisplayName("Test change mark hard skill at user with incorrect id functionality -> exception")
    public void givenUserWithIncorrectId_whenChangeHardSkillMark_thenExceptionIsThrown() {

        int userId = 1;
        ChangeMarkSkillRequest request = new ChangeMarkSkillRequest(1, 3);
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        var exception = assertThrows(UserNotFoundException.class,
                () -> serviceUnderTest.changeMarkAtUser(userId, request));

        assertEquals("User not found", exception.message());
        assertEquals("404", exception.code());
    }

    @Test
    @DisplayName("Test change mark incorrect hard skill at user functionality -> exception")
    public void givenIncorrectHardSkill_whenChangeHardSkillMark_thenExceptionIsThrown() {

        int userId = 1;
        ChangeMarkSkillRequest request = new ChangeMarkSkillRequest(1, 3);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(DataUtils.getStepanEntityPersisted()));
        when(hardSkillRepository.findById(anyInt())).thenReturn(Optional.empty());

        var exception = assertThrows(HardSkillNotFoundException.class,
                () -> serviceUnderTest.changeMarkAtUser(userId, request));

        assertEquals("Hard Skill not found", exception.message());
        assertEquals("404", exception.code());
    }

    @Test
    @DisplayName("Test change mark incorrect hard skill mark at user functionality -> exception")
    public void givenIncorrectHardSkillMark_whenChangeMarkHardSkill_thenExceptionIsThrown() {

        int userId = 1;
        ChangeMarkSkillRequest request = new ChangeMarkSkillRequest(1, 3);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(DataUtils.getStepanEntityPersisted()));
        when(hardSkillRepository.findById(anyInt())).thenReturn(Optional.of(HardSkill.builder().id(1).build()));
        when(hardSkillMarkRepository.findByHardSkillAndUser(any(HardSkill.class), any(User.class))).thenReturn(Optional.empty());

        var exception = assertThrows(HardSkillMarkNotFoundException.class,
                () -> serviceUnderTest.changeMarkAtUser(userId, request));

        assertEquals("Hard Skill Mark not found", exception.message());
        assertEquals("404", exception.code());
    }

    @Test
    @DisplayName("Test change mark hard skill functionality -> success")
    public void givenHardSkillMark_whenChangeMarkHardSkill_thenMarkIsChanged() {

        ChangeMarkSkillRequest request = new ChangeMarkSkillRequest(1, 3);
        User user = User.builder().id(1).build();
        HardSkill hardSkill = HardSkill.builder().id(1).build();
        HardSkillMark hardSkillMark = HardSkillMark.builder()
                .user(user)
                .hardSkill(hardSkill)
                .build();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(hardSkillRepository.findById(anyInt())).thenReturn(Optional.of(hardSkill));
        when(hardSkillMarkRepository.findByHardSkillAndUser(hardSkill, user)).thenReturn(Optional.of(hardSkillMark));

        serviceUnderTest.changeMarkAtUser(1, request);

        assertEquals(3, hardSkillMark.getMark());
        Mockito.verify(hardSkillMarkRepository, Mockito.times(1)).save(hardSkillMark);
    }
}
