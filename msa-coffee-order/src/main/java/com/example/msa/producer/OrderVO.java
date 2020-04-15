package com.example.msa.producer;

import com.example.msa.repository.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @EqualsAndHashCode
@NoArgsConstructor
public class OrderVO {

    private Integer orderNo;
    private String coffeeName;
    private Integer coffeeCount;
    private String memberName;

    public OrderVO(Order order) {
        this.orderNo = order.getOrderNo();
        this.coffeeName = order.getCoffeeName();
        this.coffeeCount = order.getCoffeeCount();
        this.memberName = order.getMemberName();
    }
}
