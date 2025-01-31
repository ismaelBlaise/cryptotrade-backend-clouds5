package com.chucky.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.chucky.project.dto.TokenDTO;

@Service
public class TokenService {

    @Autowired
    private WebClient webClient;

    public Boolean verifierTokenAPI(String token){
        TokenDTO tokenDTO=new TokenDTO();
        tokenDTO.setToken(token);

        try {
            return webClient.post()
                .uri("/tokens/valider-token")
                .bodyValue(tokenDTO)
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(body -> new RuntimeException(body))
                )
                .bodyToMono(Boolean.class)
                .block(); 
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
