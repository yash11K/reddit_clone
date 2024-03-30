package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.FlairDto;
import io.mountblue.reddit.redditClone.dto.RuleDto;
import io.mountblue.reddit.redditClone.dto.SubRedditDto;
import io.mountblue.reddit.redditClone.model.Flair;
import io.mountblue.reddit.redditClone.model.SubReddit;
import io.mountblue.reddit.redditClone.service.SubRedditService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/r")
public class SubRedditController {
    private SubRedditService subRedditService;

    @PostMapping("/new")
    public SubRedditDto newSubReddit(@RequestBody SubRedditDto subRedditDto, Principal principal) {
        return subRedditService.save(subRedditDto, principal);

    }

    @PostMapping("/update/{subRedditName}")
    public SubReddit updateSubReddit(@RequestBody SubRedditDto subRedditDto, @PathVariable(value = "subRedditName" ) String subRedditName) {
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

    @PostMapping("/{subRedditName}/rule/new")
    public SubReddit saveRule(@PathVariable String subRedditName, @RequestBody RuleDto ruleDto){
        return subRedditService.saveRule(ruleDto, subRedditName);
    }

    @PostMapping("/{subRedditName}/flair/new")
    public SubReddit saveFlair(@PathVariable String subRedditName, @RequestBody FlairDto flairDto){
        return subRedditService.saveFlair(flairDto, subRedditName);
    }
}
