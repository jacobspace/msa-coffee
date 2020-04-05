package com.example.msa.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface MemberRepository {
    Long save(Member member);
    List<Member> findAll();
    Optional<Member> findById(Long id);
    Member findByParam(String name);
}
