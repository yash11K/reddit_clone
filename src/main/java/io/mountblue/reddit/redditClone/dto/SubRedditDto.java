package io.mountblue.reddit.redditClone.dto;

import io.mountblue.reddit.redditClone.model.Flair;
import io.mountblue.reddit.redditClone.model.Rule;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class SubRedditDto {
    @Max(value = 21, message = "/r is less than 21characters")
    @Min(value = 3, message = "/r name should be at least 3 characters")
    String subRedditName;  //Cannot Contain whitespace,specialCharacters
    @Max(value = 500, message = "cannot be more than 500 characters")
    String subRedditDescription;
    MultipartFile banner;
    MultipartFile avatar;
    List<String> rules; // Rule has a Title, Description
    List<FlairDto> flairs; //
}
