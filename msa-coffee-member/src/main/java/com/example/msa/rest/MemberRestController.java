package com.example.msa.rest;

import com.example.msa.rest.dto.MemberResponseDto;
import com.example.msa.rest.dto.MemberSaveRequestDto;
import com.example.msa.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberRestController {

    private final MemberService memberService;

    @ApiOperation(value = "save member")
    @PostMapping("/api/v1/members")
    public Long save(@RequestBody MemberSaveRequestDto requestDto) {
        return memberService.save(requestDto);
    }

    @ApiOperation(value = "find all member")
    @GetMapping("/api/v1/members")
    public List<MemberResponseDto> findAll() {
        return memberService.findAll();
    }

    @ApiOperation(value = "find member by id")
    @GetMapping("/api/v1/members/{id}")
    public MemberResponseDto findById(@PathVariable Long id) {
        return memberService.findById(id);
    }
}
