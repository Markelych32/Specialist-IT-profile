package ru.solonchev.backend.model.hard;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hard_indicators")
public class HardIndicator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "indicator_name", unique = true, nullable = false)
    private String indicatorName;
}
