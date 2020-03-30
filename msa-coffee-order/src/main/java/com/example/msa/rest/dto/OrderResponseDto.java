package com.example.msa.rest.dto;

import com.example.msa.repository.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponseDto {

    private Long id;
    private Integer orderNo;
    private String coffeeName;
    private Integer coffeeCount;
    private String memberName;

    public OrderResponseDto(Order entity) {
        this.id = entity.getId();
        this.orderNo = entity.getOrderNo();
        this.coffeeName = entity.getCoffeeName();
        this.coffeeCount = entity.getCoffeeCount();
        this.memberName = entity.getMemberName();
    }

    @Builder
    public OrderResponseDto(Long id, Integer orderNo, String coffeeName, Integer coffeeCount, String memberName) {
        this.id = id;
        this.orderNo = orderNo;
        this.coffeeName = coffeeName;
        this.coffeeCount = coffeeCount;
        this.memberName = memberName;
    }
}
