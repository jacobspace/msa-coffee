package com.example.msa.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(MemberRestClient.class)
class MemberRestClientTest {

    @Autowired private MemberRestClient memberRestClient;
    @Autowired private MockRestServiceServer mockRestServiceServer;

    @Test
    void findByParam() throws Exception {
        //given
        String name = "test";

        MemberVo expectedMemberVo = MemberVo.builder()
                .name(name).build();

        String responseMember = new ObjectMapper().writeValueAsString(expectedMemberVo);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8075/api/v1/members/find");
        builder.queryParam("name", name);

        this.mockRestServiceServer.expect(requestTo(builder.toUriString()))
                .andRespond(withSuccess(responseMember, MediaType.APPLICATION_JSON));

        //when
        MemberVo actualMemberVo = memberRestClient.findByParam(name);

        //then
        Assertions.assertThat(actualMemberVo.getName()).isEqualTo(expectedMemberVo.getName());
    }

}