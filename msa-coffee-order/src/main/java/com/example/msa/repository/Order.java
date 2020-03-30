package com.example.msa.repository;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Order {

    private Long id;
    private Integer orderNo;
    private String coffeeName;
    private Integer coffeeCount;
    private String memberName;

    @Builder
    public Order(Long id, Integer orderNo, String coffeeName, Integer coffeeCount, String memberName) {
        this.id = id;
        this.orderNo = orderNo;
        this.coffeeName = coffeeName;
        this.coffeeCount = coffeeCount;
        this.memberName = memberName;
    }
}
