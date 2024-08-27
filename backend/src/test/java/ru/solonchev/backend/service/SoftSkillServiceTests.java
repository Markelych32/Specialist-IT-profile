package ru.solonchev.backend.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.solonchev.backend.dto.request.ChangeMarkSkillRequest;
import ru.solonchev.backend.exception.soft.SoftGroupNotFoundException;
import ru.solonchev.backend.exception.soft.SoftSkillMarkNotFoundException;
import ru.solonchev.backend.exception.soft.SoftSkillNotFoundException;
import ru.solonchev.backend.exception.user.UserNotFoundException;
import ru.solonchev.backend.model.mark.SoftSkillMark;
import ru.solonchev.backend.model.soft.SoftGroup;
import ru.solonchev.backend.model.soft.SoftSkill;
import ru.solonchev.backend.model.user.User;
import ru.solonchev.backend.repository.mark.SoftSkillMarkRepository;
import ru.solonchev.backend.repository.soft.SoftGroupRepository;
import ru.solonchev.backend.repository.soft.SoftSkillRepository;
import ru.solonchev.backend.repository.user.UserRepository;
import ru.solonchev.backend.util.DataUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SoftSkillServiceTests {
    @Mock
    private SoftGroupRepository softGroupRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SoftSkillMarkRepository softSkillMarkRepository;
    @Mock
    private SoftSkillRepository softSkillRepository;
    @InjectMocks
    private SoftSkillService serviceUnderTest;


    @Test
    @DisplayName("Test give all soft groups when no one functionality -> empty list")
    public void givenNoOneSoftGroup_whenGetAll_thenEmptyListIsReturned() {

        when(softGroupRepository.findAll()).thenReturn(Collections.emptyList());

        var result = serviceUnderTest.findAllSoftGroups();

        assertNotNull(result);
        assertTrue(result.softGroups().isEmpty());
    }

    @Test
    @DisplayName("Test give all soft groups when two groups functionality -> success")
    public void givenTwoSoftGroups_whenGetAll_thenListIsReturned() {

        SoftGroup softGroup1 = SoftGroup.builder().groupName("Thinking").build();
        SoftGroup softGroup2 = SoftGroup.builder().groupName("Management").build();
        when(softGroupRepository.findAll()).thenReturn(List.of(softGroup1, softGroup2));

        var result = serviceUnderTest.findAllSoftGroups();

        assertNotNull(result);
        assertEquals(2, result.softGroups().size());
        assertEquals("Thinking", result.softGroups().get(0));
        assertEquals("Management", result.softGroups().get(1));
    }

    @Test
    @DisplayName("Test give all soft skill of incorrect group functionality -> exception")
    public void givenIncorrectSoftGroup_whenGetAllSoftSkills_thenExceptionIsThrown() {

        String groupName = "backend";
        when(softGroupRepository.findSoftGroupByGroupName(anyString())).thenReturn(Optional.empty());

        var exception = assertThrows(SoftGroupNotFoundException.class,
                () -> serviceUnderTest.findSoftSkillsByGroupName(groupName));

        assertEquals("Soft Group not found", exception.message());
        assertEquals("404", exception.code());
    }

    @Test
    @DisplayName("Test give all soft skills of group -> success")
    public void givenSoftGroup_whenGetAllSoftSkills_thenDtoIsReturned() {

        String groupName = "Мышление";
        SoftSkill softSkill1 = SoftSkill.builder().skillName("Логическое мышление").build();
        SoftSkill softSkill2 = SoftSkill.builder().skillName("Системное мышление").build();
        SoftGroup softGroup = SoftGroup.builder()
                .groupName(groupName)
                .skills(List.of(softSkill1, softSkill2))
                .build();
        when(softGroupRepository.findSoftGroupByGroupName(anyString())).thenReturn(Optional.of(softGroup));

        var result = serviceUnderTest.findSoftSkillsByGroupName(groupName);

        assertNotNull(result);
        assertEquals(groupName, result.groupName());
        assertEquals(2, result.softSkills().size());
        assertEquals("Логическое мышление", result.softSkills().get(0));
        assertEquals("Системное мышление", result.softSkills().get(1));
    }

    @Test
    @DisplayName("Test change soft skill mark at user with incorrect userId functionality -> exception")
    public void givenUserWithIncorrectId_whenChangeMarkSoftSkill_thenExceptionIsThrown() {

        int userId = 1;
        ChangeMarkSkillRequest request = new ChangeMarkSkillRequest(1, 3);
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        var exception = assertThrows(UserNotFoundException.class,
                () -> serviceUnderTest.changeMarkAtUser(userId, request));

        assertEquals("User not found", exception.message());
        assertEquals("404", exception.code());
    }

    @Test
    @DisplayName("Test change soft skill mark at user with incorrect soft skill userId functionality -> exception")
    public void givenIncorrectSoftSkill_whenChangeMarkSoftSkill_thenExceptionIsThrown() {

        int userId = 1;
        ChangeMarkSkillRequest request = new ChangeMarkSkillRequest(1, 3);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(DataUtils.getStepanEntityPersisted()));
        when(softSkillRepository.findById(anyInt())).thenReturn(Optional.empty());

        var exception = assertThrows(SoftSkillNotFoundException.class,
                () -> serviceUnderTest.changeMarkAtUser(userId, request));

        assertEquals("Soft Skill not found", exception.message());
        assertEquals("404", exception.code());
    }

    @Test
    @DisplayName("Test change incorrect soft skill mark at user functionality -> exception")
    public void givenIncorrectSoftSkillMark_whenChangeMarkSoftSkill_thenExceptionIsThrown() {

        int userId = 1;
        ChangeMarkSkillRequest request = new ChangeMarkSkillRequest(1, 3);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(DataUtils.getStepanEntityPersisted()));
        when(softSkillRepository.findById(anyInt())).thenReturn(Optional.of(SoftSkill.builder().id(1).build()));
        when(softSkillMarkRepository.findBySoftSkillAndUser(
                any(SoftSkill.class),
                any(User.class)
        )).thenReturn(Optional.empty());

        var exception = assertThrows(SoftSkillMarkNotFoundException.class,
                () -> serviceUnderTest.changeMarkAtUser(userId, request));

        assertEquals("Soft Skill Mark not found", exception.message());
        assertEquals("404", exception.code());
    }

    @Test
    @DisplayName("Test change soft skill mark at user functionality -> success")
    public void givenSoftSkillMark_whenChangeSoftSkillMark_thenMarkIsChanged() {

        int userId = 1;
        User user = User.builder().id(userId).build();
        SoftSkill softSkill = SoftSkill.builder().id(1).build();
        SoftSkillMark softSkillMark = SoftSkillMark.builder()
                .user(user)
                .softSkill(softSkill)
                .mark(1)
                .build();
        ChangeMarkSkillRequest request = new ChangeMarkSkillRequest(1, 3);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(softSkillRepository.findById(anyInt())).thenReturn(Optional.of(softSkill));
        when(softSkillMarkRepository.findBySoftSkillAndUser(
                any(SoftSkill.class),
                any(User.class)
        )).thenReturn(Optional.of(softSkillMark));

        serviceUnderTest.changeMarkAtUser(userId, request);

        assertEquals(3, softSkillMark.getMark());
        verify(softSkillMarkRepository, times(1)).save(softSkillMark);
    }

    @Test
    @DisplayName("Test give soft skills marks of incorrect user functionality -> exception")
    public void givenUserWithIncorrectId_whenGetAllSoftSkillsWithMark_thenExceptionIsThrown() {

        int userId = 1;
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        var exception = assertThrows(UserNotFoundException.class,
                () -> serviceUnderTest.findSoftSkillsWithMarksById(userId));

        assertEquals("User not found", exception.message());
        assertEquals("404", exception.code());
    }
}
