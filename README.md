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
<br>

<details>
  <summary>유스케이스 다이어그램</summary>

  ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/ca26d634-302b-419f-89f5-eca2cb1f5442)



</details>

<br>

<details>
  <summary>시퀀스 다이어그램</summary>

  1. Actor : 회원
  ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/288f651e-bcb9-43cb-b96b-845c0ed1aa15)

  
  
  3. Actor : 관리자
  ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/ca35abda-51d9-4136-b2bf-62bf1b17337c)


</details>

<br>

<details>
  <summary>클래스 다이어그램</summary>


</details>

<br>

<details>
  <summary>블록 다이어그램</summary>
  
  ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/870d8b2f-f010-49e8-864b-e4f3113719a2)

</details>

<br>

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

### [회원 가입]
- 회원 가입을 하면 DB의 member 테이블에 입력 받은 정보를 저장합니다.
  
  ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/98e02ab3-449e-45a8-a302-17280991e456)


<br>

### [회원 로그인]
- 회원 로그인은 아이디와 비밀번호를 입력하면 DB의 데이터와 비교하여 일치한 경우 로그인에 성공하고 비밀번호가 일치하지 않을 경우 불일치 경고가 아이디가 없을 경우 회원가입 문구가 표시 됩니다.

- 로그인 성공 후, 메뉴창이 활성화되며, 각 카테고리의 값을 입력하여 메뉴를 사용할 수 있습니다.


| 패스워드 불일치 | 미등록 아이디 |
| --- | --- |
| ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/f3e5f591-4a23-4344-9ded-7bdb9ea82821) | ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/b99398b4-648d-4225-9689-85a1d172741d)
 |


<br>


#### [1. 상품전체보기]
- 1을 입력 받으면 item 테이블에 저장되어 있는 상품들을 보여줍니다.

| 상품전체보기 |
|----------|
|![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/c0801a23-3e3a-4c2a-8c12-162d39f1b70f)|

<br>

#### [2. 상품상세조회]
- 2를 입력 받으면 해당 item의 상세정보를 보여주며, 장바구니로 바로 담을 수 있도록 하였습니다.

| 상품전체보기 |
|----------|
|![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/5a99ebf1-c06c-42b7-84f1-c1f89fb8334c)|

<br>


#### [3. 주문/배송조회]
- 3을 입력 받으면 해당 구매한 상품의 정보를 확인할 수 있습니다
- 주문수정, 주문취소를 할 수 있습니다.
- 주문수정을 통해 배송지를 수정할 수 있습니다
- 주문 취소를 통해 구매한 상품을 삭제할 수 있습니다.

| 주문/배송조회 | 주문수정 | 주문 취소 |
| --- | --- | --- |
| ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/27850197-db15-4327-ac15-7608482d6337) | ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/9218081d-02ba-493b-b130-9ebcc1c02c00) | ![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/106df261-abb5-4701-97cb-7bc52b01c813) |

<br>



#### [4. Top10 상품보기]
- 4를 입력 받으면 해당 누적 카운트가 많은 순으로 상위 10개의 상품을 보여주었습니다.

| Top10 상품 |
|----------|
|![image](https://github.com/kimg1623/Shopping-CLI-Java/assets/145963790/8f1d8548-fdfe-4ffc-94a0-e53fec0d6eab)|

<br>

#### [5. 장바구니]
- 설명

| 상품전체보기 |
|----------|
|이미지|

<br>


#### [9. 내정보확인]
- 설명

| 상품전체보기 |
|----------|
|이미지|

<br>


## 5. 프로젝트 후기

### 🍊 권진철

코드를 설계할 때 가장 기본이라고 할 수 있는 CRUD를 처음으로 직접 적용해본 경험이었습니다. 만든 코드로 기능을 구현해보고 그것을 DB와 연동하여 IntelliJ에 나타냈지만, DB 구축에는 아직 낯설어 참여하지 못했던 것이 아쉬웠습니다. 프로젝트를 진행하면서 가장 크게 느꼈던 점이 있었습니다. 바로 '완벽'했을 때 비로소 프로젝트가 '완성'된다는 것이었습니다. 예를 들면 부여된 값 외의 다른 값을 입력할 때에도 코딩을 하는 것처럼 모든 상황을 대비할 수 있어야 한다고 생각했습니다. 부족한 부분은 Git과 함께 리뷰를 하면서 조금씩 채워나가겠습니다.

<br>

### 👻 김지원

이번 프로젝트에서는 CRUD기능을 모두 활용하는 것과 MVC패턴을 적용해보기 위해 노력했다. 화면을 구성하면서 CRUD가 모두 구현이 가능한지 점검했고, 이후 필요한 데이터들로 DB를 구축했는데 실제 개발을 진행하다보니 다시 고쳐야할 부분들이 보였다. 프로젝트 계획이 탄탄해야하는 이유를 경험할 수 있었다. MVC2 패턴을 적용해보고 싶었는데, Controller와 Service의 차이를 이해하는데 어려워서 완벽히 적용은 어려웠지만 시도해봤다는 점이 의미있었다. 처음으로 Git을 제대로 사용해서 진행한 프로젝트였는데, Git협업에 자신감이 조금 생기게 된 것 같다.

<br>

### 😎 유지호

처음으로 CRUD 기능을 구현하며 데이터베이스와의 상호작용을 경험했다. 화면을 구성하고 각 기능을 완성하며 결합하는 과정에서 예상치 못한 오류들이 발생하였다. 이러한 상황에 대비하여 팀원들과의 협업 과정에서 커뮤니케이션과 주석의 중요성을 느낄 수 있었다. 또한, 프로젝트를 시작하기 전에 요구사항 명세서와 다이어그램을 제작하면서 개발의 전체적인 흐름을 파악할 수 있는 기회였다. 처음 개발 프로젝트를 진행하면서 효율적인 코드 작성과 패키지 및 클래스 분리에 대한 어려움이 있었다. 이러한 부분에 대한 보완이 필요하다고 느꼈다.

<br>

### 🐬 최재혁

이번에 처음으로 프로젝트를 진행하면서 CRUD 기능과 DB연결로 프로그램을 구현했다. 프로그램을 위한 사전작업들을 해보며 프로젝트의 구조와 흐름을 파악해 볼 수 있는 값진 경험이었다. CRUD기능을 구현하면서 여러 시행착오가 발생했었는데 팀원들과의 소통을 통해 해결하면서 협업의 중요성을 알 수 있었다. 요번에 프로젝트를 진행하면서 코드를 한곳에 계속해서 작성하다보니 가독성도 떨어지고 나중에 코드의 분리가 힘들었다. 또한 DB 테이블에 대한 이해도 미흡했다. 다음 프로젝트때는 이런부분에 대해서 조금더 체계적으로 접근해서 개선해야겠다.

<br>

### 🐬 최지혜

이론으로만 접했을 때는 이해도가 떨어졌었는데 이번 프로젝트를 진행 하면서 이론적인 부분이 자리 잡힌 것 같아 프로젝트의 중요성을 다시 한번 깨달았다. Git을 사용해본 협업 프로젝트는 처음이라 많이 서툴렀지만 팀원들과 같이 배워나가며 오류사항 등을 개선해나갔다. DAO/DTO 구분만 해놓았을 때 오류의 시작점을 찾기 힘들었어서 앞으로는 작업할 때 코드 분리 작업을 선행해야겠다. 요구사항 명세서, 다이어그램  등 구체적으로 계획을 세우면서 프로젝트 진행 경험을 쌓았는데 이것을 바탕으로 발전되는 계기가 되었으면 좋겠다.
