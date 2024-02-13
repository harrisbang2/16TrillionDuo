package com.sparta.duopleaseduo.exception.commentexception;

public class IncorrectUserException extends CommentException{
    public IncorrectUserException(){
        super("댓글을 작성한 유저가 아닙니다!");
    }
}
