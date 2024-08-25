package ru.solonchev.backend.util;

import ru.solonchev.backend.model.role.Role;
import ru.solonchev.backend.model.user.AddCompetence;
import ru.solonchev.backend.model.user.Post;
import ru.solonchev.backend.model.user.User;

import java.time.LocalDate;
import java.util.ArrayList;

public final class DataUtils {
    private DataUtils() {

    }

    public static User getStepanEntityTransient() {
        return User.builder()
                .firstName("Степан")
                .lastName("Ульяшин")
                .patronymic("Михайлович")
                .dateOfBirth(LocalDate.of(1996, 7, 13))
                .gender("Мужской")
                .location("Москва")
                .post(new Post(1, "Senior"))
                .role(Role.builder().roleName("Developer").build())
                .specialization("Разработка на Go")
                .addCompetences(new ArrayList<>())
                .build();
    }

    public static User getNickEntityTransient() {
        return User.builder()
                .firstName("Никита")
                .lastName("Кологривый")
                .patronymic("Сергеевич")
                .dateOfBirth(LocalDate.of(1994, 10, 16))
                .gender("Мужской")
                .location("Москва")
                .post(new Post(1, "Intern"))
                .role(Role.builder().roleName("QA Tester").build())
                .specialization("Тестирование на Python")
                .addCompetences(new ArrayList<>())
                .build();
    }

    public static User getStepanEntityPersisted() {
        return User.builder()
                .id(1)
                .firstName("Степан")
                .lastName("Ульяшин")
                .patronymic("Михайлович")
                .dateOfBirth(LocalDate.of(1996, 7, 13))
                .gender("Мужской")
                .location("Москва")
                .post(new Post(1, "Senior"))
                .role(Role.builder().roleName("Developer").build())
                .specialization("Разработка на Go")
                .addCompetences(new ArrayList<>())
                .build();
    }

    public static User getNickEntityPersisted() {
        return User.builder()
                .id(1)
                .firstName("Никита")
                .lastName("Кологривый")
                .patronymic("Сергеевич")
                .dateOfBirth(LocalDate.of(1994, 10, 16))
                .gender("Мужской")
                .location("Москва")
                .post(new Post(1, "Intern"))
                .role(Role.builder().roleName("QA Tester").build())
                .specialization("Тестирование на Python")
                .addCompetences(new ArrayList<>())
                .build();
    }

    public static AddCompetence getAddKotlinCompetenceEntityTransient() {
        return AddCompetence.builder()
                .user(getStepanEntityPersisted())
                .name("Разработка на Kotlin")
                .role(Role.builder().roleName("Developer").build())
                .mark(1)
                .build();
    }

    public static AddCompetence getAddKotlinCompetenceEntityPersisted() {
        return AddCompetence.builder()
                .id(1)
                .user(getStepanEntityPersisted())
                .name("Разработка на Kotlin")
                .role(Role.builder().roleName("Developer").build())
                .mark(1)
                .build();
    }

    public static AddCompetence getJavaProgrammingCompetenceEntityTransient() {
        return AddCompetence.builder()
                .user(getStepanEntityPersisted())
                .name("Java Programming")
                .role(Role.builder().roleName("Developer").build())
                .mark(2)
                .build();
    }

    public static AddCompetence getJavaProgrammingCompetenceEntityPersisted() {
        return AddCompetence.builder()
                .id(2)
                .user(getStepanEntityPersisted())
                .name("Java Programming")
                .role(Role.builder().roleName("Developer").build())
                .mark(2)
                .build();
    }

    public static AddCompetence getJSDevelopmentCompetenceEntityTransient() {
        return AddCompetence.builder()
                .user(getNickEntityPersisted())
                .name("JavScript Development")
                .role(Role.builder().roleName("Developer").build())
                .mark(2)
                .build();
    }

    public static AddCompetence getJSDevelopmentCompetenceEntityPersisted() {
        return AddCompetence.builder()
                .id(3)
                .user(getNickEntityPersisted())
                .name("JavScript Development")
                .role(Role.builder().roleName("Developer").build())
                .mark(2)
                .build();
    }
}
