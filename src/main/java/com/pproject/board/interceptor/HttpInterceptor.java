package com.pproject.board.interceptor;

import com.pproject.board.entity.Idd;
import com.pproject.board.sessionData.SessionConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class HttpInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //로그인 유무 체크
        Idd idd=null;
        HttpSession session = request.getSession(false);
        if(session!=null) {
            Idd user = (Idd) session.getAttribute(SessionConstants.LOGIN_MEMBER);
            idd=user;
            log.info("세션은 있음");
            if(user==null) {
                log.info("(인터셉터)인터셉터에서 로그인되지 않음을 발견함!");
                response.sendRedirect("/login");
                return false;
            }
            else {
                log.info("(인터셉터)로그인 정상적으로 됨!");
                return true;
            }
        }
        //log.info("인터셉터에서 통과시킴!",idd.toString());
        log.info("(인터셉터)세션 자체가 생성되지 않았음");
        response.sendRedirect("/login");
        return false;
    }
}