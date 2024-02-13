package com.sparta.duopleaseduo.exception.userexception;

public class NoSuchUserException extends UserException{
    public NoSuchUserException(){
        super("해당 유저의 존재를 찾을수 없습니다.");
    }
}
