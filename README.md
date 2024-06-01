# Team-D-Server

6th-NE-O-RDINARY-HACKATHON-Team-D Server

## 👥 Server 팀원
|고민영|황규혁|이승연|
|:-:|:-:|:-:|
|<img width="100px" alt="민영" src="https://github.com/6th-NE-O-RDINARY-HACKATHON-Team-D/Dofarming-Server/assets/126947828/5bbcf8aa-3df6-4401-a373-b4683e77d5a2">|<img src="https://github.com/umc-hackathon-Y/Y-Server/assets/113760409/22148297-a7db-4abd-86cf-952e35e1be61" width="100px" />|<img width="100px" alt="승연" src="https://github.com/6th-NE-O-RDINARY-HACKATHON-Team-D/Dofarming-Server/assets/126947828/ad951722-6357-42ab-8d12-5748f2231ec1">
|[@gomin0](https://github.com/gomin0)|[@Gyuhyeok99](https://github.com/Gyuhyeok99)|[@yslle](https://github.com/yslle)|




## 🌟 담당 역할
|담당 역할|Role|
|:-:|:-:|
|ERD 작성|고민영, 이승연|
|Nginx 배포, CI/CD 구축|황규혁|
|API 개발|고민영, 황규혁, 이승연 |

## 🛠️ 개발 환경
|||
|:-:|:-:|
|통합 개발 환경|IntelliJ|
|Spring 버전|3.3.0|
|데이터베이스|AWS RDS(MySQL), ElastiCache(Redis)|
|배포|AWS Elastic beanstalk, EC2|
|Project 빌드 관리 도구|Gradle|
|Java version|java 17|
|패키지 구조|도메인 패키지 구조|
|API 테스트|PostMan, Swagger(https://dev.swacademy.store/swagger-ui/index.html#/)|

## 🔧 시스템 아키텍처
<img width="612" alt="아키텍처" src="https://github.com/6th-NE-O-RDINARY-HACKATHON-Team-D/Dofarming-Server/assets/126947828/3ac71430-44fd-4de9-a440-c5db0f73cb81">

## ☁️ERD
<img width="657" alt="erd" src="https://github.com/6th-NE-O-RDINARY-HACKATHON-Team-D/Dofarming-Server/assets/126947828/2b58cd45-f11f-411b-9f29-b2d241b4998e">

## ✨Structure
```text
api-server-spring-boot
  > .ebextensions-dev // dev 서버 관련 ci/cd 구축
    | 00-makeFiles.config
    | 01-set-timezone.config
  > .github
    > ISSUE_TEMPLATE
      | ✨feat.md
      | 🆘help.md
      | 🐛bug-report.md
      | 🚑fix.md
    > worksflows
      | develop_dev.yml // dev 서버 github action을 위한 파일
  > .platform
    | nginx.conf // nginx 설정
  > * build
  > gradle
  > src.main.java.neordinary.dofarming
    > api
      > controller
      > service
    > common
      > code
        > status
          | ErrorStatus.java // 에러 응답 메시지 모아놓은 곳
          | SuccessStatus.java // 성공 응답 메시지 모아놓은 곳
        | BaseCode.java
        | BaseErrorCode.java
        | ErrorReasonDTO.java
        | ReasonDTO.java
      > config
        | AppConfig.java
        | RedisConfig.java // 레디스 관련 설정
        | Security.config.java // Spring Security 관련 설정
        | SwaggerConfig.java // Swagger 관련 설정
        | S3Config
      > exceptions
        > handler
          | ExceptionHandler.java
        | BaseException.java // Controller, Service에서 Response 용으로 공통적으로 사용 될 익셉션 클래스
        | ExceptionAdvice.java // ExceptionHandler를 활용하여 정의해놓은 예외처리를 통합 관리하는 클래스
      | BaseEntity.java // create, update, state 등 Entity에 공통적으로 정의되는 변수를 정의한 BaseEntity
      | BaseResponse.java // Controller 에서 Response 용으로 공통적으로 사용되는 구조를 위한 모델 클래스
    > domain
      > mapping
    > utils
      > jwt // jwt 관련
        | JwtAuthenticationFilter.java
        | JwtProvider.java
        | LogoutService.java // 로그아웃
      > s3
        | S3Provider.java
      | ApplicationAuditAware.java
      | RedisProvider.java // 레디스 서비스
    | AcademyApplication // SpringBootApplication 서버 시작 지점
  > resources
    | application.yml // Database 연동을 위한 설정 값 세팅 및 Port 정의 파일
    | application-dev.yml // dev 연동
    | application-local.yml // local 연동
build.gradle // gradle 빌드시에 필요한 dependency 설정
.gitignore // git 에 포함되지 않아야 하는 폴더, 파일들을 작성

```
## 환경 설정 내역
- Local 실행 시
  - 실행 방법: 프로젝트를 로컬 환경에서 실행할 때는 환경 변수 또는 설정 파일을 통해 local 모드로 설정
  - 데이터베이스 접속: 로컬에 설치된 MySQL 데이터베이스에 접속, 로컬 데이터베이스의 접속 정보(호스트, 포트, 사용자 이름, 비밀번호 등)는 개발 환경에 맞게 설정.
  - 캐시 서버 접속: 로컬에서 실행 중인 Redis 인스턴스에 접속, 로컬 Redis 서버의 접속 정보를 환경에 맞게 설정.

- Dev 실행 시
  - 실행 방법: 개발 환경에서는 환경 변수 또는 설정 파일을 dev 모드로 설정
  - 데이터베이스 접속: AWS RDS(MySQL) 인스턴스에 접속, 개발 환경에 맞는 RDS 인스턴스의 접속 정보(엔드포인트, 포트, 사용자 이름, 비밀번호 등)를 설정 파일에 명시해야 함.
  - 캐시 서버 접속: AWS ElastiCache(Redis) 인스턴스에 접속, 개발 환경에 맞는 ElastiCache 인스턴스의 접속 정보(엔드포인트, 포트 등)를 설정 파일에 명시해야 함.

- Prod 서버 아직 존재 x

## 🌱 Branch
-  main : 최종
-  develop : 개발
-  feat : 기능 개발
-  refactor : 기능 수정
-  ci : ci/cd 구축
