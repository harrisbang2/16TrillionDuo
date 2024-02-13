package com.sparta.duopleaseduo.exception.commentexception;

public class NoSuchLikedHistory extends CommentException{
    public NoSuchLikedHistory(){
        super("해당 댓글에 like 한적이 없습니다");
    }
}
