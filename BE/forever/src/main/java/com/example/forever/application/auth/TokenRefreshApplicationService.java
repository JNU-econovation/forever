package com.example.forever.application.auth;

import com.example.forever.domain.Member;
import com.example.forever.domain.auth.token.*;
import com.example.forever.domain.member.MemberId;
import com.example.forever.exception.auth.InvalidRefreshTokenException;
import com.example.forever.infrastructure.auth.AuthenticationResponseBuilder;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TokenRefreshApplicationService {
    
    private final TokenValidationService tokenValidationService;
    private final TokenGenerationService tokenGenerationService;
    private final MemberTokenService memberTokenService;
    private final AuthenticationResponseBuilder authResponseBuilder;
    
    /**
     * 리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급합니다.
     */
    public void refreshToken(String refreshTokenValue, HttpServletResponse response) {
        try {
            //커맨드 생성 및 검증
            RefreshTokenCommand command = RefreshTokenCommand.of(refreshTokenValue);
            
            if (!command.hasRefreshToken()) {
                throw new InvalidRefreshTokenException();
            }
            
            //리프레시 토큰 값 객체 생성
            RefreshToken refreshToken = new RefreshToken(refreshTokenValue);
            
            //토큰 검증 및 회원 ID 추출
            tokenValidationService.validateRefreshTokenPresence(refreshToken);
            
            if (!tokenValidationService.isValidRefreshToken(refreshToken)) {
                throw new InvalidRefreshTokenException();
            }
            
            MemberId memberId = tokenValidationService.extractMemberIdFromToken(refreshToken);

            Member member = memberTokenService.findAndValidateMember(memberId);
            
            TokenPair newTokenPair = tokenGenerationService.generateNewTokenPair(memberId);
            
            //회원의 리프레시 토큰 업데이트
            memberTokenService.updateMemberRefreshToken(member, newTokenPair.getRefreshToken());
            authResponseBuilder.setAuthenticationResponse(
                    response, 
                    newTokenPair.getAccessTokenValue(), 
                    newTokenPair.getRefreshTokenValue()
            );
            
            log.info("토큰 재발급 완료: memberId={}", memberId.getValue());
            
        } catch (InvalidRefreshTokenException e) {
            log.warn("유효하지 않은 리프레시 토큰으로 인한 재발급 실패");
            throw e;
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.warn("토큰 재발급 실패: {}", e.getMessage());
            throw new InvalidRefreshTokenException();
        } catch (Exception e) {
            log.error("토큰 재발급 중 예상치 못한 오류 발생", e);
            throw new RuntimeException("토큰 재발급 처리 중 오류가 발생했습니다.", e);
        }
    }
}
