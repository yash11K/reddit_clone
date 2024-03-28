package io.mountblue.reddit.redditClone.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
public class CommentDto {
    private String postId; // base post of comment (get from @PathVariable)
    private String commentId; //only if subComment (get from @PathVariable)
    @Max(value = 4000, message = "comment should be less than 4000 characters")
    private String text;
    private List<MultipartFile> media; //only if Comments.isCommentsMediaAllowed
}
