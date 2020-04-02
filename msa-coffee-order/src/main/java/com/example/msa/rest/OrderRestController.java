package com.example.msa.rest;

import com.example.msa.rest.dto.OrderResponseDto;
import com.example.msa.rest.dto.OrderSaveRequestDto;
import com.example.msa.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final OrderService orderService;

    @ApiOperation(value = "save order")
    @HystrixCommand
    @PostMapping("/api/v1/orders")
    public Integer save(@RequestBody OrderSaveRequestDto requestDto) {
        return orderService.save(requestDto);
    }

    @ApiOperation(value = "find all order")
    @HystrixCommand
    @GetMapping("/api/v1/orders")
    public List<OrderResponseDto> findAll() {
        return orderService.findAll();
    }

    @ApiOperation(value = "find order by id")
    @HystrixCommand
    @GetMapping("/api/v1/orders/{id}")
    public OrderResponseDto findById(@PathVariable("id") Long id) {
        return orderService.findById(id);
    }
}
