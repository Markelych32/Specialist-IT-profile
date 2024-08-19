package ru.solonchev.backend.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.solonchev.backend.model.mark.SoftSkillMark;
import ru.solonchev.backend.model.role.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name", nullable = false, length = 128)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 128)
    private String lastName;
    @Column(length = 128)
    private String patronymic;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @Column(nullable = false, length = 1)
    private String gender;
    @Column(nullable = false, length = 128)
    private String city;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @Column(nullable = false, length = 512)
    private String specialization;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SoftSkillMark> softSkillMarks = new ArrayList<>();
}
