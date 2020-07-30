package com.market.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;

import com.market.exception.MemberAuthException;

//로그인 여부 세션 체크
public class MemberSessionCheckAspect {
   public Object sessionCheck(ProceedingJoinPoint joinPoint) throws Throwable{
      Object result = null; 
      //로그인 한 사람 : proceed()메서드의 반환값
      //로그인 안 한 사람 : view/error
      Class target= joinPoint.getTarget().getClass();
      
      Object[] args = joinPoint.getArgs();
      
      HttpServletRequest request = null;
      HttpSession session = null;
      
      for(int i=0; i<args.length; i++) {
         if(args[i] instanceof HttpServletRequest) {
            request=(HttpServletRequest) args[i];
         }
      }
      String uri = request.getRequestURI();
      
      if(uri.equals("/product")) {
         result=joinPoint.proceed();
      }else {
         if(request != null) { 
            session = request.getSession();
            if(session.getAttribute("member")==null) {
               //로그인 하지 않은 경우 얼럿 메시지 노출
               throw new MemberAuthException("로그인이 필요한 서비스 입니다.");
            }else {
               result = joinPoint.proceed(); 
            }
         }
      }
      return result;
   }
}