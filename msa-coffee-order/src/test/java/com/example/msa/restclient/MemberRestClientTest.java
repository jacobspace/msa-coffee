package com.example.msa.restclient;

import com.example.msa.restclient.vo.MemberVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(MemberRestClient.class)
class MemberRestClientTest {

    @Autowired
    private MemberRestClient memberRestClient;

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Test
    void findMemberByName() throws Exception {
        //given
        String name = "test";

        MemberVo expectedMemberVo = MemberVo.builder()
                .name(name).build();

        String responseMember = new ObjectMapper().writeValueAsString(expectedMemberVo);

        this.mockRestServiceServer.expect(requestTo("http://localhost:8075/api/v1.0/members/" + name))
                .andRespond(withSuccess(responseMember, MediaType.APPLICATION_JSON));

        //when
        MemberVo actualMemberVo = memberRestClient.findMemberByName(name);

        //then
        Assertions.assertThat(actualMemberVo.getName()).isEqualTo(expectedMemberVo.getName());
    }

}