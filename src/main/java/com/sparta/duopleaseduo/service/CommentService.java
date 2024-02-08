package com.sparta.duopleaseduo.service;

import com.sparta.duopleaseduo.dto.CommentRequestDto;
import com.sparta.duopleaseduo.dto.CommentResponseDto;
import com.sparta.duopleaseduo.entity.Comment;

import com.sparta.duopleaseduo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private CommentResponseDto commentResponseDto;

    // 추가
    public CommentResponseDto createComment(CommentRequestDto requestDto) { /// 유저랑 Feed 추가할 예정
        Comment comment;
        Comment savecomment;
        try {
            comment = new Comment(requestDto); // user feed 추가 예정
            savecomment = commentRepository.save(comment);
        }catch (Exception e){
             commentResponseDto = new CommentResponseDto();
            commentResponseDto.setCode(400);
            commentResponseDto.setMessage("댓글 생성 오류 실패!");
            return commentResponseDto;
        }
        // DB 저장

        // Entity -> ResponseDto
         commentResponseDto = new CommentResponseDto(savecomment);
        commentResponseDto.setCode(201);
        commentResponseDto.setMessage("댓글 생성 성공");
        return commentResponseDto;
    }
    // 조회
//    public List<CommentResponseDto> getComments(Long id) { /// 유저랑 Feed 추가할 예정
//         return null;
//    }
    ///// 수정
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto) {  /// 유저랑 Feed 추가할 예정 (User user , Feed feed)
        //  DB에 존재하는지 확인
        // 유저 확인.
        Comment comment;
        try {
            comment = findComment(id);
        }catch (Exception e){
            System.out.println("updateComment 에 없는 아이디 입니다");
            commentResponseDto.setCode(400);
            commentResponseDto.setStatus("Not Ok");
            commentResponseDto.setMessage("수정 / 검색 에러");
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
            return new CommentResponseDto(400);
        }
        commentResponseDto=new CommentResponseDto(comment);
        commentResponseDto.setCode(200);
        commentResponseDto.setMessage("수정 되었습니다");
        return commentResponseDto;
    }

    /// 삭제
    public CommentResponseDto deleteComment(Long id) { /// 유저랑 Feed 추가할 예정 (User user , Feed feed)

        Comment comment = findComment(id);
        // 유저 확인.
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

            return new CommentResponseDto(400);
        }
        return new CommentResponseDto(comment);
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(()-> new NullPointerException("그런 유저는 없어요"));
    }
}
