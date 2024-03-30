package io.mountblue.reddit.redditClone.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flair")
public class Flair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flair_id")
    private Long flairId;
    @Column(name = "flair_name")
    private String flairName;
    @Column(name = "color")
    private String color;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sub_reddit_id")
    private SubReddit subReddit;

    @JsonIgnore
    @ManyToMany(mappedBy = "flairs", cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    List<Post> posts;
}
