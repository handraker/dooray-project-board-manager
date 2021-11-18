package com.toast.cloud.common.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.tcp.TcpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DpbmWebClient {

    public static final int CONNECT_TIME_OUT_SECONDS = 60;
    public static final int READ_WRITE_TIME_OUT_SECONDS = 90;

    public static WebClient.Builder builder() {
        return builder(CONNECT_TIME_OUT_SECONDS, READ_WRITE_TIME_OUT_SECONDS);
    }

    public static WebClient.Builder builder(int connectionTimeoutSeconds, int readWriteTimeoutSeconds) {
        ConnectionProvider provider = ConnectionProvider.builder("tcp")
            .maxConnections(500)
            .maxIdleTime(Duration.ofSeconds(19))
            .pendingAcquireMaxCount(-1)
            .lifo()
            .build();

        TcpClient tcpClient = TcpClient.create(provider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeoutSeconds * 1000)
            .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(readWriteTimeoutSeconds, TimeUnit.SECONDS))
                .addHandlerLast(new WriteTimeoutHandler(readWriteTimeoutSeconds, TimeUnit.SECONDS)));
        ClientHttpConnector connector = new ReactorClientHttpConnector(HttpClient.from(tcpClient));

        return WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
            .clientConnector(connector);
    }

}
