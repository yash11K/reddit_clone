package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.FlairDto;
import io.mountblue.reddit.redditClone.dto.RuleDto;
import io.mountblue.reddit.redditClone.dto.SubRedditDto;
import io.mountblue.reddit.redditClone.dto.SubRedditViewDto;
import io.mountblue.reddit.redditClone.model.Flair;
import io.mountblue.reddit.redditClone.model.Rule;
import io.mountblue.reddit.redditClone.model.SubReddit;
import io.mountblue.reddit.redditClone.service.SubRedditService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
        return "view-subreddit";
    }

    @PostMapping("/newsubreddit")
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
    public String updateBanner(@ModelAttribute("subRedditDto") SubRedditDto subRedditDto) {
        // Logic to update banner
        return "redirect:/"; // Redirect to homepage or appropriate page
    }

    @PostMapping("/update/avatar")
    public String updateAvatar(@ModelAttribute("subRedditDto") SubRedditDto subRedditDto) {
        // Logic to update avatar
        return "redirect:/"; // Redirect to homepage or appropriate page
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
        return "redirect:/r/microsoft"; // Redirect to Home Page or something
    }

    @PostMapping("/delete/rule")
    public String deleteRule(@RequestParam("subRedditName") String subRedditName, @RequestParam("rule") String rule) {
        SubReddit subReddit = subRedditService.show(subRedditName);
        Long ruleId = subRedditService.ruleId(subReddit, rule);
        subRedditService.deleteRule(ruleId);
        return "redirect:/r/update/" + subRedditName;
    }
}
