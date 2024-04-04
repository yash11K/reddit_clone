package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.PostDto;
import io.mountblue.reddit.redditClone.mapper.PostMapper;
import io.mountblue.reddit.redditClone.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

import static java.util.Collections.replaceAll;

@AllArgsConstructor
@Controller
public class PostViewController {
    PostService postService;
    TopicService topicService;
    SubRedditService subRedditService;
    FlairService flairService;
    PostMapper postMapper;
    MediaService mediaService;
    UserService userService;

    @Value("${reddit.topics}")
    List<String> topics;

    @GetMapping("/post/new")
    public String createNewPost(Model model){
        PostDto postDto = new PostDto();
        model.addAttribute("postDto" , postDto);
        model.addAttribute("subRedditNames", subRedditService.fetchAllSubRedditNames());
        return "/create-post/create-post-root";
    }

    @PostMapping("/submit/post")
    public String submitPost(@ModelAttribute PostDto postDto,
                             Model model){
        if(postDto.getTitle()==null){
            return "redirect:/post/new";
        }
        postDto.setBody(postDto.getBody().replaceAll("\\r?\\n", "<br>"));
        model.addAttribute("postDto", postDto);
        model.addAttribute("topics", topics);
        model.addAttribute("flairs", flairService.fetchSubredditFlairNames(postDto.getSubRedditName()));
        return "/create-post/create-post-sides";
    }

    @PostMapping("/submit/sides")
    public String submitPostSides(@ModelAttribute PostDto postDto, @RequestPart(name = "media") MultipartFile media, Principal principal){
        postDto.setPublished(true);
        String uri = null;
        if(!media.isEmpty()){
            uri = mediaService.uploadMediaToBucket(media, postDto.getSubRedditName());
        }
        postDto.setOp(userService.findByUsername(principal.getName()));
        postService.save(postMapper.newPostMapper(postDto, uri));
        System.out.println(postDto.toString());
        return "redirect:/feed/all";
    }
}
