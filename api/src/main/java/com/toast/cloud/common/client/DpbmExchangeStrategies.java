package com.toast.cloud.common.client;

import com.toast.cloud.common.utils.ObjectMapperUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DpbmExchangeStrategies {

    public static ExchangeStrategies exchangeStrategies() {
        return ExchangeStrategies
            .builder()
            .codecs(clientDefaultCodecsConfigurer -> {
                clientDefaultCodecsConfigurer.defaultCodecs()
                    .jackson2JsonEncoder(new Jackson2JsonEncoder(ObjectMapperUtils.get(), MediaType.APPLICATION_JSON));
                clientDefaultCodecsConfigurer.defaultCodecs()
                    .jackson2JsonDecoder(new Jackson2JsonDecoder(ObjectMapperUtils.get(), MediaType.APPLICATION_JSON));
            }).build();
    }

}
