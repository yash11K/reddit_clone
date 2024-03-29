package io.mountblue.reddit.redditClone.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "sub_reddit_id")
    private SubReddit subReddit;

    @ManyToMany(mappedBy = "flairs", cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    List<Post> posts;
}
