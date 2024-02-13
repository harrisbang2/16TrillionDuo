package com.sparta.duopleaseduo.controller;

import com.sparta.duopleaseduo.dto.request.CommentRequestDto;
import com.sparta.duopleaseduo.exception.commentexception.CommentCreateErrorException;
import com.sparta.duopleaseduo.exception.commentexception.CommentException;
import com.sparta.duopleaseduo.exception.commentexception.CommentUpdateFailException;
import com.sparta.duopleaseduo.exception.commentexception.IncorrectUserException;
import com.sparta.duopleaseduo.exception.userexception.NoSuchUserException;
import com.sparta.duopleaseduo.service.CommentLikeService;
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
    private CommentService commentService;
    private CommentLikeService likeService;
    /// add comments
    @PostMapping("/create/{feedId}")
    public ResponseEntity<?> CreateComment(@RequestBody CommentRequestDto requestDto,@PathVariable(name="feedId") Long feed_id, HttpServletRequest request, BindingResult bindingResult) throws CommentCreateErrorException {
        if(bindingResult.hasErrors()) {
            log.info("댓글 작성 오류");
            return ResponseEntity.badRequest()
                    .body(createErrorMessages(bindingResult));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(requestDto,feed_id,request));
    }

    /// get comments
//    @GetMapping("/comment")
//    public List<CommentResponseDto> getComments(@RequestParam(value = "ID") Long id){
//        return service.getComments(id);
//    }
    ///
    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable(name = "commentId") Long id, CommentRequestDto requestDt,HttpServletRequest request,BindingResult bindingResult) throws CommentException, NoSuchUserException {
        if(bindingResult.hasErrors()) {
            log.info("댓글 수정 오류");
            return ResponseEntity.badRequest()
                    .body(createErrorMessages(bindingResult));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.updateComment(id,requestDt,request));
    }
    // deleting the item.
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "commentId") Long id,HttpServletRequest request) throws NoSuchUserException, CommentException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.deleteComment(id,request));
    }

    // error message
    private List<String> createErrorMessages(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

    // comment like
    @PostMapping("/like/{commentId}")
    private ResponseEntity<?> addLike(@PathVariable(name ="commentId") Long commentId,HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(likeService.createComment(commentId,request));
    }

    @DeleteMapping("/like/{commentId}")
    private ResponseEntity<?> deleteLike(@PathVariable(name ="commentId") Long commentId,HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(likeService.deleteComment(commentId,request));
    }
}
