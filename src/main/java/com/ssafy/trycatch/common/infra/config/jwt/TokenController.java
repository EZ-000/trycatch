package com.ssafy.trycatch.common.infra.config.jwt;

import static com.ssafy.trycatch.common.infra.config.jwt.Token.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TokenController {
    private final TokenService tokenService;

    @GetMapping("/token/expired")
    public ResponseEntity<String> auth(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(CheckAccessTokenAttributeKey);
        if (token != null && tokenService.verifyToken(token)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/token/refresh")
    public ResponseEntity<String> refreshAuth(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(CheckRefreshTokenAttributeKey);

        if (token != null && tokenService.verifyToken(token)) {
            final String uid = tokenService.getUid(token);
            final String accessToken = tokenService.getAccessToken(token);
            final Token newToken = tokenService.generateToken(uid, accessToken);

            // 새로 생성된 Access Token을 헤더에 넣어 돌려준다.
            response.addHeader(HeaderDefaultTokenAttributeKey, newToken.getToken());
            //response.addHeader(HeaderRefreshTokenAttributeKey, newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
