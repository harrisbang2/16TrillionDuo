package com.sparta.duopleaseduo.exception.commentexception;

public class NoSuchLikeHistory extends CommentException{
    public NoSuchLikeHistory(){
        super("해당 댓글에 like 한적이 없습니다");
    }
}
