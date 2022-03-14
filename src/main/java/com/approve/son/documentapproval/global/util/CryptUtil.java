package com.approve.son.documentapproval.global.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CryptUtil {

    // 회원가입 시 암호화 사용
    public String encode(String word) {
        return new BCryptPasswordEncoder().encode(word);
    }

    public Boolean matches(CharSequence rawWord, String encodedWord) {
        return new BCryptPasswordEncoder().matches(rawWord,encodedWord);
    }
}
