package com.ssafy.trycatch.elasticsearch.infra;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ssafy.trycatch.elasticsearch.domain.repository")
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
    @Value("${elasticsearch.host}")
    private String host;

    @NonNull
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration =
                ClientConfiguration.builder()
                        .connectedTo(host)
                        .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
