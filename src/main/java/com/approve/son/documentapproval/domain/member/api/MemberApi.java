package com.approve.son.documentapproval.domain.member.api;

import com.approve.son.documentapproval.domain.member.application.MemberService;
import com.approve.son.documentapproval.domain.member.dto.LoginDto;
import com.approve.son.documentapproval.domain.member.dto.MemberDto;
import com.approve.son.documentapproval.global.response.CommonResponse;
import com.approve.son.documentapproval.global.util.SessionsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class MemberApi {

    private final MemberService memberService;
    private final SessionsUtil sessionManager;

    /**
     * @name : getMemberLoginInfo
     * @param : LoginDto, HttpServletResponse
     * @return : ResponseEntity
     * @description : 로그인 체크
     */
    @PostMapping("/login/check")
    public ResponseEntity<?> getMemberLoginInfo(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        MemberDto member = memberService.getMemberLogin(loginDto.getLoginId(), loginDto.getLoginPassword());

        // 로그인 성공 처리
        sessionManager.createSession(member, response);

        return ResponseEntity.ok().body(new CommonResponse(member));
    }

    /**
     * @name : logout
     * @param : HttpServletRequest
     * @return : ResponseEntity
     * @description : 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        sessionManager.expire(request);

        return ResponseEntity.ok().body(new CommonResponse("logout successes"));
    }
    /**
     * @name : getMemberList
     * @param :
     * @return : ResponseEntity
     * @description : 문서 목록 조회
     */
    @GetMapping("")
    public ResponseEntity<?> getMemberList() {
        List<MemberDto> member = memberService.getMemberList();

        return ResponseEntity.ok().body(new CommonResponse(member));
    }




}
