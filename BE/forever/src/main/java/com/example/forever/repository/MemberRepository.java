package com.example.forever.repository;

import com.example.forever.domain.Member;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    List<Member> findAllByIsDeletedTrueAndDeletedAtBefore(LocalDateTime time);

}
