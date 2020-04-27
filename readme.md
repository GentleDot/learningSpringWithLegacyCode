# 스프링 웹 프로젝트를 Spring Boot로 구현하기
세부적인 내용은 [Notion_Gentledot](https://www.notion.so/gentledot/Spring-Boot-4906cbe24eb54227975a7a9699288743) 페이지에 정리하였음.

## 목표
- 도서[코드로 배우는 스프링 웹 프로젝트 / 구멍가게코딩단 (남가람북스, 2016)] 내 코드 구현 방법을 따라가면서 소개되어 있는 Spring Code를 참조, Spring Boot v2.2.6 으로 구현
- 진행 순서는 책에서 구성된 Part를 따름
- 도서 예제는 Spring, MyBatis, MySQL을 사용하여 프로젝트를 진행 (IDE : STS)
- 실제 구현은 IntelliJ IDE를 사용하여 Gradle 사용 Spring Starter Project를 생성

##  도서의 구성
### Part 1： 프로젝트의 기본 구조 구성

- 스프링의 몇 가지 특징
- 스프링 MVC, MyBatis 개발 환경 설정
- 개발에 사용하는 MySQL 데이터베이스의 설정과 테스트

### Part 2： 기본적인 기능의 게시물 관리

- 단순한 등록, 수정, 삭제, 조회와 리스트 기능의 게시판 만들기
- 페이징 처리와 검색 기능

### Part 3：Ajax 댓글 처리

- REST 방식의 개발과 @RestController
- jQuery와 Ajax를 이용하는 서버 호출
- Ajax와 REST 방식을 이용하는 게시물에 대한 댓글 처리

### Part 4： AOP와 트랜잭션 처리

- AOP의 개념과 설정
- 트랜잭션 관리의 설정과 테스트

### Part 5： 게사물의 첨부파일 가능

- <form>을 이용하는 파일 업로드
- Ajax를 이용하는 파일 업로드
- 썸네일 이미지 처리와 다운로드

### Part 6： 인터셉터를 활용하는 로그인 처리

- 인터셉터와 필터
- 로그인 처리와 로그인 체크
- 세션 방식으로 로그인 처리
- 자동 로그인 처리


# 구현 내용
## 프로젝트의 기본 구조 구성
### 개발 환경 구성
1. Spring Boot 생성 및 MySQL과의 Transaction 구현을 위한 MyBatis 의존성 설정
    - Spring Boot v2.2.6
    - Gradle Project
    - spring-boot-starter-tomcat:2.2.6.RELEASE
        - Tomcat 9.0
    - JSP를 view로 사용하기 위해 library 추가
        - javax.servlet:jstl
        - tomcat-embed-jasper     
    - MySQL과의 접속을 위한 의존성 추가 (MySQL Connector/J, JDBC 5.2.5)
        - mysql-connector-java:8.0.19
        - spring-boot-starter-data-jdbc:2.2.6.RELEASE
    - MyBatis 사용을 위한 의존성 추가
        - mybatis-spring-boot-starter:2.1.2
    - 그 외 편의를 위해 추가한 library들
        - common-lang3
        
2. 환경 설정 관련 테스트 진행 (Junit 활용)
    - MySQL 연결 테스트
    - JDBC 연결 테스트
    - MyBatis 연결 테스트 (SqlSessionFactory)
    
3. Controller 구현
    - SampleController
    
4. MyBatis를 통한 Transaction 구현
    - DB 내 table 생성 : tbl_member
    - Domain 생성 : Member.class
    - Mapper 구성 : interface MemberMapper
    - 동작 테스트 : MemberMapperTest.class
    - log4jdbc-log4j2 로그 사용
    
    
## 기본적인 기능의 게시물 관리
### 기능 구현 계획
- 개발 목표 (순서 및 시나리오 설정)
- 개발 준비
    - DB
    - Spring MVC
    - View

### 프로젝트 생성 준비
- DB 작업
    - Datasource 설정
    - Table 생성 : tbl_board
    
- MVC의 한글 깨짐 처리 해결
    - UTF-8 처리 필터 등록

- View 구현 준비
    - static resouce의 path mapping 설정

### Domain 객체 구현
- Board.class 생성
- Board 생성 Test 

### Repository 구현
- BoardMapper Interface 생성 (@Mapper)
- BoardMapper 기능 Test (@MybatisTest)

### Service 구현
- BoardService 구현
    - 등록
    - 조회
    - 수정
    - 삭제

### Controller 구현
- BoardController 구현
    - 게시물 등록 기능
        - 등록 화면 요청 : boardRegisterView() [GET board/register]
        - 등록 요청 : registBoard() [POST board/register]
    - 게시물 목록 조회
        - 목록 화면 요청 : listAllBoard() [GET board/listAll]
    - 게시물 조회
        - 조회 화면 요청 : readBoard() [GET board/read?bno=#]
    - 게시물 삭제
        - 삭제 요청 : removeBoard() [DELETE board/remove]
    - 게시물 수정
        - 수정 화면 요청 : boardModifierView() [GET board/modify?bno=#]
        - 수정 요청 : modifyBoard() [PUT board/modify]

### 예외 처리 설정
- CommonExceptionAdvice.class 를 통해 예상되는 Exception 처리
    - [log_debug] apiErrorHandler() : controller, service 내 비즈니스 로직 처리에서 예외가 발생하는 경우
    - [log_warn] myBatisSystemExceptionHandler() : MyBatis 기능 동작에서 예외가 발생한 경우
    - [log_warn] nullPointerExceptionHandler() : null을 참조할 때 발생하는 예외
    - [log_error] unexpectedExceptionHandler() : 예상하지 못한 예외가 발생한 경우

### 페이징 처리
- boardMapper 내 요청한 수의 게시물 list를 조회할 수 있는 기능 추가
- boardService 내 게시물 list 및 pagination 구성에 필요한 정보를 담은 Map을 반환하는 method 구성
- boardController 내 페이징 처리에 필요한 criteria, pageMaker를 사용한 method 추가
- 목록 및 페이지 정보를 유지할 수 있도록 수정된 listPage, readPage, modifyPage view 추가