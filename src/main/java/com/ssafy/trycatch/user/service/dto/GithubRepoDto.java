package com.ssafy.trycatch.user.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class GithubRepoDto {
    String nodeId;
    String languagesUrl;
    String description;
}
