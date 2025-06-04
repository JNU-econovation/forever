package com.example.forever.infrastructure.persistence;

import com.example.forever.domain.Member;
import com.example.forever.domain.member.Email;
import com.example.forever.domain.member.MemberDomainRepository;
import com.example.forever.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepositoryAdapter implements MemberDomainRepository {
    
    private final MemberRepository memberRepository;
    
    @Override
    public Optional<Member> findByEmail(Email email) {
        return memberRepository.findByEmail(email.getValue());
    }
    
    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }
    
    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }
    
    @Override
    public boolean existsByEmail(Email email) {
        return memberRepository.findByEmail(email.getValue()).isPresent();
    }
    
    @Override
    public List<Member> findAllActiveMembers() {
        return memberRepository.findAll().stream()
                .filter(member -> !member.isDeleted())
                .collect(java.util.stream.Collectors.toList());
    }
}
