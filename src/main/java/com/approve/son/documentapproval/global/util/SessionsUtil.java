package com.approve.son.documentapproval.global.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionsUtil {
    public static final String SESSION_ID = "SON_SESSION_ID";

    // 동기화 안정을 위해 ConcurrentHashMap 사용 -> 고도화 시 DB 또는 Redis 저장 필요
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /**
     * 세션 생성
     */
    public void createSession(Object value, HttpServletResponse response){
        // 세션 id를 생성하고, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        // 쿠키 생성
        response.addCookie(new Cookie(SESSION_ID, sessionId));
    }

    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request){

        Cookie sessionCookie = findCookie(request, SESSION_ID);
        if (sessionCookie == null){
            return null;
        }

        return sessionStore.get(sessionCookie.getValue());
    }

    /**
     * 세션 만료
     */
    public void expire(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSION_ID);
        if (sessionCookie != null){
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    private Cookie findCookie(HttpServletRequest request, String cookieName) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }
}
