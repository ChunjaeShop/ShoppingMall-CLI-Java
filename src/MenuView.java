import Util.ScannerUtil;
import Util.DBUtil;
import item.ItemService;
import user.*;
import admin.*;

import java.sql.Connection;
import java.util.Scanner;

public class MenuView {
    private ConsoleTextControl consoleTextControl = new ConsoleTextControl();
    static ScannerUtil cs = new ScannerUtil(); // scanner 컨트롤러로 usage 확인해서 나중에 옮기기
    Scanner scanner = new Scanner(System.in);
    DBUtil dbUtil = new DBUtil();
    UserService userService;
    ItemService itemService;
    AdminService adminService;
    String loggedInUserId;
    MemberDAO memberDAO;

    public MenuView(Connection conn) {
        memberDAO = new MemberDAO(conn);
        userService = new UserService(conn);
        itemService = new ItemService(conn);
        adminService = new AdminService(conn);
    }

    public void printDisabledMainMenu(){ // 0. 로그인 전 메인메뉴 (비활성화)
        System.out.println("--------------------------------------------");
        consoleTextControl.logMassge("\t\t\t[천재쇼핑몰 Main Menu]", "green");
        consoleTextControl.logMassge("- 로그인 후 메뉴를 이용할 수 있습니다.","black");
        System.out.println("0.상품전체보기\t\t0.상품상세조회\t\t0.주문/배송조회");
        System.out.println("0.Top10상품보기\t\t0.장바구니\t\t\t0.내정보확인");
    }

    public int printLoginMenu(){ // 1. 로그인/회원가입 메뉴
        System.out.println("--------------------------------------------");
        System.out.println("1. 회원 로그인 | 2. 회원가입 | 3. 관리자 로그인");

        // -----아래로는 컨트롤러 옮기기
        boolean stat = true;
        int result = 0;
        do { // 회원/관리자 로그인, 회원가입이 성공하면 stat으로 true 반환
            switch (cs.scanMenu()) {
                case "1":  // 회원 로그인 메뉴
                    loggedInUserId = userService.login();
                    result = 1;
                    break;
                case "2": // 회원가입 메뉴
                    stat = userService.create();
                    result = 2;
                    break;
                case "3": // 관리자 로그인 메뉴
                    stat = adminService.login();
                    result = 3;
                    break;
                default:
                    System.out.println("유효한 메뉴를 선택하세요. (1 ,2 ,3 중 선택하세요)");
            }
        } while(result<=1 && result>=3); // stat이 true일 경우 반복문을 탈출해야 하므로 (!stat)으로 조건 지정
        return result;
    }
    public String getLoggedInUserId(){
        return loggedInUserId;
    }

}
