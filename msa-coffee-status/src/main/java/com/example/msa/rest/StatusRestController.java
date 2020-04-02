package com.example.msa.rest;

import com.example.msa.rest.dto.StatusResponseDto;
import com.example.msa.service.StatusService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StatusRestController {

    private final StatusService statusService;

    @ApiOperation(value = "find all status")
    @HystrixCommand
    @GetMapping("/api/v1/status")
    public List<StatusResponseDto> findAll() {
        return statusService.findAll();
    }

    @ApiOperation(value = "find status by id")
    @HystrixCommand
    @GetMapping("/api/v1/status/{id}")
    public StatusResponseDto findById(@PathVariable("id") Long id) {
        return statusService.findById(id);
    }
}
