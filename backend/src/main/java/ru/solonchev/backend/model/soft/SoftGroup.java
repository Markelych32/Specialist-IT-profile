package ru.solonchev.backend.model.soft;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "soft_groups")
public class SoftGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "group_name", nullable = false, unique = true, length = 512)
    private String groupName;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "softGroup")
    private List<SoftSkill> skills = new ArrayList<>();
}
