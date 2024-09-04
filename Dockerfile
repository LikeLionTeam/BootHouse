# 1단계: 빌드 이미지 설정
FROM openjdk:17-jdk-slim AS builder

# 작업 디렉토리 설정
WORKDIR /app

# Gradle Wrapper 및 빌드 파일 복사
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

# 각 서브프로젝트의 소스 코드 및 빌드 파일 복사
COPY db/build.gradle ./db/build.gradle
COPY db/src ./db/src
COPY view/build.gradle ./view/build.gradle
COPY view/src ./view/src

# 빌드 실행
RUN ./gradlew :view:build -x test

# 2단계: 실행 이미지 설정
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 결과물 복사
COPY --from=builder /app/view/build/libs/*.jar boothouse.jar

RUN mkdir -p /app/noticeImages

# 애플리케이션 실행 명령어 설정
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "boothouse.jar"]

# 포트 노출
EXPOSE 8080
