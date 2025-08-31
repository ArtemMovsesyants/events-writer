package com.amovsesyants.events.writer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_points", nullable = false)
    private Integer activityPoints;

    @Enumerated(EnumType.STRING)
    @Column(name = "reward_level", nullable = false)
    private RewardLevel rewardLevel;

    @CreationTimestamp
    private Instant createdAt;
}