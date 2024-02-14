package com.sparta.duopleaseduo.exception.userexception;

public class IncorrectPasswordException extends UserException{
    public IncorrectPasswordException(){
        super("아이디,비밀번호가 일치하지 않습니다.");
    }
}
