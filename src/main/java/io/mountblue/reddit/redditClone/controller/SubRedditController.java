package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.SubRedditDto;
import io.mountblue.reddit.redditClone.model.SubReddit;
import io.mountblue.reddit.redditClone.service.SubRedditService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/subreddit")
public class SubRedditController {
    private SubRedditService subRedditService;

    @PostMapping("/new")
    public SubRedditDto newSubReddit(@RequestBody SubRedditDto subRedditDto, Principal principal) {
        return subRedditService.save(subRedditDto, principal);

    }

    @PostMapping("/update/{subRedditName}")
    public SubRedditDto updateSubReddit(@RequestBody SubRedditDto subRedditDto, @PathVariable(value = "subRedditName" ) String subRedditName) {
        return subRedditService.update(subRedditDto, subRedditName);
    }

    @GetMapping("/{subRedditName}")
    public SubReddit showSubReddit(@PathVariable(value = "subRedditName" ) String subRedditName) {
        return subRedditService.show(subRedditName);
    }

    @GetMapping("/create")
    public SubRedditDto newSubRedditForm() {
        return new SubRedditDto();
    }

    @DeleteMapping("delete/{subRedditId}")
    public void deleteSubReddit(@PathVariable(value = "subRedditId" ) Long subRedditId) {
        subRedditService.delete(subRedditId);
    }

}
