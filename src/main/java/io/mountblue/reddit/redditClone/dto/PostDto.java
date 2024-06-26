package io.mountblue.reddit.redditClone.dto;

import io.mountblue.reddit.redditClone.model.SubReddit;
import io.mountblue.reddit.redditClone.model.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class PostDto {
    @Min(value = 1, message = "a community is required")
    private String subRedditName; // selected subRedditName for post
    @Max(value = 300, message = "title should be less than 300 characters")
    private String title;
    @Max(value = 4000, message = "post body cannot exceed 4000 characters")
    private String body;
    private List<String> flairs; //pre-populated dropdown
    private List<SubReddit> subRedditSubscribed; //for giving r/ options
    private List<String> topics;
    private boolean isCommentMediaAllowed;
    private boolean isPublished;
    private User op;
    @Override
    public String toString() {
        return "PostDto{" +
                "subRedditName='" + subRedditName + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", flair=" + flairs +
                ", subRedditSubscribed=" + subRedditSubscribed +
                ", topics=" + topics +
                ", isCommentMediaAllowed=" + isCommentMediaAllowed +
                ", isPublished=" + isPublished +
                '}';
    }
}

// Body might be an <html> instead of String
