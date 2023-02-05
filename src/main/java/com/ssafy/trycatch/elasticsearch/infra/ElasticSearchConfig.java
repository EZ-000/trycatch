package com.ssafy.trycatch.elasticsearch.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ssafy.trycatch.elasticsearch.domain.repository")
public class ElasticSearchConfig extends ElasticsearchConfiguration {
    @Value("${elasticsearch.host}")
    private String host;

    @NonNull
    @Override
    public ClientConfiguration clientConfiguration() {
        // ES version up to 7.14
        return ClientConfiguration.builder()
                .connectedTo(host)
                .build();
    }
}
