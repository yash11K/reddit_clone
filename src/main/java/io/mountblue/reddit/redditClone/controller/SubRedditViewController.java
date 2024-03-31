package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.SubRedditViewDto;
import io.mountblue.reddit.redditClone.model.SubReddit;
import io.mountblue.reddit.redditClone.service.SubRedditService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/r")
public class SubRedditViewController {

    private final SubRedditService subRedditService;

    @GetMapping("/{subRedditName}")
    public String viewSubReddit(@PathVariable String subRedditName, Model model) {
        SubReddit subReddit = subRedditService.show(subRedditName);
        SubRedditViewDto subRedditViewDto =  subRedditService.subRedditToSubRedditViewDto(subReddit);
        model.addAttribute("subRedditViewDto", subRedditViewDto);
        System.out.println(subRedditViewDto);
        return "view-subreddit";
    }
}
