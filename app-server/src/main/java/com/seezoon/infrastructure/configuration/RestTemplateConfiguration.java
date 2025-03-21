package com.seezoon.infrastructure.configuration;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.Timeout;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfiguration {

    private static final Timeout CONNECT_TIMEOUT = Timeout.ofSeconds(3);
    private static final Timeout SOCKET_TIMEOUT = Timeout.ofSeconds(10);

    @Bean
    public RestClientCustomizer customizer() {
        return restClientBuilder -> {
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
            connectionManager.setMaxTotal(200); // 最大连接数
            connectionManager.setDefaultMaxPerRoute(200); // 每个路由的默认最大连接数
            connectionManager.setConnectionConfigResolver(httpRoute -> ConnectionConfig.custom()
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .setTimeToLive(Timeout.ofMinutes(10))
                    .setValidateAfterInactivity(Timeout.ofSeconds(60 * 3)) // 60 * 3s未使用的连接，需要先检测
                    .build());
            connectionManager.setDefaultSocketConfig(
                    SocketConfig.custom().setSoKeepAlive(true).setSoTimeout(SOCKET_TIMEOUT).build());
            HttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setConnectionRequestTimeout(Timeout.ofSeconds(6)) // 设置从连接池获取连接的超时时间（毫秒）
                            .build())
                    .build();

            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            restClientBuilder.requestFactory(requestFactory);
        };
    }

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        builder.messageConverters((httpMessageConverters) -> {
            httpMessageConverters.add(new TextPlainMappingJackson2HttpMessageConverter());
        });
        return builder.build();
    }

    /**
     * 微信接口返回contentType 不规范
     */
    public static class TextPlainMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

        public TextPlainMappingJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_PLAIN);
            setSupportedMediaTypes(mediaTypes);
        }
    }
}


