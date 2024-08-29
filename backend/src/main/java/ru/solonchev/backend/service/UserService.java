package ru.solonchev.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solonchev.backend.dto.response.user.GeneralUserInfoDto;
import ru.solonchev.backend.dto.response.user.UserDto;
import ru.solonchev.backend.dto.response.user.UserJobInfoDto;
import ru.solonchev.backend.exception.user.UserNotFoundException;
import ru.solonchev.backend.model.user.User;
import ru.solonchev.backend.repository.user.UserRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public GeneralUserInfoDto findGeneralInfoById(int id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
        int age = calculateAge(user.getDateOfBirth());
        String ageString = getAgeString(age);
        return new GeneralUserInfoDto(
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth(),
                age + " " + ageString,
                user.getGender(),
                user.getLocation()
        );
    }

    public UserJobInfoDto findJobInfoById(int id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
        return new UserJobInfoDto(
                user.getPost().getPostName(),
                user.getRole().getRoleName(),
                user.getSpecialization()
        );
    }

    public List<UserDto> findAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(this::fromEntityToDto)
                .toList();
    }

    public List<UserDto> findAllUsersByFullName(String fullName) {
        return userRepository.findByFullName(fullName)
                .stream()
                .map(this::fromEntityToDto)
                .toList();
    }

    private int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    private String getAgeString(int age) {
        if (age % 10 == 1 && age % 100 != 11) {
            return "год";
        } else if ((age % 10 >= 2 && age % 10 <= 4) && (age % 100 < 10 || age % 100 >= 20)) {
            return "года";
        } else {
            return "лет";
        }
    }

    private UserDto fromEntityToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                user.getDateOfBirth(),
                user.getGender(),
                user.getLocation(),
                user.getPost().getPostName(),
                user.getRole().getRoleName(),
                user.getSpecialization());
    }
}
