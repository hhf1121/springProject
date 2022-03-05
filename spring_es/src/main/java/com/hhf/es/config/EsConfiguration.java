package com.hhf.es.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Configuration
public class EsConfiguration {

    private static final int ADDRESS_LENGTH = 2;

    private static final String HTTP_SCHEME = "http";

    @Value("${es.hosts:127.0.0.1:9200}")
//    @Value("${es.hosts:127.0.0.1:9200}")
    private String[] hosts;

    @Value("${es.username:}")
    private String username;

    @Value("${es.password:}")
    private String password;

    @Value("${es.connectTimeout:6000}")
    private Integer connectTimeout;

    @Value("${es.socketTimeout:6000}")
    private Integer socketTimeout;

    @Value("${es.connectionRequestTimeout:6000}")
    private Integer connectionRequestTimeout;

    public CredentialsProvider basicCredentialsProvider() {
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(username, password));
            return credentialsProvider;
        }
        return new BasicCredentialsProvider();
    }

    @Bean
    public RestClientBuilder restClientBuilder() {
        HttpHost[] httpHosts = Arrays.stream(hosts)
                .map(this::getHttpHost)
                .filter(Objects::nonNull)
                .toArray(HttpHost[]::new);
        RestClientBuilder builder = RestClient.builder(httpHosts);
        CredentialsProvider credentialsProvider = basicCredentialsProvider();
        if (credentialsProvider != null) {
            builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                    return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                }
            });
            builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                @Override
                public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                    //设置连接超时时间
                    requestConfigBuilder.setConnectTimeout(connectTimeout);
                    //设置socket超时时间s
                    requestConfigBuilder.setSocketTimeout(socketTimeout);
                    //设置连接请求超时时间
                    requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeout);
                    return requestConfigBuilder;
                }
            });
            builder.setFailureListener(new RestClient.FailureListener() {
                /*3.设置每次节点发生故障时收到通知的侦听器。内部嗅探到故障时被启用。*/
                @Override
                public void onFailure(Node node) {
                    log.error("某个节点发生故障，故障地址是" + node.getHost());
                }
            });
        }
        return builder;
    }

    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder) {
        return new RestHighLevelClient(restClientBuilder);
    }


    private HttpHost getHttpHost(String host) {
        assert StringUtils.isNotEmpty(host);
        String[] address = host.split(":");
        if (address.length == ADDRESS_LENGTH) {
            String ip = address[0];
            int port = Integer.parseInt(address[1]);
            return new HttpHost(ip, port, HTTP_SCHEME);
        } else {
            return null;
        }
    }

}