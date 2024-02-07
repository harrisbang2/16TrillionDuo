package com.sparta.duopleaseduo.controller;

import com.sparta.duopleaseduo.dto.CommentRequestDto;
import com.sparta.duopleaseduo.dto.CommentResponseDto;
import com.sparta.duopleaseduo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    @Autowired
    private CommentService service;
    /// add comments
    @PostMapping("/comment/create")
    public CommentResponseDto CreateComment(@RequestBody CommentRequestDto requestDto) {
        return service.createComment(requestDto);
    }

    /// get comments
//    @GetMapping("/comment")
//    public List<CommentResponseDto> getComments(@RequestParam(value = "ID") Long id){
//        return service.getComments(id);
//    }
    ///
    @PatchMapping("/comment/{commentId}")
    public Long updateComment(@PathVariable(name = "commentId") Long id, CommentRequestDto requestDt) {
        return service.updateComment(id,requestDt);
    }
    // deleting the item.
    @DeleteMapping("/comment/{commentId}")
    public CommentResponseDto deleteComment(@PathVariable(name = "commentId") Long id) {
        return service.deleteComment(id);
    }
}
