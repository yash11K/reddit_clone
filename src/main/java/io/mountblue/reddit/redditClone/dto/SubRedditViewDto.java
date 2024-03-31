package io.mountblue.reddit.redditClone.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubRedditViewDto {
    private Long subRedditId;
    private String subRedditName;
    String subRedditDescription;
    MultipartFile banner;
    MultipartFile avatar;
    List<String> rules;
    List<SubRedditPostDto> subRedditPostDtos;
}