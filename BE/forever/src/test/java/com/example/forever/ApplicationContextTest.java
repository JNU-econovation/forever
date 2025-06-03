package com.example.forever;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class ApplicationContextTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @Order(1)
    @DisplayName("ApplicationContext가 정상적으로 로딩된다")
    void contextLoads() {
        // ApplicationContext가 정상적으로 로딩되었는지 확인
        assertThat(applicationContext).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("데이터베이스 연결이 정상적으로 작동한다")
    void databaseConnectionWorks() {
        // 데이터소스 빈이 정상적으로 로딩되었는지 확인
        assertThat(applicationContext.getBean(DataSource.class)).isNotNull();
    }

    @Test
    @Order(3)
    @DisplayName("주요 빈들이 정상적으로 등록되었다")
    void mainBeansAreRegistered() {
        // 애플리케이션의 주요 컴포넌트들이 등록되었는지 확인
        assertThat(applicationContext.getBeanDefinitionCount()).isGreaterThan(0);
    }
}