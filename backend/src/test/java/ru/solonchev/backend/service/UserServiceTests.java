package ru.solonchev.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.solonchev.backend.exception.user.UserNotFoundException;
import ru.solonchev.backend.model.role.Role;
import ru.solonchev.backend.model.user.Post;
import ru.solonchev.backend.model.user.User;
import ru.solonchev.backend.repository.role.RoleRepository;
import ru.solonchev.backend.repository.soft.SoftGroupRepository;
import ru.solonchev.backend.repository.user.UserRepository;
import ru.solonchev.backend.util.DataUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private SoftGroupRepository softGroupRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserService serviceUnderTest;

    @Test
    @DisplayName("Test get general info of user with incorrect userId functionality -> exception")
    public void givenUserWithIncorrectId_whenFindGeneralInfo_thenExceptionIsThrown() {

        int userId = 1;
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(UserNotFoundException.class,
                () -> serviceUnderTest.findGeneralInfoById(userId));

        assertEquals("User not found", exception.message());
        assertEquals("404", exception.code());
    }

    @Test
    @DisplayName("Test get general info of user functionality -> success")
    public void givenUser_whenFindGeneralInfo_thenDtoIsReturned() {

        int userId = 1;
        User user = DataUtils.getStepanEntityPersisted();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        var result = serviceUnderTest.findGeneralInfoById(userId);

        assertNotNull(result);
        assertEquals("Степан", result.firstName());
        assertEquals("Ульяшин", result.lastName());
        assertEquals("1996-07-13", String.valueOf(result.dateOfBirth()));
        assertEquals("28 лет", result.years());
        assertEquals("Мужской", result.gender());
        assertEquals("Москва", result.location());
    }

    @Test
    @DisplayName("Test get working info of user with incorrect userId functionality -> exception")
    public void givenUserWithIncorrectId_whenGetWorkingInfoOfUser_thenExceptionIsThrown() {

        int userId = 1;
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        var exception = assertThrows(UserNotFoundException.class,
                () -> serviceUnderTest.findJobInfoById(userId));

        assertEquals("User not found", exception.message());
        assertEquals("404", exception.code());
    }

    @Test
    @DisplayName("Test get working info of user functionality -> success")
    public void givenUser_whenGetWorkingInfoOfUser_thenDtoIsReturned() {

        int userId = 1;
        Role role = Role.builder().roleName("Senior").build();
        Post post = Post.builder().postName("Developer").build();
        User user = User.builder()
                .post(post)
                .role(role)
                .specialization("Java Backend Developer")
                .build();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        var result = serviceUnderTest.findJobInfoById(userId);

        assertNotNull(result);
        assertEquals("Developer", result.post());
        assertEquals("Senior", result.role());
        assertEquals("Java Backend Developer", result.specialization());
    }

    @Test
    @DisplayName("Test get all users when no one functionality -> empty list")
    public void givenNoOneUsers_whenGetAll_thenEmptyListIsReturned() {

        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        var result = serviceUnderTest.findAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Test get all users functionality -> success")
    public void givenTwoUsers_whenGetAll_thenDtoIsReturned() {

        User user1 = DataUtils.getNickEntityPersisted();
        User user2 = DataUtils.getStepanEntityPersisted();
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        var result = serviceUnderTest.findAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Степан", result.get(1).firstName());
        assertEquals("Никита", result.get(0).firstName());
    }


}
