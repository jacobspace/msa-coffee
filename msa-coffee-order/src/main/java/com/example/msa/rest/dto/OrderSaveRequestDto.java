package com.example.msa.rest.dto;

import com.example.msa.repository.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderSaveRequestDto {

    private Integer orderNo;
    private String coffeeName;
    private Integer coffeeCount;
    private String memberName;

    @Builder
    public OrderSaveRequestDto(Integer orderNo, String coffeeName, Integer coffeeCount, String memberName) {
        this.orderNo = orderNo;
        this.coffeeName = coffeeName;
        this.coffeeCount = coffeeCount;
        this.memberName = memberName;
    }

    public Order toEntity() {
        return Order.builder()
                .orderNo(orderNo)
                .coffeeName(coffeeName)
                .coffeeCount(coffeeCount)
                .memberName(memberName)
                .build();
    }
}
