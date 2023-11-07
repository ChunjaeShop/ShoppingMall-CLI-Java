import Util.DBUtil;
import Util.ScannerUtil;
import admin.AdminService;
import admin.ConsoleTextControl;
import item.ItemService;
import item.PurchaseListService;
import user.MemberDAO;
import user.UserService;

import java.sql.Connection;
import java.util.Scanner;

public class UserView {
    private ConsoleTextControl consoleTextControl = new ConsoleTextControl();
    static ScannerUtil cs = new ScannerUtil(); // scanner 컨트롤러로 usage 확인해서 나중에 옮기기
    Scanner scanner = new Scanner(System.in);
    DBUtil dbUtil = new DBUtil();
    UserService userService;
    ItemService itemService;
    AdminService adminService;
    PurchaseListService purchaseListService;
    MemberDAO memberDAO;
    String loggedInUserId;

    public UserView(Connection conn) {
        memberDAO = new MemberDAO(conn);
        userService = new UserService(conn);
        itemService = new ItemService(conn);
        adminService = new AdminService(conn);
        purchaseListService = new PurchaseListService(conn);
    }

    public void printDisabledMainMenu(){ // 0. 로그인 전 메인메뉴 (비활성화)
        System.out.println("--------------------------------------------");
        consoleTextControl.logMassge("\t\t\t[천재쇼핑몰 Main Menu]", "green");
        consoleTextControl.logMassge("- 로그인 후 메뉴를 이용할 수 있습니다.","black");
        System.out.println("0.상품전체보기\t\t0.상품상세조회\t\t0.주문/배송조회");
        System.out.println("0.Top10상품보기\t\t0.장바구니\t\t\t0.내정보확인");
    }


    public void userLoginPassMenu() {
        System.out.println();
        String menuNo;

        // -----아래로는 컨트롤러 옮기기
        boolean stat = true;
        int result = 0;
        do { // userLoginPassMenu의 메뉴들을 실행 완료하면 stat으로 true 반환
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("[1.상품전체보기] [2.상품상세조회] [3.주문/배송조회] [4.Top10상품보기]   [5.장바구니]   [9.내정보확인]  [0.로그아웃/종료]");
            System.out.println("-----------------------------------------------------------------------------------");
            switch (cs.scanMenu()) {
                case "1":
                    stat = itemService.AllItemList();
                    break;
                case "2":
                    stat = itemService.DetailItemSearch();
                    break;
                case "3":
                    stat = purchaseListService.UserPurchaseList(loggedInUserId);
                    break;
                case "4":
                    stat = itemService.itemRanking();
                    break;
                case "5":
                    //Cart();
                    break;
                case "9":
                    //MyInfo();
                    break;
                case "0":
                    stat = false;
                default:
                    System.out.println("유효하지 않은 메뉴입니다.");
            }
        }while(stat); // 0입력해서 stat이 fasle일 경우에만 반복문 종료
        System.out.println("프로그램 종료");
    }

    public void setLoggedInUserId(String loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }
}