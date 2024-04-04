package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.*;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.model.Rule;
import io.mountblue.reddit.redditClone.model.SubReddit;
import io.mountblue.reddit.redditClone.model.User;
import io.mountblue.reddit.redditClone.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/r")
public class SubRedditViewController {

    private final SubRedditService subRedditService;
    private final MediaService mediaService;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final VoteService voteService;

    @GetMapping("/{subRedditName}")
    public String viewSubReddit(@PathVariable String subRedditName, Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        UserDto userDto = UserDto.builder().username(user.getUsername()).mediaUri("/beanheads/"+user.getProfilePic())
                .karma(postService.getPostCountByUser(user) + commentService.getUserCommentCount(user))
                .joined(user.getJoinDate())
                .build();
        model.addAttribute("userDto", userDto);
        SubReddit subReddit = subRedditService.show(subRedditName);
        SubRedditViewDto subRedditViewDto =  subRedditService.subRedditToSubRedditViewDto(subReddit, principal);
        model.addAttribute("subRedditViewDto", subRedditViewDto);
        return "view-subreddit";
    }

    @GetMapping("/form/new")
    public String showCreateSubForm(SubRedditViewDto subRedditDto, Model model){
        model.addAttribute("subRedditDto", subRedditDto);
        return "new-sub";
    }

    @PostMapping("/submit/r")
    public String createSubredditPlain(@ModelAttribute SubRedditViewDto subRedditViewDto, Principal principal){
        subRedditService.saveSub(SubReddit.builder().subRedditName(subRedditViewDto.getSubRedditName())
                .description(subRedditViewDto.getSubRedditDescription())
                .modUser(userService.findByUsername(principal.getName()))
                .build());
        return "redirect:/r/" + subRedditViewDto.getSubRedditName();
    }

    @PostMapping("/sub/new")
    public String createSubReddit(@RequestParam String newSubRedditName, Principal principal) {
        SubRedditDto subRedditDto = new SubRedditDto();
        subRedditDto.setSubRedditName(newSubRedditName);
        subRedditService.save(subRedditDto, principal);
        return "redirect:/r/" + newSubRedditName;
    }

    @GetMapping("/update/{subRedditName}")
    public String getUpdateSubRedditForm(@PathVariable String subRedditName, Model model) {
        SubReddit subReddit = subRedditService.show(subRedditName);
        SubRedditDto subRedditDto = new SubRedditDto();
        subRedditDto.setSubRedditName(subReddit.getSubRedditName());
        subRedditDto.setSubRedditDescription(subReddit.getDescription());
        List<String> subRedditRules = new ArrayList<>();
        if (subReddit.getRules() != null) {
            for (Rule rules : subReddit.getRules()) {
                subRedditRules.add(rules.getRule());
            }
        }
        subRedditDto.setRules(subRedditRules);
        System.out.println(subRedditDto);
        RuleDto ruleDto = new RuleDto();
        FlairDto flairDto = new FlairDto();
        model.addAttribute("subRedditName" , subReddit.getSubRedditName());
        model.addAttribute("subRedditDto" , subRedditDto);
        model.addAttribute("ruleDto" , ruleDto);
        model.addAttribute("flairDto" , flairDto);
        return "update_subreddit_form";
    }

    @PostMapping("/update/banner")
    public String updateBanner(@ModelAttribute("subRedditDto") SubRedditDto subRedditDto, @RequestPart(name = "media") MultipartFile media) {
        String uri = mediaService.uploadMediaToBucket(media, subRedditDto.getSubRedditName());
        SubReddit subReddit = subRedditService.findSubRedditByName(subRedditDto.getSubRedditName()).orElseThrow();
        subReddit.setBanner(uri);
        subRedditService.saveDirectSubReddit(subReddit);
        return "redirect:/r/" + subRedditDto.getSubRedditName(); // Redirect to homepage or appropriate page
    }

    @PostMapping("/update/avatar")
    public String updateAvatar(@ModelAttribute("subRedditDto") SubRedditDto subRedditDto, @RequestPart(name = "media") MultipartFile media) {
        String uri = mediaService.uploadMediaToBucket(media, subRedditDto.getSubRedditName());
        SubReddit subReddit = subRedditService.findSubRedditByName(subRedditDto.getSubRedditName()).orElseThrow();
        subReddit.setAvatar(uri);
        subRedditService.saveDirectSubReddit(subReddit);
        return "redirect:/r/" + subRedditDto.getSubRedditName(); // Redirect to homepage or appropriate page
    }

    @PostMapping("/update/description")
    public String updateDescription(@ModelAttribute("subRedditDto") SubRedditDto subRedditDto, @RequestParam String subRedditName) {
        subRedditDto.setSubRedditName(subRedditName);
        subRedditService.update(subRedditDto, subRedditName);
        System.out.println(subRedditDto);
        return "redirect:/r/update/" + subRedditName; // Redirect to homepage or appropriate page
    }

    @PostMapping("/update/rules")
    public String addRules(@ModelAttribute("ruleDto") RuleDto ruleDto, @RequestParam String subRedditName) {
        subRedditService.saveRule(ruleDto,subRedditName);
        System.out.println(ruleDto);
        return "redirect:/r/update/" + subRedditName; // Redirect to homepage or appropriate page
    }

    @PostMapping("/delete/{subRedditName}")
    public String deleteSubReddit(@PathVariable String subRedditName) {
        SubReddit subReddit = subRedditService.show(subRedditName);
        subRedditService.delete(subReddit.getSubRedditId());
        return "redirect:/feed/all"; // Redirect to Home Page or somethingC
    }

    @PostMapping("/delete/rule")
    public String deleteRule(@RequestParam("subRedditName") String subRedditName, @RequestParam("rule") String rule) {
        SubReddit subReddit = subRedditService.show(subRedditName);
        Long ruleId = subRedditService.ruleId(subReddit, rule);
        subRedditService.deleteRule(ruleId);
        return "redirect:/r/update/" + subRedditName;
    }

    @PostMapping("/updatejoinstatus/{subRedditName}")
    public String updateJoinStatus(@PathVariable String subRedditName, Principal principal) {
        String username = principal.getName();
        subRedditService.updateJoinStatus(subRedditName, username);

        return "redirect:/r/" + subRedditName;
    }
    @PostMapping("/voting")
    public String voting(
            @RequestParam(name = "postId") Long postId,
            @RequestParam(name = "votetype") String voteType,
            Model model
    ) {
        Post post = postService.fetchPostById(postId);
        voteService.votes(post, voteType);
        FullPostViewDto fullPostViewDto = postService.postToFullViewPostDto(post);
        String subRedditName = post.getSubReddit().getSubRedditName();
        Long voteCounts = post.getVoteCount();
        model.addAttribute("fullPostViewDto", fullPostViewDto);
        model.addAttribute("votesCount", voteCounts);
        return "redirect:/r/" + subRedditName;
    }
}
