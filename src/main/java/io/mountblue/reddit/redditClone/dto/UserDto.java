package io.mountblue.reddit.redditClone.dto;

import io.mountblue.reddit.redditClone.model.GENDER;
import io.mountblue.reddit.redditClone.model.Topic;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotEmpty(message = "username is required")
    private String username; //Cac be prePopulated with redditApi
//    @Min(value = 4, message = "more than 3 characters")
    private String password;
    private GENDER gender;
    private String mediaUri;
    private List<Topic> interests;
    private List<Long> interestIds;
    private String email;
    private LocalDateTime joined;
    private int karma;
}
