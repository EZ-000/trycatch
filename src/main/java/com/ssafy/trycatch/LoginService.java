package com.ssafy.trycatch;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {
	private static final Logger log = LoggerFactory.getLogger(LoginService.class);
	private String url = "https://github.com/login/oauth/access_token";
	private String clientId = "7b4fb4e472ba8a793c1b";
	private String clientSecret = "4f5672997fa38b23f9d8641f85cd1ea3cca44abf";
	//client secret 값은 노출되면 안되므로, 서버 환경변수에 해당 값을 등록하여 코드에서 다음과 같이 불러와 사용할 수
	//private String clientSecret = System.getenv("GITHUB_CLIENT_SECRET")

	public GithubToken getAccessToken(String code) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Map<String, String> header = new HashMap<>();
		header.put("Accept", "application/json"); //json 형식으로 응답 받음
		headers.setAll(header);

		MultiValueMap<String, String> requestPayloads = new LinkedMultiValueMap<>();
		Map<String, String> requestPayload = new HashMap<>();
		requestPayload.put("client_id", clientId);
		requestPayload.put("client_secret", clientSecret);
		requestPayload.put("code", code);
		requestPayloads.setAll(requestPayload);

		HttpEntity<?> request = new HttpEntity<>(requestPayloads, headers);
		ResponseEntity<?> response = new RestTemplate().postForEntity(url, request,
			GithubToken.class); //미리 정의해둔 GithubToken 클래스 형태로 Response Body를 파싱해서 담아서 리턴
		return (GithubToken)response.getBody();
	}

}