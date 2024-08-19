package ru.solonchev.backend.model.mark;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.solonchev.backend.model.soft.SoftSkill;
import ru.solonchev.backend.model.user.User;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "marks_of_users_soft_skills")
public class SoftSkillMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soft_mark_id")
    private int softMarkId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private SoftSkill softSkill;
    @Column(name = "mark", columnDefinition = "INT DEFAULT 0 CHECK (mark IN (-1, 0, 1, 2, 3))")
    private int mark;
}
