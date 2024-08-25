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

    public static AddCompetence getAddCompetenceEntityTransient() {
        return AddCompetence.builder()
                .user(getStepanEntityPersisted())
                .name("Разработка на Kotlin")
                .role(Role.builder().roleName("Developer").build())
                .mark(1)
                .build();
    }

    public static AddCompetence getAddCompetenceEntityPersisted() {
        return AddCompetence.builder()
                .id(1)
                .user(getStepanEntityPersisted())
                .name("Разработка на Kotlin")
                .role(Role.builder().roleName("Developer").build())
                .mark(1)
                .build();
    }
}
