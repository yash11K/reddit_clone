package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.CommentDto;
import io.mountblue.reddit.redditClone.dto.FullPostViewDto;
import io.mountblue.reddit.redditClone.model.Comment;
import io.mountblue.reddit.redditClone.model.CommentTree;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.service.CommentManager;
import io.mountblue.reddit.redditClone.service.CommentService;
import io.mountblue.reddit.redditClone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/r")
public class FullPostViewController {
    private final PostService postService;
    private CommentTree commentTree;
    private CommentService commentService;

    @GetMapping("/{subRedditName}/comments/{postId}")
    public String viewComments(
            @PathVariable Long postId,
            Model model
    ) {
        Post post = postService.fetchPostById(postId);
        FullPostViewDto fullPostViewDto = postService.postToFullViewPostDto(post);
//        List<Comment> comments = postService.findAllCommentsByPostId(postId);
//        for(Comment comment : comments) {
//            Map.Entry<Comment, List<Comment>> entry = commentTree.getParentChildMap().entrySet().iterator().next();
//            List<Comment> values = entry.getValue();
//            for(Comment value : values) {
//                commentTree.fetchComment(comment);
//            }
//        }
        model.addAttribute("fullPostViewDto", fullPostViewDto);
//        model.addAttribute("comments", comments);
        return "view-comments";
    }

    @PostMapping("/comment/add/{postId}")
    public String addComment(
            @PathVariable Long postId,
            @RequestParam(name = "commentText") String commentText,
            Model model
    ) {
        Post post = postService.fetchPostById(postId);
        CommentDto commentDto = new CommentDto();
        commentDto.setPostId(postId);
        commentDto.setText(commentText);
        commentService.save(commentDto, postId);
        FullPostViewDto fullPostViewDto = postService.postToFullViewPostDto(post);
        model.addAttribute("fullPostViewDto", fullPostViewDto);
        return "view-comments";
    }

    @PostMapping("/edit/comment/{postId}")
    public String editComment(
            @PathVariable("postId") Long postId,
            @RequestParam("commentId") Long commentId
    ) {
//        commentService.editComment(commentId, commentText);
//        return "redirect:/your-redirect-url";
        return "view-comments";
    }

    @PostMapping("/delete/comment/{postId}")
    public String deleteComment(
            @RequestParam("commentId") Long commentId,
            @PathVariable(name = "postId") Long postId,
            Model model
    ) {
        commentService.delete(commentId);
        Post post = postService.fetchPostById(postId);
        FullPostViewDto fullPostViewDto = postService.postToFullViewPostDto(post);
        model.addAttribute("fullPostViewDto", fullPostViewDto);
        return "view-comments";
    }

}