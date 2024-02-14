package org.jaco114.repolookup.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().exchangeStrategies(
                ExchangeStrategies.builder().
                        codecs(ClientCodecConfigurer::defaultCodecs)
                        .build()
        ).build();
    }

}
