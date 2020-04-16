package com.example.msa.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest(properties = {"spring.datasource.data="})
class OrderRepositoryTest {

    @Autowired private OrderRepository orderRepository;

    @Test
    void save() {
        //given
        Order order = Order.builder()
                .coffeeName("아메리카노")
                .coffeeCount(1)
                .memberName("관리자").build();

        //when then
        orderRepository.save(order);
        assertThat(order.getOrderNo()).isEqualTo(1);

        //when then
        orderRepository.save(order);
        assertThat(order.getOrderNo()).isEqualTo(2);
    }
}