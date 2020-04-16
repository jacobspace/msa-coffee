package com.example.msa.service;

import com.example.msa.repository.Member;
import com.example.msa.repository.MemberRepository;
import com.example.msa.rest.dto.MemberResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        memberService = new MemberService(memberRepository);
    }

    @Test
    void findById_withExistsId_returnMember() {
        assertNotNull(memberService);

        Member expectedMember = Member.builder()
                .id(1L)
                .name("김석환")
                .phoneNumber("01012341234").build();

//        when(memberRepository.findById(1L)).thenReturn(Optional.of(expectedMember));
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(expectedMember));


        //when
        MemberResponseDto actualMember = memberService.findById(1L);

        //then
        assertThat(actualMember.getId()).isEqualTo(expectedMember.getId());

    }

    @Test
    void findById_withNotExistsId_returnIllegalArgumentException() {
        assertNotNull(memberService);

//        when(memberRepository.findById(1L)).thenThrow(new IllegalArgumentException());
//        when(memberRepository.findById(1L)).thenReturn(Optional.empty());
//        doThrow(new IllegalArgumentException()).when(memberRepository).findById(1L);
        given(memberRepository.findById(1L)).willReturn(Optional.empty());

        //then then
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.findById(1L);
        });
    }
}