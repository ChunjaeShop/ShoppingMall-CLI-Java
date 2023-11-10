import Util.ConsoleTextControl;
import Util.ScannerUtil;
import Util.DBUtil;
import item.ItemService;
import user.*;
import admin.*;

import java.sql.Connection;
import java.util.Scanner;

public class MenuView {
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
        ConsoleTextControl.printColorln("      _____ _                 _", "green");
        ConsoleTextControl.printColorln("     / ____| |               (_) ", "green");
        ConsoleTextControl.printColorln("    | |    | |__  _   _ _ __  _  __ _  ___  ", "green");
        ConsoleTextControl.printColorln("    | |    | '_ \\| | | | '_ \\| |/ _` |/ _ \\", "green");
        ConsoleTextControl.printColorln("    | |____| | | | |_| | | | | | (_| |  __/", "green");
        ConsoleTextControl.printColorln("     \\_____|_| |_|\\__,_|_| |_| |\\__,_|\\___|", "green");
        ConsoleTextControl.printColorln("     / ____| |              _/ |   (_)  ", "green");
        ConsoleTextControl.printColorln("    | (___ | |__   ___  _ _|__/ __  _ _ __   __ _", "green");
        ConsoleTextControl.printColorln("     \\___ \\| '_ \\ / _ \\| '_ \\| '_ \\| | '_ \\ / _` |", "green");
        ConsoleTextControl.printColorln("     ____) | | | | (_) | |_) | |_) | | | | | (_| |", "green");
        ConsoleTextControl.printColorln("    |_____/|_| |_|\\___/| .__/| .__/|_|_| |_|\\__, |", "green");
        ConsoleTextControl.printColorln("                       | |   | |             __/ |", "green");
        ConsoleTextControl.printColorln("                       |_|   |_|            |___/ ", "green");
        ConsoleTextControl.printColorln("┌------------------------------------------------------┐", "green");
        ConsoleTextControl.printColorln("│\t\t\t\t [천재쇼핑몰 Main Menu]\t\t\t\t   │", "green");
        ConsoleTextControl.printColorln("└------------------------------------------------------┘", "green");
        ConsoleTextControl.printColorln("         - 로그인 후 메뉴를 이용할 수 있습니다 - ","exit");
        ConsoleTextControl.printColorln("0.상품전체보기\t\t0.상품상세조회\t\t0.주문/배송조회","exit");
        ConsoleTextControl.printColorln("0.Top10상품보기\t\t0.장바구니\t\t\t0.내정보확인","exit");
        ConsoleTextControl.printColorln("--------------------------------------------------------","exit");
    }

    public int printLoginMenu(){ // 1. 로그인/회원가입 메뉴

        ConsoleTextControl.printColorln("1.회원 로그인 | 2.회원가입 | 3.관리자 로그인","purple");
        // -----아래로는 컨트롤러 옮기기
        boolean stat = true;
        int result = 0;
        do { // 회원/관리자 로그인, 회원가입이 성공하면 stat으로 true 반환
            switch (cs.scanMenu()) {
                case "1":  // 회원 로그인 메뉴
                    do {
                        loggedInUserId = userService.login();
                        if(loggedInUserId == "main")
                            return 2;   // 로그인 시도할 때 main으로 돌아가기 눌렀을 때
                    }while (loggedInUserId==null);
                    result = 1;
                    break;
                case "2": // 회원가입 메뉴
                    stat = userService.create();
                    result = 2;
                    break;
                case "3": // 관리자 로그인 메뉴
                    stat = adminService.login();
                    if(stat)
                        return 3; // 관리자 로그인 성공하면, 관리자 메뉴 나오도록 3반환
                    else
                        return 2; // 관리자 로그인 실패하면, 메인메뉴(비활)로 돌아가도록 2반환
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
