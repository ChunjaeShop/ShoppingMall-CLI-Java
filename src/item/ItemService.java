package item;

import Util.ScannerUtil;
import user.*;


import java.sql.Connection;
import java.util.Scanner;

// 유저 로그인, 회원가입, 유저가 볼 수 있는 메뉴의 기능들
public class ItemService {
    static ScannerUtil scannerUtil = new ScannerUtil();

    Scanner scanner = new Scanner(System.in); // 나중에 지우기
    String loggedInUserID = null;
    MemberDAO memberDAO;
    ItemDAO itemDAO;

    public ItemService(Connection conn) {
        memberDAO = new MemberDAO(conn);
        itemDAO = new ItemDAO(conn);
    }

    public boolean AllItemList() {

        // 타이틀 및 컬럼명 출력
        System.out.println();
        System.out.println("［상품 전체 보기］");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-20s%-20s%-20s%-20s%n", "category_id", "item_id", "item_name", "price");
        System.out.println("-------------------------------------------------------------------------");

        boolean result = itemDAO.printAllItemList(); // boards 테이블에서 게시물 정보를 가져와서 출력하기
        if(result)
            return true;
        else
            return false;
    }

    public boolean DetailItemSearch() {
        int itemId = -1; // 아이템 ID를 -1로 초기화

        while (itemId == -1) { // 아이템 ID가 유효하지 않은 경우 반복
            System.out.println();
            System.out.print("조회할 상품 ID : ");
            try {
                itemId = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        boolean printResult = itemDAO.printItemDetail(itemId);
        if (printResult) // itemId로 상세조회를 성공하면
            addCart();// 장바구니 담기 메뉴 -> 실제 동작 구현 필요 중요!!
        else    // 상세조회 실패하면
            return false;
    return true;
    }

    public void addCart(){
        boolean stat = true;
        do {
            System.out.println("--------------------------------------------------------------------------------------------------");
            System.out.println("메뉴 : [1.장바구니담기] [9.뒤로가기]");
            // -----아래로는 컨트롤러 옮기기
            switch (scannerUtil.scanMenu()) {
                case "1":
                    stat = true; // true 자리에 장바구니 넣는 메서드 ---DAO에 구현하기
                    System.out.println("상품이 장바구니에 담겼습니다");
                    break;
                case "9":
                    stat = false;
                    break;
                default:
                    System.out.println("유효하지 않은 메뉴입니다. 다시 선택해주세요.");
            }
        }while(!stat); // [1.장바구니담기]가 성공하면 true를 반환, 반복문을 탈출해야 함으로 (!stat)으로 조건식 작성
    }
}
