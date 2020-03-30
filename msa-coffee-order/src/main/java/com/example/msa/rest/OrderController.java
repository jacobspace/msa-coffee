package com.example.msa.rest;

import com.example.msa.rest.dto.OrderSaveRequestDto;
import com.example.msa.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders")
    public Long save(@RequestBody OrderSaveRequestDto requestDto) {
        return orderService.save(requestDto);
    }
}
