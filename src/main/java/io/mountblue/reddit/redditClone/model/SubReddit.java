package io.mountblue.reddit.redditClone.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class SubReddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_reddit_id")
    private Long subRedditId;
    private String profilePicture;
    private Date createdAt;
    private String backgroundPicture;

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

    @ManyToMany(mappedBy = "subscribedSubReddits")
    private List<User> subscribedUsers;

    @OneToMany(mappedBy = "subReddit", cascade = CascadeType.ALL)
    private List<Flair> flairs;

}