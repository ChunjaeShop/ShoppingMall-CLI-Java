--create database team_prj;
use team_prj;

drop table if exists item;
drop table if exists member;
drop table if exists categories;

CREATE TABLE member (
    u_no INT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(30) NOT NULL,
    name VARCHAR(30) NOT NULL,
    user_pw VARCHAR(10) NOT NULL,
    address VARCHAR(30) NOT NULL,
    gender VARCHAR(10) CHECK(gender IN('F','M')),
    height_cm INT,
    phone VARCHAR(30) NOT NULL,
    birth VARCHAR(30),
    UNIQUE KEY unique_name (user_id)
);
INSERT INTO member (user_id, name, user_pw, address, gender, height_cm, phone, birth)
VALUES
('test', '최지혜', '1234', '서울', 'F', 173,'01086473956', '19960213');
INSERT INTO member (user_id, name, user_pw, address, gender, height_cm, phone, birth)
VALUES
('wisdomcho2', '최지혜', '1212', '서울', 'F', 173,'01086473956', '19960213'),
('kindman', '권진철', '123123', '수원', 'M', 171,'01098225289', '19961102'),
('supportKim', '김지원', '1212', '서울', 'F', 168,'01012324565', '19970203'),
('yunsik', '강윤식', '000000', '천안', 'M', 172,'01085691425', '19980803'),
('delay2', '이지연', '111', '서울', 'F', 165,'01025961475', '19980113'),
('User16', '김민주', 'pas3', '대전', 'F', 165,'01099998888', '19971125'),
('Customer17', '정승호', 'qwerty', '서울', 'M', 178,'01077776666', '19990228'),
('Client18', '윤지수', 'zxcvbn', '부산', 'F', 175,'01055554444', '20010301'),
('Member19', '이승훈', '654321', '대구', 'M', 185,'01033332222', '20030405'),
('User20', '김유진', 'pas123', '대전', 'F', 170,'01011112222', '19970303'),
('Customer21', '박찬호', 'qwerty', '서울', 'M', 176,'01098767890', '19990415'),
('Client22', '장미경', 'zxcvbn', '부산', 'F', 172,'01087657890', '20010220'),
('Member23', '오성훈', '654321', '대구', 'M', 180,'01076545654', '20030710'),
('User24', '최수빈', 'pas123', '대전', 'F', 168,'01065436543', '19970521'),
('Customer25', '이종현', 'qwerty', '서울', 'M', 182,'01054325432', '19991128'),
('Client10', '최민우', 'abcdef', '대전', 'M', 180,'01011112222', '20001010'),
('Client13', '박재민', 'zxcvbn', '부산', 'M', 180,'01033334444', '20010203'),
('User26', '이지은', '1q2w3e4r!', '서울', 'F', 167,'01077778888', '19981230'),
('Customer27', '박민지', 'securepw', '대전', 'F', 169,'01088889999', '19991205'),
('Client28', '송영훈', '1q2w!!', '부산', 'M', 175,'01099990000', '20000120'),
('Member29', '김승우', '123456', '대구', 'M', 178,'01012341234', '20030315');


CREATE TABLE categories (
    category_id VARCHAR(30) NOT NULL,
    category_name VARCHAR(30) NOT NULL,
    UNIQUE KEY unique_name (category_id)
);

INSERT INTO categories (category_id, category_name)
VALUES
('A01', '티셔츠'),
('A02', '조끼'),
('B01', '긴바지'),
('B02', '반바지'),
('C01', '운동화'),
('C02', '구두'),
('C03', '슬리퍼'),
('E01', '양말');


CREATE TABLE item (
    category_id VARCHAR(30) NOT NULL,
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(30) NOT NULL UNIQUE,
    purchase_cnt INT,
    remain INT,
    price INT NOT NULL,
    item_contents VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);


