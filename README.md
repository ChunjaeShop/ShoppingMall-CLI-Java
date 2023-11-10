# 🛍천재쇼핑몰 Java-CLI 프로그램

![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/24701101-d868-4574-a582-50c88dddd7ae)



<br>

## 프로젝트 소개

- 천재쇼핑몰은 의류와 잡화를 취급하며, 회원으로 가입하면 누구나 구매할 수 있습니다.
- 회원으로 로그인하면 상품을 검색하고, 장바구니에 담아 구매할 수 있습니다.
- 관리자는 상품을 등록/수정하고, 고객의 주문을 관리합니다.

<br>

## 팀원 구성과 역할

<div align="center">

| **권진철** | **김지원** | **유지호** | **최재혁** | **최지혜** |
| :------: |  :------: | :------: | :------: | :------: |
| [<img src="https://avatars.githubusercontent.com/u/145963704?v=4" height=150 width=150> <br/> @Jincheol-11](https://github.com/Jincheol-11) | [<img src="https://avatars.githubusercontent.com/u/40616792?v=4" height=150 width=150> <br/> @kimg1623](https://github.com/kimg1623) | [<img src="https://avatars.githubusercontent.com/u/145963790?v=4" height=150 width=150> <br/> @jiho-96](https://github.com/jiho-96) | [<img src="https://avatars.githubusercontent.com/u/145963663?v=4" height=150 width=150> <br/> @Jaehyuk-96](https://github.com/Jaehyuk-96) | [<img src="https://avatars.githubusercontent.com/u/145963612?v=4" height=150 width=150> <br/> @jyeeeh](https://github.com/jyeeeh) |
| 상품전체보기<br>상품상세조회<br>관리자 로그인<br>기능 구현 | 관리자 기능 구현<br>구현 기능 연결| DB 구축 및 Query 작성<br>주문/배송 조회<br>내정보확인<br>기능 구현 | 회원가입<br>로그인<br>Top10상품보기<br> 기능 구현 | 장바구니 기능 구현<br>발표 |

</div>

<br>

## 1. 개발 환경

**Language** <div><img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white"></div>  

**Tools** <div><img src="https://img.shields.io/badge/IntelliJIDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white"></div>  

**Collaboration** <div><img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white">
  <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white">



## 2. 개발 기간 및 작업 관리

### 개발 기간

- 전체 개발 기간 : 2023-10-28 ~ 2023-11-09
- 기획 : 2023-10-28 ~ 2023-10-31
- 기능 구현 : 2023-11-01 ~ 2023-11-09

<br>

### 작업 관리

- GitHub로 코드 형상관리를 하고, 기능별로 branch를 분리하여 협업을 진행했습니다.
- Slak을 사용하여 프로젝트 진행상황을 공유하고 Notion에 회의 회의 내용을 기록했습니다.

<br>

## 3. 요구사항 명세 및 다이어그램

<details>
  <summary>요구사항 명세서</summary>

  1. 초기화면
  ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/973c1332-38bb-4187-ab84-4ca33e168388)

2. 회원 로그인 성공 후 화면
   ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/7a5de9d5-e1a9-4336-afc7-b55d2e6ede68)

3. 장바구니/결제
   ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/70c0ce39-3f57-4930-8cd5-cf604a83802b)

4. 관리자 로그인 성공 후 메뉴 / 상품관리화면 / 주문관리화면
   ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/d85f7c9f-ab37-46a0-97d7-e831c1bc6a21)



</details>





## 4. 구현 기능

### [Main Menu]
- 프로그램을 실행하면 메인 메뉴가 나타납니다.
    - 로그인이 되어 있지 않은 경우 : 비활성화된 메뉴
    - 로그인이 되어 있는 경우 : 활성화된 메뉴
- 메인 메뉴에서는 회원과 관리자 로그인, 회원가입을 할 수 있습니다.

| 초기화면 | 회원 로그인 화면 |
| --- | --- |
| ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/0ada0842-b780-4db9-87d2-e84ca17faa16) | ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/a82d09a7-5722-4ebe-94ea-87fcf39163f7) |


<br>

### [회원 로그인]
- 회원가입은 아이디와 비밀번호를 입력하면 DB의 데이터와 비교하여 일치한 경우 로그인에 성공하고 비밀번호가 일치하지 않을 경우 불일치 경고가 아이디가 없을 경우 회원가입 문구가 표시 됩니다.

- 로그인 성공 후, 메뉴창이 활성화되며, 각 카테고리의 값을 입력하여 메뉴를 사용할 수 있습니다.


| 패스워드 불일치 | 미등록 아이디 |
| --- | --- |
| ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/f3e5f591-4a23-4344-9ded-7bdb9ea82821) | ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/b99398b4-648d-4225-9689-85a1d172741d)
 |




<br>

#### [1.상품전체보기]
- 설명

| 상품전체보기 |
|----------|
|![img](link)|

<br>

#### [1.상품전체보기]
- 설명

| 상품전체보기 |
|----------|
|![img](link)|

<br>

#### [1.상품전체보기]
- 설명

| 상품전체보기 |
|----------|
|![img](link)|

<br>

#### [1.상품전체보기]
- 설명

| 상품전체보기 |
|----------|
|![img](link)|

<br>

#### [1.상품전체보기]
- 설명

| 상품전체보기 |
|----------|
|![img](link)|

<br>


## 5. 프로젝트 후기

### 🍊 권진철

ㅇㅇ

<br>

### 👻 김지원

ㅇㅇ

<br>

### 😎 유지호

ㅇㅇ

<br>

### 🐬 최재혁

ㅇㅇ<br>

### 🐬 최지혜

ㅇㅇ
