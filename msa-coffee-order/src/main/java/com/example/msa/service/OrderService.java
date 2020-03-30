package com.example.msa.service;

import com.example.msa.repository.OrderRepository;
import com.example.msa.rest.dto.OrderResponseDto;
import com.example.msa.rest.dto.OrderSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public Long save(OrderSaveRequestDto requestDto) {
        return orderRepository.save(requestDto.toEntity());
    }

    public List<OrderResponseDto> findAll() {
        return orderRepository.findAll().stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }

    public OrderResponseDto findById(Long id) {
        return new OrderResponseDto(orderRepository.findById(id));
    }
}
