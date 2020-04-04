package com.example.msa.restclient;

import com.example.msa.restclient.vo.MemberVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${msa.coffee.member}", fallback = MemberFeignClientFallback.class)
public interface MemberFeignClient {
    @GetMapping("/api/v1/members/find")
    MemberVo findByParam(@RequestParam String name);
}

@Component
class MemberFeignClientFallback implements MemberFeignClient {
    private final Logger logger = LoggerFactory.getLogger(MemberFeignClientFallback.class);
    @Override
    public MemberVo findByParam(String name) {
        logger.error("Member 서비스 : 응답지연 발생");
        return null;
    }
}
