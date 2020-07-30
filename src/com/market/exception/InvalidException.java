package com.market.exception;
/*
 * throw 로 발생시킬 수 있는 예외의 종류
 * 
 * 1) Throwable 인터페이스
 * 2) Exception : 예외처리를 강요한다 (catched Exception)
 * 3) RuntimeException : 예외처리를 강제하지 않음 (uncathed Exception)
 * 
 * */

public class InvalidException extends BusinessException{
   public InvalidException(String msg) {
      super(msg);
   }
   public InvalidException(Throwable e) {
      super(e);
   }
   public InvalidException(String msg, Throwable e) {
      super(msg, e);
   }
   
}