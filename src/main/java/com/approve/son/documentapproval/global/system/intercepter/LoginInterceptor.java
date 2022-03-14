package com.approve.son.documentapproval.global.system.intercepter;


import com.approve.son.documentapproval.domain.member.dto.MemberDto;
import com.approve.son.documentapproval.domain.member.repository.MemberRepository;
import com.approve.son.documentapproval.global.system.context.SonContext;
import com.approve.son.documentapproval.global.system.context.SonContextHolder;
import com.approve.son.documentapproval.global.util.SessionsUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;
    private final SessionsUtil sessionsUtil;

    public LoginInterceptor(MemberRepository memberRepository, SessionsUtil sessionsUtil) {
        this.memberRepository = memberRepository;
        this.sessionsUtil = sessionsUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        MemberDto sessionItem = (MemberDto) sessionsUtil.getSession(request);

        if(sessionItem == null) {
            response.getOutputStream().println("LOGIN REQUIRED!");
            return false;
        }

        SonContext context = SonContextHolder.getContext();

        context.setCurrentMemberId(sessionItem.getId());
        context.setMember(sessionItem);

        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        SonContextHolder.clearContext();
    }
}
