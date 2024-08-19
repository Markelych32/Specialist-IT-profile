package ru.solonchev.backend.model.hard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.solonchev.backend.model.role.Role;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hard_skills")
public class HardSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "skill_name", unique = true, nullable = false)
    private String skillName;
    @Column(name = "grade_method", nullable = false)
    private String gradeMethod;
    @Column(name = "develop_method", nullable = false)
    private String developMethod;
    @ManyToMany
    @JoinTable(
            name = "hard_skills_roles",
            joinColumns = @JoinColumn(name = "skill_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "skill_id")
    private List<HardIndicator> indicators = new ArrayList<>();

    public void addRole(Role role) {
        roles.add(role);
        role.getHardSkills().add(this);
    }

    public void deleteRole(Role role) {
        role.getHardSkills().remove(this);
        roles.remove(role);
    }
}
