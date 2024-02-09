package com.sparta.duopleaseduo.controller;

import com.sparta.duopleaseduo.dto.request.CommentRequestDto;
import com.sparta.duopleaseduo.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService service;
    /// add comments
    @PostMapping("/create/{feedId}")
    public ResponseEntity<?> CreateComment(@RequestBody CommentRequestDto requestDto,@PathVariable(name="feedId") Long feed_id, HttpServletRequest request, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.info("댓글 작성 오류");
            return ResponseEntity.badRequest()
                    .body(createErrorMessages(bindingResult));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createComment(requestDto,feed_id,request));
    }

    /// get comments
//    @GetMapping("/comment")
//    public List<CommentResponseDto> getComments(@RequestParam(value = "ID") Long id){
//        return service.getComments(id);
//    }
    ///
    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable(name = "commentId") Long id, CommentRequestDto requestDt,HttpServletRequest request,BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.info("댓글 수정 오류");
            return ResponseEntity.badRequest()
                    .body(createErrorMessages(bindingResult));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.updateComment(id,requestDt,request));
    }
    // deleting the item.
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "commentId") Long id,HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.deleteComment(id,request));
    }



    // error message
    private List<String> createErrorMessages(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }


}
