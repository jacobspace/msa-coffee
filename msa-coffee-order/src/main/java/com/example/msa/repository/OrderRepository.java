package com.example.msa.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderRepository {
    void save(Order order);
    List<Order> findAll();
    Order findById(Long id);
}
