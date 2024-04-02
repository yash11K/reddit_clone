package io.mountblue.reddit.redditClone.dto;

import io.mountblue.reddit.redditClone.model.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SubRedditPostDto {
    private Long postId;
    private User opUser;
    private String title;
    private String body;
    private MultipartFile mediaUri;
    private String createdAt;
    private Long voteCount;
    private Long commentCount;
}
