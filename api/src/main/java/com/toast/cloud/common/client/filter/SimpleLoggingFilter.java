package com.toast.cloud.common.client.filter;

import com.toast.cloud.common.utils.ObjectMapperUtils;
import com.toast.cloud.common.utils.StringBuilderEx;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.slf4j.MDC;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.*;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpRequestDecorator;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.reactive.function.BodyExtractor;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
public class SimpleLoggingFilter implements ExchangeFilterFunction {

    public static final String LOG_FORMAT = "---> %-8s : %s";

    @Override
    public Mono<ClientResponse> filter(ClientRequest clientRequest, ExchangeFunction exchangeFunction) {
        StringBuilderEx builder = new StringBuilderEx();
        Map<String, String> mdcContextMap = MDC.getCopyOfContextMap();
        if (log.isDebugEnabled()) {
            makeRequestLog(clientRequest, builder);
        }

        long start = System.currentTimeMillis();
        return exchangeFunction.exchange(new ClientRequestDecorator(clientRequest, builder))
            .doOnNext(clientResponse -> {
                if (log.isDebugEnabled()) {
                    long end = System.currentTimeMillis();
                    makeResponseLog(end - start, clientResponse, builder);
                }
            })
            .map(clientResponse -> new ClientResponseDecorator(clientResponse, builder, mdcContextMap));
    }

    @SneakyThrows
    private void makeRequestLog(ClientRequest request, StringBuilderEx builder) {
        builder.newline().append(LOG_FORMAT, "URL", request.url().toURL());
        builder.newline().append(LOG_FORMAT, "METHOD", request.method());

        HttpHeaders headers = request.headers();
        if (!headers.isEmpty()) {
            builder.newline().append("---> %-8s :", "HEADERS");
            makeHeaderLog(builder, headers);
        }
        builder.newline();
    }

    private void makeResponseLog(long elapsedTime, ClientResponse response, StringBuilderEx builder) {
        builder.newline().append("<--- %-13s : %d msec", "ELAPSED TIME", elapsedTime);
        builder.newline().append("<--- %-13s : %d %s", "STATUS", response.statusCode().value(), response.statusCode().name());
        HttpHeaders headers = response.headers().asHttpHeaders();
        if (!headers.isEmpty()) {
            builder.newline().append("<--- %-8s :", "HEADERS");
            makeHeaderLog(builder, headers);
        }
    }

    private void makeHeaderLog(StringBuilderEx builder, HttpHeaders headers) {
        int keyLen = getMaximumKeyLengthOfHeaders(headers);
        headers.entrySet().forEach(entry -> entry.getValue().forEach(value -> builder.newline().append("        %-" + keyLen + "s : %s",
                                                                                                       entry.getKey(),
                                                                                                       value)));
    }

    private int getMaximumKeyLengthOfHeaders(HttpHeaders headers) {
        return headers.keySet()
            .stream()
            .map(String::length)
            .max(Comparator.comparing(Integer::intValue))
            .orElse(0);
    }

    @AllArgsConstructor
    public static class ClientRequestDecorator implements ClientRequest {

        private ClientRequest delegate;
        private StringBuilderEx builder;

        @Override
        public HttpMethod method() {
            return delegate.method();
        }

        @Override
        public URI url() {
            return delegate.url();
        }

        @Override
        public HttpHeaders headers() {
            return delegate.headers();
        }

        @Override
        public MultiValueMap<String, String> cookies() {
            return delegate.cookies();
        }

        @Override
        public BodyInserter<?, ? super ClientHttpRequest> body() {
            return delegate.body();
        }

        @Override
        public Map<String, Object> attributes() {
            return delegate.attributes();
        }

        @Override
        public String logPrefix() {
            return delegate.logPrefix();
        }

