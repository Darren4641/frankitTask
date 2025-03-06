# OpenJDK 베이스 이미지를 사용합니다.
FROM openjdk:23-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 로컬에서 빌드된 JAR 파일을 Docker 이미지로 복사합니다.
COPY build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=dev
ENV DB_URL=db-url
ENV DB_USERNAME=db-username
ENV DB_PASSWORD=db-password

# JAR 파일을 실행합니다.
ENTRYPOINT ["java", "-Xms512m", "-Xmx768m", "-XX:+UseG1GC", "-jar", "app.jar"]

## docker build --platform linux/amd64 -t frankit-task:0.0.1 .
## docker run -d -p 8086:8080 frankit-task:0.0.1

