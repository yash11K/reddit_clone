package io.mountblue.reddit.redditClone.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sub_reddit")
public class SubReddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_reddit_id")
    private Long subRedditId;
    private String avatar;
    private LocalDateTime createdAt;
    private String banner;

    @Column(name = "sub_reddit_name")
    private String subRedditName;

    @Column(name = "sub_reddit_description")
    private String description;

    @OneToMany(mappedBy = "subReddit")
    private List<Rule> rules;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User modUser;

    @OneToMany(mappedBy = "subreddit", cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToMany(mappedBy = "subscribedSubReddits", cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    private List<User> subscribedUsers;

    @OneToMany(mappedBy = "subReddit", cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    private List<Flair> flairs;

}