package com.example.msa.restclient;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MemberRestClient {

    private final RestTemplate restTemplate;

    public MemberRestClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public MemberVo findByParam(String name) {
        String url = "http://localhost:8075/api/v1/members/find";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("name", name);
        return restTemplate.getForObject(builder.toUriString(), MemberVo.class);
    }
}
