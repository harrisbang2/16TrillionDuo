package com.sparta.duopleaseduo.service;

import com.sparta.duopleaseduo.dto.request.CommentRequestDto;
import com.sparta.duopleaseduo.dto.response.CommentResponseDto;
import com.sparta.duopleaseduo.entity.Comment;

import com.sparta.duopleaseduo.jwt.JwtUtil;
import com.sparta.duopleaseduo.repository.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private CommentResponseDto commentResponseDto;

    private JwtUtil jwtUtil;

    // 추가
    public CommentResponseDto createComment(CommentRequestDto requestDto,HttpServletRequest request) { /// 유저랑 Feed 추가할 예정
        Comment comment;
        Comment savecomment;
        System.out.println(jwtUtil.validateTokenAndGetUserName(request));
        try {
            comment = new Comment(requestDto); // user feed 추가 예정
            savecomment = commentRepository.save(comment);
        }catch (Exception e){
             commentResponseDto = new CommentResponseDto();
            return commentResponseDto;
        }
        // DB 저장
        // Entity -> ResponseDto
         commentResponseDto = new CommentResponseDto(savecomment);
        return commentResponseDto;
    }
    // 조회
//    public List<CommentResponseDto> getComments(Long id) { /// 유저랑 Feed 추가할 예정
//         return null;
//    }
///// 수정
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {  /// 유저랑 Feed 추가할 예정 (User user , Feed feed)
        //  DB에 존재하는지 확인
        // 유저 확인.
        Comment comment;
        try {
            comment = findComment(id);
        }catch (Exception e){
            System.out.println("updateComment 에 없는 아이디 입니다");
            return  commentResponseDto;
        }
//        if(comment.getUser().getId().equals(user.getID) && comment.getFeed().getId().equals(feed.getID){
//            //  내용 수정
//            comment.update(requestDto);
//        }
//        else {
//            throw new IllegalStateException("유저가 다릅니다!!!!");
//        }
        try{
            comment.update(requestDto);
            commentRepository.save(comment);
        } catch (Exception e){
            System.out.println("updateComment 에서 오류");
        }
        commentResponseDto=new CommentResponseDto(comment);
        return commentResponseDto;
    }

    /// 삭제
    public CommentResponseDto deleteComment(Long id, HttpServletRequest request) { /// 유저랑 Feed 추가할 예정 (User user , Feed feed)

        Comment comment = findComment(id);
//      // 유저 확인.
//        if(comment.getUser().getId().equals(user.getID) && comment.getFeed().getId().equals(feed.getID){
//            // memo 삭제
//            commentRepository.delete(comment);
//        }
//        else {
//            throw new IllegalStateException("삐빅 ! 유저 혹은 feed 가 다릅니다!!!!");
//        }
        try{
            commentRepository.delete(comment);
        } catch (Exception e){
            System.out.println("deleteComment 에서 오류");
            return new CommentResponseDto();
        }
        return new CommentResponseDto(comment);
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(()-> new NullPointerException("그런 유저는 없어요"));
    }
}
