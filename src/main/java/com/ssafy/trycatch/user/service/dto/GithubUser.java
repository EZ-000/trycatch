package com.ssafy.trycatch.user.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class GithubUser {
    String login;
    Integer id;
    String nodeId;
    String reposUrl;
    String company;
    String name;
    Integer publicRepos;
}
