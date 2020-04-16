package com.example.msa.producer;

import com.example.msa.repository.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @EqualsAndHashCode @ToString
@NoArgsConstructor
public class OrderVO {

    private Integer orderNo;
    private String coffeeName;
    private Integer coffeeCount;
    private String memberName;

    public OrderVO(Order entity) {
        this.orderNo = entity.getOrderNo();
        this.coffeeName = entity.getCoffeeName();
        this.coffeeCount = entity.getCoffeeCount();
        this.memberName = entity.getMemberName();
    }
}
