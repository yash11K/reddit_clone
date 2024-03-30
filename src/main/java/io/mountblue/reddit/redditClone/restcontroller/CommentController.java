package io.mountblue.reddit.redditClone.restcontroller;

import io.mountblue.reddit.redditClone.dto.CommentDto;
import io.mountblue.reddit.redditClone.model.Comment;
import io.mountblue.reddit.redditClone.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/all")
    public List<Comment> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/{commentId}")
    public Comment getCommentById(
            @PathVariable Long commentId
    ) {
        return commentService.getCommentById(commentId);
    }

    @PostMapping("/create/{postId}")
    public Comment newComment(
            @RequestBody CommentDto commentDto,
            @PathVariable(name = "postId") Long postId
    ) {
        return commentService.save(commentDto, postId);
    }

    @PutMapping("/update/{commentId}")
    public Comment updateComment(
            @PathVariable(name = "commentId") Long commentId,
            @RequestBody CommentDto commentDto
    ) {
        return commentService.updateComment(commentId, commentDto);
    }

    @DeleteMapping("/delete/{commentId}")
    public String deleteComment(
            @PathVariable Long commentId
    ) {
        commentService.delete(commentId);
        return "deleted successfully";
    }
}
