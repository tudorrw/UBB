package com.vvss.FlavorFiesta.controllers;

import com.vvss.FlavorFiesta.models.Comment;
import com.vvss.FlavorFiesta.services.CommentService;
import com.vvss.FlavorFiesta.util.OwnershipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private OwnershipUtil ownershipUtil;

    @GetMapping("/")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping("/")
    public Comment addComment(@RequestBody Comment comment) {
        return commentService.saveComment(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        if (comment != null) {
            if (ownershipUtil.loggedInUserIsCommentOwner(comment)) {
                commentService.deleteComment(comment);
            } else {
                throw new ResponseStatusException(FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
    }
}