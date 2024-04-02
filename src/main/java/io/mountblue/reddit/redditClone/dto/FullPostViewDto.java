package io.mountblue.reddit.redditClone.dto;

import io.mountblue.reddit.redditClone.model.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FullPostViewDto {
    private Long postId;
    private Post post;
    private SubReddit subReddit;
    private LocalDateTime createdAt;
    private User opUser;
    private String title;
    private List<Flair> flairs;
    private String body;
    private String mediaUri;
    private Long voteCount;
    private Long CommentCount;
    private List<Comment> comments;

//    might be needed i don't know for now
//    private boolean isCommentsMediaAllowed;
//    private Double popularityScore;

}
