package user;

import item.ItemDAO;

import java.sql.Connection;
import java.util.Scanner;

// 유저 로그인, 회원가입, 유저가 볼 수 있는 메뉴의 기능들
public class UserService {
    Scanner scanner = new Scanner(System.in); // 나중에 지우기
    String loggedInUserID = null;
    MemberDAO memberDAO;
    ItemDAO itemDAO;

    public UserService(Connection conn) {
        memberDAO = new MemberDAO(conn);
        itemDAO = new ItemDAO(conn);
    }

    public String login(){ // User.login
        String userID;
        boolean passwordMatched = false;


        while (!passwordMatched) {

            do {
                System.out.println("=========회원로그인========");
                System.out.print("아이디 입력: ");
                userID = scanner.nextLine();

                if (userID.isEmpty()) {
                    System.out.println("아이디를 입력하세요.");
                }
            } while (userID.isEmpty());
            System.out.print("비밀번호 입력: ");
            String userPassword = scanner.nextLine();
            System.out.print("1.로그인 | 9.뒤로가기 : ");
            String startLogin = scanner.nextLine();
            if (startLogin.equals("1")) {
                int loginResult = memberDAO.loginConfirm(userID, userPassword);
            if (loginResult == 1) {             // 로그인 성공
                    loggedInUserID = userID;    // Set the logged-in user ID
                    System.out.println(loggedInUserID + "님 환영합니다!");
                    return loggedInUserID; // 로그인 성공하면 String으로 로그인한 ID 반환
                } else if (loginResult == 0) { // 비밀번호 불일치
                    System.out.println("비밀번호 불일치 id랑 비밀번호를 다시 입력하세요");
                } else if (loginResult == -1) { // 아이디 없음
                    System.out.println("아이디가 없습니다. 회원가입을 하시겠습니까?(Y/N)");
                    String retry = scanner.nextLine();
                    if (retry.equalsIgnoreCase("Y")) { // 회원가입을 원할 경우
                        create(); // User.create()실행
                        break;
                    } else { // 회원가입을 원하지 않는다면 메인 메뉴로 돌아가기
                        return null;
                    }
                } else {
                    System.out.println("데이터베이스 오류");
                }
            }else if(startLogin.equals("9")){ // 9.뒤로가기
                return null;
            }
        }
        return null;
    }
    public boolean create(){
        // 나중에 회원가입 입력받는 메뉴 메소드로 분리하기
        MemberDTO memberDTO = new MemberDTO();
        System.out.println("----------------------- 회원가입 ----------------------");
        System.out.println("［개인정보 입력］");
        System.out.print("이름: ");
        memberDTO.setName(scanner.nextLine());
        System.out.print("아이디: ");
        memberDTO.setUserId(scanner.nextLine());
        System.out.print("비밀번호: ");
        memberDTO.setUserPw(scanner.nextLine());
        System.out.print("주소: ");
        memberDTO.setAddress(scanner.nextLine());
        System.out.print("성별: ");
        memberDTO.setGender(scanner.nextLine());
        System.out.print("키: ");
        memberDTO.setHeight(Integer.parseInt(scanner.nextLine()));
        System.out.print("번호: ");
        memberDTO.setPhone(scanner.nextLine());
        System.out.print("생년월일: ");
        memberDTO.setBirth(scanner.nextLine());

        System.out.println("-------------------------------------------------------");
        System.out.println("보조 메뉴 : 1.회원가입 | 2.취소");
        System.out.print("메뉴 선택 : ");
        String menuNo = scanner.nextLine();
        if (menuNo.equals("1")) { // 정보입력 후 1.회원가입 메뉴 선택
            boolean resultStat = memberDAO.joinConfirm(memberDTO); // joinConfirm()으로 Insert SQL 실행
            if(resultStat) // joinConfirm()이 정상 실행되면 creat()에서 true 반환
                return true;
            else          // joinConfirm()이 실패하면 creat()에서 false 반환
                return false; // 다시 로그인화면 반복되도록 false 반환
        } else {
            System.out.println("취소하셨습니다.");
            return false;    // 다시 로그인화면 반복되도록 false 반환
        }
    }

    // 내정보 확인 메서드
    public boolean MyInfo(String loggedInUserID) {
        if (loggedInUserID != null) {
            System.out.println();
            System.out.println("［내 정보 확인］");
            System.out.println("현재 정보");
            System.out.println("-------------------------------------------------------");
            boolean printStat = memberDAO.printMyInfo(loggedInUserID);
            String menuNo;
            do{
                System.out.println();
                System.out.println("-------------------------------------------------------");
                System.out.println("메뉴 : 1.수정 | 9.뒤로가기");
                System.out.print("메뉴 선택 :");
                menuNo = scanner.nextLine();

                switch (menuNo) {
                    case "1":
                        System.out.println("수정하기");
                        MyInfoUpdate(); // 수정할 정보 입력 받아서 -> DAO 처리하고 옮
                        return true;

                    case "9":
                        return true;

                    default:
                        System.out.println("유효하지 않은 메뉴입니다.");
                        System.out.println("원래 메뉴로 돌아갑니다.");
                        return true;

                }
            }while(!menuNo.equals("1")  &&!menuNo.equals("9"));
        }
        return false;
    }

    public void MyInfoUpdate() {
        MemberDTO newMemberinfo = new MemberDTO();
        System.out.print("변경할 비밀번호 입력 (변경하지 않으려면 엔터):");
        String newPassword = scanner.nextLine();
        if (!newPassword.isEmpty()) {
            newMemberinfo.setUserPw(newPassword);
        }

        System.out.print("변경할 주소 입력 (변경하지 않으려면 엔터):");
        String newAddress = scanner.nextLine();
        if (!newAddress.isEmpty()) {
            newMemberinfo.setAddress(newAddress);
        }

        System.out.print("변경할 휴대폰 번호 입력 (변경하지 않으려면 엔터):");
        String newPhone = scanner.nextLine();
        if (!newPhone.isEmpty()) {
            newMemberinfo.setPhone(newPhone);
        }
        boolean updateStat = memberDAO.updateMemberInfo(loggedInUserID, newMemberinfo);
        if(updateStat) {// 내정보 수정 성공하면
            System.out.println("수정이 완료되었습니다.");
        }
        else {
            System.out.println("잘못된 정보입력으로 수정을 실패했습니다.");
        }
//        System.out.println();
//        String choice;
//        do {
//            System.out.println("메뉴 : [2. 메뉴로 돌아가기] [9.내정보 확인]");
//            System.out.print("메뉴 선택 :");
//            choice = scanner.nextLine();
//
//            switch (choice) {
//                case "2":
//                    LoginPassMenu();
//                    break;
//                case "9":
//                    MyInfo();
//                    break;
//                default:
//                    System.out.println("유효하지 않은 메뉴입니다.");
//            }
//        }while(!menuNo.equals("2")&&!menuNo.equals("9"));
    }



}
