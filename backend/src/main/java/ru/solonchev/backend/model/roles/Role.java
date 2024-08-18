package ru.solonchev.backend.model.roles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.solonchev.backend.model.hard.HardSkill;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    public void addHardSkill(HardSkill hardSkill) {
        hardSkills.add(hardSkill);
        hardSkill.getRoles().add(this);
    }

    public void deleteHardSkill(HardSkill hardSkill) {
        hardSkill.getRoles().remove(this);
        hardSkills.remove(hardSkill);
    }
}
