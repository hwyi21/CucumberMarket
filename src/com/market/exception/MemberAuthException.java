package com.market.exception;

public class MemberAuthException extends InvalidException{

	public MemberAuthException(String msg) {
		super(msg);
	}
	
	public MemberAuthException(Throwable e) {
		super(e);
	}
	
	public MemberAuthException(String msg, Throwable e) {
		super(msg, e);
	}
}
