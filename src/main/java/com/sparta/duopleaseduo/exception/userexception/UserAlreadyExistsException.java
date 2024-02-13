package com.sparta.duopleaseduo.exception.userexception;

public class UserAlreadyExistsException extends UserException{
    public UserAlreadyExistsException(){
        super("회원 이메일 중복, 이미 존재하는 email 입니다.");
    }
}
