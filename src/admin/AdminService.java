package admin;

import user.MemberDAO;

import java.sql.Connection;
import java.util.Scanner;

public class AdminService {
    Scanner scanner = new Scanner(System.in); // 나중에 지우기
    String loggedInUserID = null;
    MemberDAO memberDAO;

    public AdminService(Connection conn) {
        memberDAO = new MemberDAO(conn);
    }

    public boolean login(){
        String adminID;
        String adminPassword;
        do {
            System.out.print("관리자 아이디 입력: ");
            adminID = scanner.nextLine();
            if (adminID.isEmpty()) {
                System.out.println("아이디를 입력하세요");
            }
        } while (adminID.isEmpty());

        do {
            System.out.print("관리자 비밀번호 입력: ");
            adminPassword = scanner.nextLine();
            if (adminPassword.isEmpty()) {
                System.out.println("비밀번호를 입력하세요");
            }
        } while (adminID.isEmpty());


        if (adminID.equals("host") && adminPassword.equals("1234")) {
            System.out.println("관리자로 로그인하셨습니다!");
        } else {
            System.out.println("관리자 로그인 실패");
            return true;
        }
        return false;
    }
}
