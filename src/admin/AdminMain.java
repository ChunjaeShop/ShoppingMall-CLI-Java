package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminMain {
    Scanner scanner = new Scanner(System.in);
    private Connection conn;

    public AdminMain() {

    }

    public void mainMenu(Connection conn) { // 3. 관리자메뉴(관리자 로그인 성공화면)
        this.conn = conn;
        System.out.println();
        String menuNo;

        do {
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("* 관리자님 환영합니다! **");
            System.out.println("[1.상품전체보기]  [2.주문내역조회] ");
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.print("메뉴 선택 :");
            menuNo = scanner.nextLine();

            switch (menuNo) {
                case "1":
                    AllItemList(); // 3-1
                    //listSubmenu();
                    break;
                case "2":
                    //DetailItemSearch();
                    break;
                case "4":
                    //itemRank();
                    break;
                case "9":
                    //MyInfo();
                    break;
                default:
                    System.out.println("유효하지 않은 메뉴입니다.");

            }
        }while(!menuNo.equals("1")  && !menuNo.equals("2"));
    }


    public void AllItemList() {
    }


}
