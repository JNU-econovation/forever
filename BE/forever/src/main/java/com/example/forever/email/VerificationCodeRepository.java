package com.example.forever.email;

import com.example.forever.domain.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByEmailAndCode(String email, String code);

    Optional<VerificationCode> findByCode(String code);

    void deleteByExpiresTimeBefore(LocalDateTime time);
}
