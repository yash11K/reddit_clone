package io.mountblue.reddit.redditClone.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "rule")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id")
    private Long ruleId;
    @Column(name = "rule")
    private String rule;
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "sub_reddit_id")
    private SubReddit subReddit;
}
