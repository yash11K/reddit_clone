package io.mountblue.reddit.redditClone.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "flair")
public class Flair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flairId;
    @Column(name = "flair_name")
    private String flairName;
    @Column(name = "color")
    private String color;
    @Column(name = "bg_color")
    private String bgColor;
    @Column(name = "text_color")
    private String textColor;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "subRedditId")
    private SubReddit subReddit;
}
