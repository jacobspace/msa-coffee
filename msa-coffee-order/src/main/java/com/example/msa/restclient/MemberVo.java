package com.example.msa.restclient;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberVo {

    private Long id;
    private String name;
    private String phoneNumber;

    @Builder
    public MemberVo(Long id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
