package com.example.forever.common.auth;

import com.example.forever.common.annotation.AuthMember;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.exception.auth.UnauthorizedAccessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticatedMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    // @AuthUser 어노테이션이 붙은 파라미터를 찾아서 동작
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMember.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        // 헤더 값 체크
        if (httpServletRequest != null) {
            String token = httpServletRequest.getHeader("Authorization");
            if (token != null && !token.trim().isEmpty()) {
                // 토큰 있을 경우 검증
                if (jwtTokenProvider.validateToken(token)) {
                    // 검증 후 MemberInfo 리턴
                    Long memberId = jwtTokenProvider.getMemberIdFromToken(token);
                    return new MemberInfo(memberId);
                }
            }

            // 토큰은 없지만 필수가 아닌 경우
            AuthMember annotation = parameter.getParameterAnnotation(AuthMember.class);
            if (annotation != null && !annotation.required()) {
                // 기본 객체 리턴
                return new MemberInfo();
            }
        }

        // 토큰 값이 없으면 에러
        throw new UnauthorizedAccessException();
    }
}
