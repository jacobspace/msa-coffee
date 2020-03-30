package com.example.msa.rest.dto;

import com.example.msa.repository.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StatusResponseDto {

    private Long id;
    private String orderHistory;

    public StatusResponseDto(Status entity) {
        this.id = entity.getId();
        this.orderHistory = entity.getOrderHistory();
    }

    @Builder
    public StatusResponseDto(Long id, String orderHistory) {
        this.id = id;
        this.orderHistory = orderHistory;
    }
}
