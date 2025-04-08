package com.example.forever.common.scheduler;

import com.example.forever.domain.Member;
import com.example.forever.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberScheduler {

    private final MemberRepository memberRepository;

    @Scheduled(cron = "0 0 3 * * *") // 매일 새벽 3시
    public void deleteUsersAfterGracePeriod() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(30);
        List<Member> toBeDeleted = memberRepository.findAllByIsDeletedTrueAndDeletedAtBefore(threshold);

        memberRepository.deleteAll(toBeDeleted);
    }
}