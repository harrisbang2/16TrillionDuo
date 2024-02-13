package com.sparta.duopleaseduo.exception.commentexception;

public class NoSuchCommentException extends CommentException{
    public NoSuchCommentException(){
        super("존재 하지 않는 댓글입니다.");
    }
}
