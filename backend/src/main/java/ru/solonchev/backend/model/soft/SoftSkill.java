package ru.solonchev.backend.model.soft;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "soft_skills")
public class SoftSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "skill_name", nullable = false, unique = true, length = 512)
    private String skillName;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private SoftGroup softGroup;

}
