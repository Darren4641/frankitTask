# Frankit 과제 수행 (백엔드 지원자 김대현)

| 사용 기술       | 버전    |
|-------------|-------|
| Spring Boot | 3.4.3 |
| Java        | 23    |

| IDE      |
|----------|
| IntelliJ |

## Demo URL

| url                   | https://frankit.tonglink.site         |
|-----------------------|------------|


# Docker Build
```shell
docker build --platform linux/amd64 -t frankit-task:0.0.1 .
docker run -d -p 8086:8080 frankit-task:0.0.1
```

# Jar Build
```shell
./gradlew bootJar
java -jar build/libs/FrankTaskJava-frankit-task-0.0.1.jar 
```


# 시연 영상
<img src="./frankit.gif" width="600" height="350">