        @Override
        public Mono<Void> writeTo(ClientHttpRequest request, ExchangeStrategies strategies) {
            return delegate.writeTo(new ClientHttpRequestDecorator(request) {
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    if (log.isDebugEnabled() && request.getHeaders().getContentType().isCompatibleWith(MediaType.APPLICATION_JSON)) {
                        body = DataBufferUtils.join(Flux.from(body))
                            .map(DataBuffer::asInputStream)
                            .map(this::convertTo)
                            .doOnNext(bodyStr -> builder.append(LOG_FORMAT, "BODY", bodyStr).newline())
                            .map(bodyStr -> bufferFactory().wrap(bodyStr.getBytes()));
                    }
                    return super.writeWith(body);
                }

                @SneakyThrows
                private String convertTo(InputStream inputStream) {
                    return StreamUtils.copyToString(inputStream, Charset.forName("utf8"));
                }
            }, strategies);
        }

    }

    @AllArgsConstructor
    public static class ClientResponseDecorator implements ClientResponse {

        private ClientResponse delegate;
        private StringBuilderEx builder;
        private Map<String, String> mdcContextMap;

        @Override
        public HttpStatus statusCode() {
            return delegate.statusCode();
        }

        @Override
        public int rawStatusCode() {
            return delegate.rawStatusCode();
        }

        @Override
        public Headers headers() {
            return delegate.headers();
        }

        @Override
        public MultiValueMap<String, ResponseCookie> cookies() {
            return delegate.cookies();
        }

        @Override
        public ExchangeStrategies strategies() {
            return delegate.strategies();
        }

        @Override
        public <T> T body(BodyExtractor<T, ? super ClientHttpResponse> bodyExtractor) {
            return delegate.body(bodyExtractor);
        }

        @Override
        public <T> Mono<T> bodyToMono(Class<? extends T> clazz) {
            return delegate.bodyToMono(String.class)
                .switchIfEmpty(Mono.just(""))
                .doOnNext(body -> {
                    if (log.isDebugEnabled()) {
                        builder.newline().append("<--- %-13s : %s", "BODY", body);
                        printLog();
                    }
                })
                .flatMap(body -> {
                    if (delegate.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.empty();
                    } else {
                        if (clazz == Void.class) {
                            return Mono.empty();
                        }

                        return Mono.just(ObjectMapperUtils.deserialize(body, clazz));
                    }
                });
        }

        @Override
        public <T> Mono<T> bodyToMono(ParameterizedTypeReference<T> parameterizedTypeReference) {
            return delegate.bodyToMono(parameterizedTypeReference);
        }

        @Override
        public <T> Flux<T> bodyToFlux(Class<? extends T> aClass) {
            return delegate.bodyToFlux(aClass);
        }

        @Override
        public <T> Flux<T> bodyToFlux(ParameterizedTypeReference<T> parameterizedTypeReference) {
            return delegate.bodyToFlux(parameterizedTypeReference);
        }

        @Override public Mono<Void> releaseBody() {
            return delegate.releaseBody();
        }

        @Override
        public <T> Mono<ResponseEntity<T>> toEntity(Class<T> aClass) {
            return delegate.toEntity(aClass);
        }

        @Override
        public <T> Mono<ResponseEntity<T>> toEntity(ParameterizedTypeReference<T> parameterizedTypeReference) {
            return delegate.toEntity(parameterizedTypeReference);
        }

        @Override
        public <T> Mono<ResponseEntity<List<T>>> toEntityList(Class<T> aClass) {
            return delegate.toEntityList(aClass);
        }

        @Override
        public <T> Mono<ResponseEntity<List<T>>> toEntityList(ParameterizedTypeReference<T> parameterizedTypeReference) {
            return delegate.toEntityList(parameterizedTypeReference);
        }

        @Override public Mono<ResponseEntity<Void>> toBodilessEntity() {
            return delegate.toBodilessEntity();
        }

        @Override public Mono<WebClientResponseException> createException() {
            return delegate.createException();
        }

        @Override public String logPrefix() {
            return delegate.logPrefix();
        }

        private void printLog() {
            MDC.clear();
            if (mdcContextMap != null) {
                MDC.setContextMap(mdcContextMap);
                MDC.put("thread", Thread.currentThread().getName());
            }
            log.debug("{}", builder.toString());
        }

    }

}
