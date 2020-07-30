package com.market.exception;

//개발 후, 어플리케이션 운영상에서 발생할 수 있는 강요되지 않은예외의 최상위 객체
public class BusinessException extends RuntimeException{
	public BusinessException(Throwable e) {
		super(e);
	}
	public BusinessException(String msg) {
		super(msg);
	}
	public BusinessException(String msg, Throwable e) {
		super(msg, e);
	}
}
