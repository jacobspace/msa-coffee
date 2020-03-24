package com.example.msa.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberRepository {
    Long save(Member member);
    List<Member> findAll();
    Member findById(Long id);
}
