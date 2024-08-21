package ru.solonchev.backend.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.solonchev.backend.model.role.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "add_competences")
public class AddCompetence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false, length = 512, unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @Column(name = "mark", nullable = false, columnDefinition = "INT DEFAULT 1 CHECK (mark IN (0, 1, 2, 3))")
    private int mark;
}
