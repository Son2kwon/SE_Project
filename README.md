# 이슈 관리 프로그램

소프트웨어공학 02분반 Team7 - 손의권, 강성준, 김성훈, 신성섭, 이재규
## <Required>

  - ![Node.js](https://img.shields.io/badge/node.js-20.10.0-green)
  - ![npm](https://img.shields.io/badge/npm-10.2.3-red)
  - ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)
  - ![Java](https://img.shields.io/badge/java-17%2B-blue)

  - **MySQL**에 `seproject` 데이터베이스 생성
  - `SE_Project/src/main/resources/application-secret.properties` 파일 작성: 
    ```properties
    spring.datasource.username=username
    spring.datasource.password=password
    ```

## <How to Execute>

### 리액트 실행
```sh
cd frontend
npm install
npm start
```
### 스프링 실행
```
  java -jar SE_Project/build/libs/issueManagementSystem-0.0.1-SNAPSHOT.jar
```
### 접속
```
  http://localhost:3000/
```

  
