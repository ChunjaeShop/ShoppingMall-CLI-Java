package item;

import Util.ScannerUtil;
import user.MemberDAO;

import java.sql.Connection;
import java.util.Scanner;

// 유저 로그인, 회원가입, 유저가 볼 수 있는 메뉴의 기능들
public class PurchaseListService {
    static ScannerUtil scannerUtil = new ScannerUtil();

    Scanner scanner = new Scanner(System.in); // 나중에 지우기
    String loggedInUserID = null;
    MemberDAO memberDAO;
    ItemDAO itemDAO;
    PurchaseListDAO purchaseListDAO;

    public PurchaseListService(Connection conn){
        memberDAO = new MemberDAO(conn);
        itemDAO = new ItemDAO(conn);
        purchaseListDAO = new PurchaseListDAO(conn);
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
        boolean printResult = itemDAO.printItemDetail(itemId); // 출력
        if (printResult) // itemId로 상세조회를 성공하면
            addCart();// 장바구니 담기 메뉴 -> 실제 동작 구현 필요 중요!!
        else    // 상세조회 실패하면
            return false;
    return true;
    }

    public void addCart(){ // 장바구니 담기
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

    public boolean UserPurchaseList (String loggedInUserID) {  // 주문배송조회

        boolean result = purchaseListDAO.printUsersPurchaseList(loggedInUserID); // boards 테이블에서 게시물 정보를 가져와서 출력하기
        if(result) {
            //userView
            String menuNo;
            boolean stat = true;
            int selectedOrderID;
            do {
                System.out.println();
                System.out.println("메뉴 : [1.주문 수정] [2.주문 취소] [9.뒤로 가기]");
                System.out.print("메뉴 선택 :");
                menuNo = scanner.nextLine();

                switch (menuNo) {
                    case "1": // 주문 수정
                        System.out.print("수정할 주문의 Order ID를 입력하세요: ");
                        selectedOrderID = Integer.parseInt(scanner.nextLine());
                        stat = purchaseListDAO.modifyPurchsaseList(selectedOrderID, loggedInUserID); // 주소 수정 성공하면 true, 아니면 false
                        return true; // 주문 수정 성공하면 바로 true반환하여 userLoginPassMenu로 돌아가기

                    case "2": // 주문 취소
                        System.out.print("수정할 주문의 Order ID를 입력하세요: ");
                        selectedOrderID = Integer.parseInt(scanner.nextLine());
                        stat = purchaseListDAO.cancelPurchase(selectedOrderID, loggedInUserID); // 주소 수정 성공하면 true, 아니면 false
                        return true; // 주문 취소 성공하면 바로 true반환하여 userLoginPassMenu로 돌아가기

                    case "9":
                        return true;

                    default:
                        System.out.println("유효하지 않은 메뉴입니다.");
                }

            } while (stat); //
            return true;
        }else {
            return false;
        }
    }
}
