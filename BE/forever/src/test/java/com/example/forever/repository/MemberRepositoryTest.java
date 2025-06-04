package com.example.forever.repository;

import com.example.forever.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("MemberRepository 테스트")
class MemberRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원을 저장하고 조회할 수 있다")
    void saveMember_Success() {
        // given
        Member member = Member.builder()
                .email("test@example.com")
                .nickname("테스트유저")
                .major("컴퓨터공학과")
                .school("테스트대학교")
                .inflow("지인추천,검색")
                .build();

        // when
        Member savedMember = memberRepository.save(member);
        entityManager.flush();

        // then
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getEmail()).isEqualTo("test@example.com");
        assertThat(savedMember.getNickname()).isEqualTo("테스트유저");
        assertThat(savedMember.getMajor()).isEqualTo("컴퓨터공학과");
        assertThat(savedMember.getSchool()).isEqualTo("테스트대학교");
        assertThat(savedMember.getInflow()).isEqualTo("지인추천,검색");
    }

    @Test
    @DisplayName("이메일로 회원을 찾을 수 있다")
    void findByEmail_Success() {
        // given
        Member member = Member.builder()
                .email("find@example.com")
                .nickname("찾기테스트")
                .major("소프트웨어공학과")
                .school("찾기대학교")
                .build();
        
        memberRepository.save(member);
        entityManager.flush();

        // when
        Optional<Member> foundMember = memberRepository.findByEmail("find@example.com");

        // then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getNickname()).isEqualTo("찾기테스트");
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 조회하면 빈 Optional을 반환한다")
    void findByEmail_NotFound() {
        // when
        Optional<Member> foundMember = memberRepository.findByEmail("notfound@example.com");

        // then
        assertThat(foundMember).isEmpty();
    }

    @Test
    @DisplayName("inflow 필드가 null이어도 저장할 수 있다")
    void saveMember_WithNullInflow_Success() {
        // given
        Member member = Member.builder()
                .email("nullinflow@example.com")
                .nickname("null테스트")
                .major("테스트학과")
                .school("테스트학교")
                .inflow(null)
                .build();

        // when
        Member savedMember = memberRepository.save(member);
        entityManager.flush();

        // then
        assertThat(savedMember.getInflow()).isNull();
    }

    @Test
    @DisplayName("기본값들이 올바르게 설정된다")
    void saveMember_DefaultValues_Correct() {
        // given
        Member member = Member.builder()
                .email("defaults@example.com")
                .nickname("기본값테스트")
                .major("기본학과")
                .school("기본학교")
                .build();

        // when
        Member savedMember = memberRepository.save(member);
        entityManager.flush();

        // then
        assertThat(savedMember.getAvailableTokens()).isEqualTo(3); // 기본값 3으로 변경
        assertThat(savedMember.isDeleted()).isFalse();
        assertThat(savedMember.getDeletedAt()).isNull();
        assertThat(savedMember.getIsAgreedPolicy()).isTrue();
        assertThat(savedMember.getIsAgreedTerms()).isTrue();
        assertThat(savedMember.getCreatedAt()).isNotNull();
        assertThat(savedMember.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("여러 회원을 저장하고 모두 조회할 수 있다")
    void saveMultipleMembers_Success() {
        // given
        Member member1 = Member.builder()
                .email("member1@example.com")
                .nickname("회원1")
                .major("학과1")
                .school("학교1")
                .inflow("검색")
                .build();

        Member member2 = Member.builder()
                .email("member2@example.com")
                .nickname("회원2")
                .major("학과2")
                .school("학교2")
                .inflow("지인추천,광고")
                .build();

        // when
        memberRepository.save(member1);
        memberRepository.save(member2);
        entityManager.flush();

        List<Member> allMembers = memberRepository.findAll();

        // then
        assertThat(allMembers).hasSize(2);
        assertThat(allMembers)
                .extracting(Member::getEmail)
                .contains("member1@example.com", "member2@example.com");
    }
}
