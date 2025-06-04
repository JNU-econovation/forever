package com.example.forever.domain.member;

import com.example.forever.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberDomainRepository {
    
    Optional<Member> findByEmail(Email email);
    
    Member save(Member member);
    
    Optional<Member> findById(Long id);
    
    boolean existsByEmail(Email email);
    
    List<Member> findAllActiveMembers();
}
