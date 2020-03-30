package com.example.msa.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderRepository {
    Long save(Order order);
    List<Order> findAll();
    Order findById(Long id);
}
