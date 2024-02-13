package com.sparta.duopleaseduo.exception.commentexception;

public class CommentCreateErrorException extends CommentException{
    public CommentCreateErrorException(){
        super("댓글 을 저장하는데 오류가 발생하였습니다.");
    }
}
