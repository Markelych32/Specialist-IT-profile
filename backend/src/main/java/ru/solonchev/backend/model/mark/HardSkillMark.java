package ru.solonchev.backend.model.mark;

import jakarta.persistence.*;
import lombok.*;
import ru.solonchev.backend.model.hard.HardSkill;
import ru.solonchev.backend.model.user.User;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "marks_of_users_hard_skills")
public class HardSkillMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hard_mark_id")
    private int hardMarkId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private HardSkill hardSkill;
    @Column(name = "mark", columnDefinition = "INT DEFAULT 0 CHECK (mark IN (0, 1, 2, 3))")
    private int mark;
}
