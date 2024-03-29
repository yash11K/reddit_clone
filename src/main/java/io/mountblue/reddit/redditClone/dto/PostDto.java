package io.mountblue.reddit.redditClone.dto;

import io.mountblue.reddit.redditClone.model.SubReddit;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
public class PostDto {
    @Min(value = 1, message = "a community is required")
    private String subRedditName; // selected subRedditName for post
    @Max(value = 300, message = "title should be less than 300 characters")
    private String title;
    @Max(value = 4000, message = "post body cannot exceed 4000 characters")
    private String body;
    private List<MultipartFile> media; //multiple media
    private List<String> Flair; //pre-populated dropdown
    private List<SubReddit> subRedditSubscribed; //for giving r/ options
    private boolean isPublished;
}

// Body might be an <html> instead of String
