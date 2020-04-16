package com.example.msa.service;

import com.example.msa.producer.OrderProducer;
import com.example.msa.producer.OrderVO;
import com.example.msa.repository.Order;
import com.example.msa.repository.OrderRepository;
import com.example.msa.rest.dto.OrderResponseDto;
import com.example.msa.rest.dto.OrderSaveRequestDto;
import com.example.msa.restclient.MemberFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberFeignClient memberFeignClient;
    private final OrderProducer orderProducer;

    public Integer save(OrderSaveRequestDto requestDto) {
        if (memberFeignClient.findByParam(requestDto.getMemberName()) == null) {
            return 0;
        }

        Order order = requestDto.toEntity();
        orderRepository.save(order);

        orderProducer.send(new OrderVO(order));

        return order.getOrderNo();
    }

    public List<OrderResponseDto> findAll() {
        return orderRepository.findAll().stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }

    public OrderResponseDto findById(Long id) {
        return new OrderResponseDto(orderRepository.findById(id));
    }

}
