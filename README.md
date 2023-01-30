
# 777 Team Lucky Seven 
>**“Customer, seller matching transaction service”**

<div>
 <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white">
 <img src="https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white">
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
 <img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
 <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white">
</div>
<div>
 <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white">
 <img src="https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white">
 </div>
<div>
 <img src="https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white"><img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white">
</div>

## 목차

<!-- TOC -->
* [💻 프로젝트 개발 환경](#💻)
* [👥 팀원 소개](#팀원-소개)
    * [역할](#역할)
* [프로젝트 요구사항](#프로젝트-요구사항)
* [Usecase](#usecase)
* [Table ERD](#table-erd)
* [Class UML](#class-uml)
* [API 명세](#api-명세)
<!-- TOC -->


<br>


## 💻
<details><summary> &nbsp 프로젝트 개발 환경</summary>

- spring 2.7.7

- JDK 11
- build.gradle
    ```
   dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
        implementation 'org.springframework.boot:spring-boot-starter-security'
        implementation 'org.springframework.boot:spring-boot-starter-web'
        implementation 'org.springframework.boot:spring-boot-starter-validation'
    
        compileOnly 'org.projectlombok:lombok'
        runtimeOnly 'com.h2database:h2'
        annotationProcessor 'org.projectlombok:lombok'
    
        testImplementation 'org.springframework.boot:spring-boot-starter-test'
        testImplementation 'org.springframework.security:spring-security-test'
    
        testCompileOnly 'org.projectlombok:lombok'
        testAnnotationProcessor 'org.projectlombok:lombok'
    
    
        compileOnly group: 'io.jsonwebtoken', name: 'jjwt-api', version: '0.11.2'
        runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-impl', version: '0.11.2'
        runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-jackson', version: '0.11.2'
    
        implementation 'org.springframework.boot:spring-boot-starter-data-redis'
        implementation group: 'it.ozimov', name: 'embedded-redis', version: '0.7.1'
    
        implementation 'org.springframework.boot:spring-boot-starter-websocket'
    }
    ```

- application.properties

  ```
   spring.jpa.hibernate.ddl-auto=create
    spring.jpa.generate-ddl=true
    
    spring.jpa.properties.hibernate.format_sql=true
    spring.jpa.properties.hibernate.highlight_sql=true
    logging.level.org.hibernate.SQL=debug
    logging.level.org.hibernate.type.descriptor.sql=trace
    
    spring.h2.console.enabled=true
    spring.datasource.url=jdbc:h2:mem:db;MODE=MYSQL;
    spring.datasource.username=sa
    
    jwt.secret.key=7ZWt7ZW0OTntmZTsnbTtjIXtlZzqta3snYTrhIjrqLjshLjqs4TroZzrgpjslYTqsIDsnpDtm4zrpa3tlZzqsJzrsJzsnpDrpbzrp4zrk6TslrTqsIDsnpA=
    
    spring.redis.host=localhost
    spring.redis.port=6379
    
    profile.default.image.path=/Users/sj/Downloads/default_profile.png
    profile.image.dir=/Users/sj/Downloads/user_profile_image/
    ```
</details>
<br>

## 👥 팀원 소개
이상환, 이송언, 이신희, 장성준, 조성제

### 역할

| 담당자 | 역할                                                                          |
|:---:|:----------------------------------------------------------------------------|
|     |                                                                             |
| 이상환 | - 유저 조회<br/>- 판매자 권한 요청/승인/삭제<br/>- 권한 요청 목록 조회                             |
| 이송언 | -  시큐리티<br/>- 회원가입 / 로그인<br/>-  로그아웃<br/>- 프로필 설정/조회<br/>- 리드미 작성           |
| 이신희 | - 전체 판매 상품 조회<br/>- 판매자 등록 상품 조회/검색/등록/수정/삭제(판매자 포함)<br/>- 채팅<br/>- 프로젝트 발표 |
| 장성준 | - 시큐리티(리프레시 토큰)<br/>-  레디스 적용<br/>- 카테고리 조회/생성/수정/삭제                        |
| 조성제 | - 거래 조회/요청/수락<br/>- 판매자 조회<br/>- 프로필 이미지 저장/조회<br/>- 시연 영상 제작               |


<br>

## 프로젝트 요구사항
<details><summary> 명세
</summary>- 우리팀만의 매칭 서비스 프로젝트 만들기
[ 고객-판매자 매칭 서비스 (매칭주제 자유) ]

- 회원가입/로그인/로그아웃/토큰 기능
- 유저 권한 기능
    - 유저는 3가지 권한으로 나뉩니다.
        - 고객 : 최초 회원가입한 유저
        - 판매자 : 판매자 승인을 받은 고객
        - 운영자 : 판매자 승인을 해주는 유저
- 유저 권한 별 기능
    - 고객
        - 조회
            - 나의 프로필 설정 및 조회 : 유저별 프로필(닉네임, 이미지)을 설정할 수 있고 조회
            - 전체 판매상품 목록 : 판매 상품목록을 페이징하며 조회
            - 전체 판매자 목록 : 판매자들의 목록을 페이징하며 조회
            - 판매자 정보 : 판매자를 선택해서 프로필 정보(닉네임,이미지,소개글+매칭주제 정보)를 조회
        - 작성
            - 판매자에게 요청폼 : 판매자에게 요청내용(매칭주제 정보) 보내기
        - 권한 요청
            - 판매자 등록 요청 : 판매자 프로필 요청 정보를 작성해서 운영자에게 판매자 등록 요청
    - 판매자
        - 조회
            - 나의 판매자 프로필 설정 및 조회 : 판매자별 프로필(닉네임,이미지,소개글+매칭주제 정보)을 설정, 조회
            - 나의 판매상품 조회 : 내가 판매중인 상품 목록을 페이징하며 조회
            - 고객요청 목록 조회 : 모든상품의 고객요청 목록을 페이징하며 조회
        - 등록
            - 나의 판매상품 등록 : 판매 상품 정보를 작성하여 목록에 등록
        - 수정
            - 나의 판매상품 수정/삭제 : 판매 상품 정보를 작성하여 목록에서 수정
        - 삭제
            - 나의 판매상품 삭제 : 판매 상품 정보를 작성하여 목록에서 삭제
        - 고객요청 처리 : 고객요청을 수락하고 완료처리
    - 운영자
        - 조회
            - 고객 목록 : 고객들의 목록을 페이징하며 조회
            - 판매자 목록 : 판매자들의 목록을 페이징하며 조회
            - 판매자 등록 요청폼 목록 : 판매자 등록 요청목록을 조회
        - 권한 등록
            - 판매자 권한 승인 : 판매자 등록 요청을 승인
        - 삭제
            - 판매자 권한 : 유저의 판매자 권한을 삭제
- 검색 기능
    - 키워드 검색 : 페이징 목록 조회를 할때 검색 키워드를 입력해 검색하는 기능을 추가해보세요.
    - 판매자 검색 : 페이징 목록 조회를 할때 판매자명으로 검색하는 기능을 추가해보세요.


- 고객-판매자 대화 기능
    - 대화방 생성 : 판매가 시작될때 대화방이 생성된다.
    - 대화 메세지 전송기능 : 고객과 판매자가 판매건에 대한 대화를 나눈다.
    - 대화방 메세지 목록 조회 : 고객과 판매자가 나눈 대화목록을 조회할 수 있다.
    - 대화방 종료 : 판매가 완료될때 대화방이 중지되고 더이상 메세지 전송이 불가능하다.
</details>

<br>

## Usecase
![Usecase.png](document/usecase.png)

<br>

## Table ERD
![TableERD.png](document/TableERD.png)

<br>

## Class UML
![ClassUML.png](document/ClassUML.png)

## API 명세
![img.png](document/UserAPI.png)

![img.png](document/AdminAPI.png)

![img.png](document/ItemAPI.png)

![img.png](document/TransactionAPI.png)

![img.png](document/CategoryAPI.png)

![img.png](document/ChatAPI.png)













