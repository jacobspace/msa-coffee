package com.example.msa.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberRepository {
    Long save(Member member);
    List<Member> findAll();
    Member findById(Long id);
    Member findByParam(String name);
}
