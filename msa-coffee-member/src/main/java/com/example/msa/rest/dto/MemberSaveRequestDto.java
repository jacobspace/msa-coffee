package com.example.msa.rest.dto;

import com.example.msa.repository.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {

    private String name;
    private String phoneNumber;

    @Builder
    public MemberSaveRequestDto(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .phoneNumber(phoneNumber).build();
    }
}
