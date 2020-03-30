package com.example.msa.restclient;

import com.example.msa.restclient.vo.MemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "msa-coffee-member", url = "${msa.coffee.member.url}")
public interface MemberFeignClient {
    @GetMapping("/api/v1.0/members/{name}")
    MemberVo findMemberBydNameAndPhoneNumber(@PathVariable("name") String name);
}
