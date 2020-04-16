package com.example.msa.consumer;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderVO {

    private Integer orderNo;
    private String coffeeName;
    private Integer coffeeCount;
    private String memberName;

    @Builder
    public OrderVO(Integer orderNo, String coffeeName, Integer coffeeCount, String memberName) {
        this.orderNo = orderNo;
        this.coffeeName = coffeeName;
        this.coffeeCount = coffeeCount;
        this.memberName = memberName;
    }
}
