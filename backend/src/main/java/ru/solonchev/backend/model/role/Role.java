package ru.solonchev.backend.model.role;

import jakarta.persistence.*;
import lombok.*;
import ru.solonchev.backend.model.hard.HardSkill;
import ru.solonchev.backend.model.user.AddCompetence;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName;
    @Column(name = "description", nullable = false)
    private String description;
    @ManyToMany(mappedBy = "roles")
    private List<HardSkill> hardSkills = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
    private List<AddCompetence> addCompetences = new ArrayList<>();

    public void addHardSkill(HardSkill hardSkill) {
        hardSkills.add(hardSkill);
        hardSkill.getRoles().add(this);
    }

    public void deleteHardSkill(HardSkill hardSkill) {
        hardSkill.getRoles().remove(this);
        hardSkills.remove(hardSkill);
    }
}
