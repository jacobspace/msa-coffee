package com.example.msa.service;

import com.example.msa.repository.StatusRepository;
import com.example.msa.rest.dto.StatusResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatusService {

    private final StatusRepository statusRepository;

    public List<StatusResponseDto> findAll() {
        return statusRepository.findAll().stream().map(StatusResponseDto::new).collect(Collectors.toList());
    }

    public StatusResponseDto findById(Long id) {
        return new StatusResponseDto(statusRepository.findById(id));
    }
}