INSERT INTO item (category_id, item_name, purchase_cnt, remain, price, item_contents)
VALUES
('A01', '후드티', 120, 60, 85000, '가볍고 스타일리시한 디자인'),
('A01', '맨투맨', 100, 40, 75000, '심플한 디자인의 맨투맨입니다'),
('A02', '니트조끼', 80, 30, 45000, '편안한 착용감의 조끼입니다'),
('A02', '레더조끼', 90, 50, 55000, '다양한 컬러로 제공되는 조끼입니다'),
('B01', '청바지', 70, 20, 125000, '고급 브랜드의 청바지입니다'),
('B01', '면바지', 110, 70, 60000, '편안한 착용감의 면바지입니다'),
('B01', '카고바지', 130, 87, 70000, '모든 옷에 어울리는 바지입니다'),
('B02', '숏팬츠', 50, 40, 35000, '여름철에 시원하게 입을 수 있는 바지입니다'),
('B02', '면 반바지', 60, 45, 45000, '다양한 디자인의 반바지입니다'),
('C01', '런닝화', 90, 30, 85000, '편안한 운동화입니다'),
('C01', '워킹화', 70, 40, 95000, '내구성이 뛰어난 워킹화입니다'),
('C02', '부츠', 110, 60, 75000, '다양한 스타일을 낼 수 있는 부츠입니다'),
('C02', '로퍼', 100, 50, 65000, '고급감 있는 디자인의 로퍼입니다'),
('C03', '실내화', 120, 70, 25000, '편안한 실내용 슬리퍼입니다'),
('C03', '슬리퍼', 130, 80, 35000, '다양한 디자인의 슬리퍼입니다'),
('C03', '샌들', 99, 110, 38000, '시원한 디자인의 샌들입니다'),
('C03', '털신', 75, 80, 49000, '겨울에 따뜻하게 신을 수 있습니다'),
('E01', '스니커즈', 80, 30, 12000, '스포티한 디자인의 스니커즈입니다'),
('E01', '골프양말', 90, 60, 10000, '고급 소재의 골프양말입니다'),
('E01', '런닝양말', 100, 70, 8000, '편안한 착용감의 런닝양말입니다'),
('E01', '캐주얼양말', 110, 80, 5000, '다양한 컬러의 캐주얼양말입니다'),
('E01', '드레스양말', 120, 90, 6000, '고급 브랜드의 드레스양말입니다');


CREATE TABLE cartlist (
    cart_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(30) NOT NULL,
    item_name VARCHAR(30) NOT NULL,
    price INT NOT NULL,
    phone VARCHAR(30),
    CONSTRAINT itme_name_fk FOREIGN KEY (item_name) REFERENCES item(item_name) on delete cascade,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES member(user_id)
);


CREATE TABLE purchase_list (
   purchase_no   INT  AUTO_INCREMENT PRIMARY KEY,
   user_id 		 VARCHAR(30) NOT NULL, -- 유저아이디
   item_name VARCHAR(30),  -- 상품명
   price INT, -- 가격
   order_date DATETIME DEFAULT CURRENT_TIMESTAMP, -- 주문날짜
   phone VARCHAR(30)); -- 전화번호


INSERT INTO purchase_list (user_id, item_name, price, phone)
VALUES
('User16', '후드티', 85000, '01012345678'),
('Client18', '청바지', 125000, '01034567890'),
('Member19', '런닝화', 85000, '01045678901'),
('User20', '맨투맨', 75000, '01056789012'),
('Customer21', '워킹화', 95000, '01067890123'),
('Member23', '실내화', 25000, '01089012345'),
('User24', '카고바지', 70000, '01090123456'),
('User16', '후드티', 85000, '01012345678'),
('Client18', '청바지', 125000, '01034567890'),
('Member19', '런닝화', 85000, '01045678901'),
('User20', '맨투맨', 75000, '01056789012'),
('Customer21', '워킹화', 95000, '01067890123'),
('Member23', '실내화', 25000, '01089012345'),
('User24', '카고바지', 70000, '01090123456'),
('User16', '후드티', 85000, '01012345678'),
('Client18', '청바지', 125000, '01034567890'),
('Member19', '런닝화', 85000, '01045678901'),
('User20', '맨투맨', 75000, '01056789012'),
('Customer21', '워킹화', 95000, '01067890123'),
('Member23', '실내화', 25000, '01089012345'),
('User24', '카고바지', 70000, '01090123456'),
('wisdomcho2', '후드티', 85000, '01022223333'),
('wisdomcho2', '맨투맨', 75000, '01022223333'),
('wisdomcho2', '청바지', 125000, '01022223333'),
('wisdomcho2', '운동화', 85000, '01022223333'),
('wisdomcho2', '슬리퍼', 35000, '01022223333');