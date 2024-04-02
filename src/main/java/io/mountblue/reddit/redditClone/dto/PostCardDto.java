package io.mountblue.reddit.redditClone.dto;

import io.mountblue.reddit.redditClone.model.Flair;
import io.mountblue.reddit.redditClone.model.VOTE_TYPE;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
public class PostCardDto {
    private Long postId;
    private String subRedditName; // selected subRedditName for post
    private String title;
    private String body;
    private String media; //multiple media
    private List<Flair> flairs; //pre-populated dropdown
    private String createdAt;
    private int voteCount;
    private int commentCount;
    private VOTE_TYPE userVote;
}
