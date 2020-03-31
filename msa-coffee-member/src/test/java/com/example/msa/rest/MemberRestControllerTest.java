package com.example.msa.rest;

import com.example.msa.rest.dto.MemberResponseDto;
import com.example.msa.rest.dto.MemberSaveRequestDto;
import com.example.msa.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {MemberRestController.class})
class MemberRestControllerTest {

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 신규회원등록() throws Exception {
        //given
        MemberSaveRequestDto memberSaveRequestDto = MemberSaveRequestDto.builder()
                .name("손흥민")
                .phoneNumber("01012341234").build();

        String requestBody = new ObjectMapper().writeValueAsString(memberSaveRequestDto);

        given(memberService.save(any(MemberSaveRequestDto.class))).willReturn(1L);

        //when
        mockMvc.perform(post("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        //then
        verify(memberService).save(any(MemberSaveRequestDto.class));
    }

    @Test
    void 회원목록조회() throws Exception {
        //given
        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .id(1L)
                .name("손흥민")
                .phoneNumber("01012341234").build();

        List<MemberResponseDto> memberResponseDtoList = Collections.singletonList(memberResponseDto);

        given(memberService.findAll()).willReturn(memberResponseDtoList);

        //when
        mockMvc.perform(get("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo("1")))
                .andExpect(jsonPath("$[0].name", equalTo("손흥민")))
                .andExpect(jsonPath("$[0].phoneNumber", equalTo("01012341234")));
    }

    @Test
    void 회원명으로_회원조회() throws Exception {
        //given
        MemberResponseDto expectedResponse = MemberResponseDto.builder()
                .id(1L)
                .name("손흥민")
                .phoneNumber("01012341234").build();

        given(memberService.findByParam(expectedResponse.getName())).willReturn(expectedResponse);

        //when
        mockMvc.perform(get("/api/v1/members/find")
                .queryParam("name", expectedResponse.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(expectedResponse.getName())));

    }
}