package com.example.msa.service;

import com.example.msa.repository.Member;
import com.example.msa.repository.MemberRepository;
import com.example.msa.rest.dto.MemberResponseDto;
import com.example.msa.rest.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberSaveRequestDto dto) {
        return memberRepository.save(dto.toEntity());
    }

    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll().stream().map(MemberResponseDto::new).collect(Collectors.toList());
    }

    public MemberResponseDto findById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        member.orElseThrow(() -> new IllegalArgumentException("존재하지않는 회원번호 : " + id));
        return new MemberResponseDto(member.get());
    }

    public MemberResponseDto findByParam(String name) {
        return new MemberResponseDto(memberRepository.findByParam(name));
    }
}
