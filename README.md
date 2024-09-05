# 🏠 Boothouse - 부트캠프 정보 및 리뷰 플랫폼 1차 readme

Boothouse는 IT 부트캠프 정보를 한 눈에 볼 수 있고, 수강생들의 솔직한 리뷰를 공유할 수 있는 플랫폼입니다.

---

## 📅 개발 기간
2024.07.29 ~ 2024.09.05

---

## 👥 팀원 및 역할

| 이름 | 역할 | 수행업무 |
|------|------|----------|
| 김민지 | Backend, Frontend | Bootcamp, Course, Frontend(Layout, Event) |
| 장세창 | Backend, Frontend, Document Management | Chatting, Frontend(Layout, Event) |
| 서재필 | Backend, PR Reviewer | Admin, User, Project module design & director  |
| 이예지 | Backend | Notice, like, API |
| 임희진 | Backend, Frontend, Document Management | Review, Frontend(Layout) |

---

## 🚀 주요 기능

1. 📚 부트캠프 정보 조회
   - 다양한 IT 부트캠프 정보 제공
   - 카테고리별, 조건별 필터링 기능

2. 📝 리뷰 시스템
   - 수강생들의 솔직한 리뷰 작성 및 조회
   - 리뷰 검색 및 정렬 기능

3. ❤️ 찜하기 기능
   - 관심 있는 부트캠프와 리뷰를 찜하기 가능

4. 💬 채팅 시스템
   - 사용자 간 실시간 채팅 기능

5. 📢 공지사항
   - 관리자가 작성하는 공지사항 및 이벤트 정보

---

## 🛠 기술 스택

- Backend: Spring Boot 2.7.13, Java 17
- Frontend: Thymeleaf, TailwindCSS
- Database: PostgreSQL, Redis
- ORM: Spring Data JPA, Querydsl
- Authentication: JWT
- Websocket: STOMP
- Build Tool: Gradle
- Version Control: Git, GitHub
- Containerization: Docker

---

## 🗂 프로젝트 구조

프로젝트는 멀티 모듈로 구성되어 있습니다:

- `db`: 데이터베이스 관련 설정 및 엔티티
- `view`: 웹 애플리케이션 메인 모듈

---

## 🌟 주요 특징

1. **멀티 모듈 아키텍처**
   - `db`와 `view` 모듈로 분리하여 관심사 분리 및 모듈 간 의존성 관리
   - `db` 모듈: 데이터베이스 관련 설정, 엔티티, 리포지토리 포함
   - `view` 모듈: 웹 애플리케이션의 컨트롤러, 서비스, 뷰 템플릿 포함

2. **계층 구조**
   - Presentation Layer (컨트롤러, 뷰 템플릿) / Business Layer (서비스) / Persistence Layer (리포지토리, 엔티티) 구성
   - 각 계층 간 책임 분리로 유지보수성 및 테스트 용이성 향상

3. **ORM 및 동적 쿼리**
   - JPA를 사용한 객체-관계 매핑으로 데이터베이스 작업 추상화
   - Querydsl을 활용한 동적 쿼리 구현으로 복잡한 검색 및 필터링 기능 구현

4. **인증 및 보안**
   - JWT를 이용한 토큰 기반 인증 시스템 구현을 통한 사용자 인증 정보의 안전한 관리 및 전송

5. **실시간 통신**
   - WebSocket과 STOMP를 이용한 실시간 채팅 기능 구현
   - 날짜별 채팅 내용 최적화로 대화 이력 관리 효율성 증대

6. **반응형 웹 디자인**
   - Thymeleaf를 이용한 서버 사이드 렌더링 구현
   - TailwindCSS를 활용한 반응형 디자인으로 다양한 디바이스에 대응

7. **빌드 및 버전 관리**
   - Gradle을 사용한 효율적인 빌드 및 의존성 관리
   - Git과 GitHub를 활용한 협업 및 버전 관리

8. **컨테이너화**
   - Docker를 활용한 애플리케이션 컴포넌트(JWT, PostgreSQL 등)의 컨테이너화
   - 개발, 테스트, 배포 환경의 일관성 확보 및 확장성 개선
  
9. **성능 최적화**
   - Redis 캐싱을 활용한 채팅 시스템 및 자주 조회되는 데이터의 응답 시간 단축
   - 날짜별 메시지 그룹화를 통한 효율적인 채팅 이력 관리 및 조회 성능 향상
   - QueryDSL을 활용한 동적 쿼리 최적화 및 성능 개선 (처리 시간 84.2% ~ 92.9% 감소, 처리량 67.4% ~ 416.4% 증가)

10. **데이터 크롤링**
      - 부트캠프 정보 자동 수집을 위한 크롤링 기능 구현
      - 최신 부트캠프 정보의 주기적 업데이트 지원

11. **모니터링**
      - JMeter를 활용한 정기적인 성능 테스트 실시
      - 부하 수준별 (낮음, 중간, 높음) 성능 메트릭 추적 및 분석
      - 성능 테스트 결과를 바탕으로 한 지속적인 쿼리 및 애플리케이션 최적화
