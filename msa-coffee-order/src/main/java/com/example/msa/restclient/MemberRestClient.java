package com.example.msa.restclient;

import com.example.msa.restclient.vo.MemberVo;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MemberRestClient {

    private final RestTemplate restTemplate;

    public MemberRestClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public MemberVo findMemberByName(String name) {
        return restTemplate.getForObject("http://localhost:8075/api/v1.0/members/" + name, MemberVo.class);
    }
}
