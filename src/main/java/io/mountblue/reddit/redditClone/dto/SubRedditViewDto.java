package io.mountblue.reddit.redditClone.dto;

import io.mountblue.reddit.redditClone.model.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SubRedditViewDto {
    private Long subRedditId;
    private String subRedditName;
    String subRedditDescription;
    String banner;
    String avatar;
    User opUser;
    boolean isSubscribed;
    List<String> rules;
    List<String> allSubReddits;
    List <String> previousSubRedditNames;
    private Long subscribedUsers;
    List<SubRedditPostDto> subRedditPostDtos;
}