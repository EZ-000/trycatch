package com.ssafy.trycatch.common.infra.config.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.trycatch.user.domain.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trycatch.common.infra.config.jwt.Token;
import com.ssafy.trycatch.common.infra.config.jwt.TokenService;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.ssafy.trycatch.common.infra.config.jwt.Token.HeaderDefaultTokenAttributeKey;
import static com.ssafy.trycatch.common.infra.config.jwt.Token.HeaderRefreshTokenAttributeKey;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        final OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        final String currentUserNodeId = oAuth2User.getAttribute("nodeId");

        // 만약 데이터베이스에 유저가 존재한다면, 해당 객체를 가져오고
        // 만약 존재하지 않는다면, 저장 후 가져오도록 작성됨
        final User tempUser = userRepository.findByGithubNodeId(currentUserNodeId)
                .orElse(userRequestMapper.newEntity(oAuth2User));
        final User savedUser = userRepository.save(tempUser);

        final Token token = tokenService.generateToken(
                savedUser.getId().toString(),
                oAuth2User.getAttribute("AC_TOKEN")
        );

        log.info("{}", token);

        writeTokenResponse(response, token);
    }

    private void writeTokenResponse(HttpServletResponse response, Token token) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader(HeaderDefaultTokenAttributeKey, token.getToken());
        response.addHeader(HeaderRefreshTokenAttributeKey, token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        response.sendRedirect("https://i8e108.p.ssafy.io/");
        // var writer = response.getWriter();
        // writer.println(objectMapper.writeValueAsString(token));
        // writer.flush();
    }
}