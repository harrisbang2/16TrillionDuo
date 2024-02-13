package com.sparta.duopleaseduo.exception.commentexception;

public class CommentUpdateFailException extends CommentException{
    public CommentUpdateFailException(){
        super("댓글을 업데이트 할수가 없어요");
    }
}
