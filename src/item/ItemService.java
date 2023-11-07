package item;

import Util.ScannerUtil;
import user.*;


import java.sql.Connection;
import java.util.Scanner;

// 유저 로그인, 회원가입, 유저가 볼 수 있는 메뉴의 기능들
public class ItemService {
    static ScannerUtil scannerUtil = new ScannerUtil();

    Scanner scanner = new Scanner(System.in); // 나중에 지우기
    PurchaseListService purchaseListService;
    String loggedInUserID = null;
    MemberDAO memberDAO;
    ItemDAO itemDAO;
    PurchaseListDAO purchaseListDAO;
    CartListDAO cartListDAO;

    public ItemService(Connection conn) {
        memberDAO = new MemberDAO(conn);
        itemDAO = new ItemDAO(conn);
        purchaseListDAO = new PurchaseListDAO(conn);
        cartListDAO = new CartListDAO(conn);
        purchaseListService = new PurchaseListService(conn);
    }

    public boolean allItemList() {

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

    public boolean detailItemSearch() {
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
            addCart(itemId);// 장바구니 담기 메뉴 보이기
        else    // 상세조회 실패하면
            return false;
    return true;
    }

    public void addCart(int itemId){ // 장바구니 담기
        boolean stat = true;
        do {
            System.out.println("--------------------------------------------------------------------------------------------------");
            System.out.println("메뉴 : [1.장바구니담기] [9.뒤로가기]");
            // -----아래로는 컨트롤러 옮기기
            switch (scannerUtil.scanMenu()) {
                case "1":
                    stat = cartListDAO.insertIntoCartList(loggedInUserID, itemId); // item_name과 price를 전달
                    System.out.println("상품이 장바구니에 담겼습니다");
                    break;
                case "9":
                    stat = true;
                    break;
                default:
                    System.out.println("유효하지 않은 메뉴입니다. 다시 선택해주세요.");
            }
        }while(!stat); // [1.장바구니담기]가 성공하면 true를 반환, 반복문을 탈출해야 함으로 (!stat)으로 조건식 작성
    }

    // 4. TOP10 확인
    public boolean itemRanking() { // TOP10 출력이 끝나면 1.상세조회 메소드 실행 후 동작 또는 2.바로 loginPassMenu로 돌아가기
        System.out.println();
        System.out.println("-----------------------------［상품순위］----------------------------------");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-20s%-20s%-15s%-20s\n", "순위", "상품이름", "누적판매량", "가격");
        itemDAO.printItemRanking();

        String menuNo;

        do {
            System.out.println("메뉴: 1. 상품상세조회 9. 뒤로가기");
            System.out.print("메뉴 선택 :");
            menuNo = scanner.nextLine();

            switch (menuNo) {
                case "1":
                    //장바구니 넣는 메서드
                    System.out.println("상품상세조회로 이동합니다.");
                    detailItemSearch();
                    return true;

                case "9":
                    return true;

                default:
                    System.out.println("유효하지 않은 메뉴입니다.");
            }
        }while(!menuNo.equals("1")&&!menuNo.equals("9"));
        return false;
    }

    public boolean cartList(){
        boolean stat = true;
        do {
            // 장바구니 -- no, 상품명, 가격 출력
            System.out.println();
            System.out.println("［장바구니］");
            System.out.println("-------------------------------------------------------------------------");
            System.out.printf("%-20s%-20s%-20s\n", "no", "상품명", "가격");
            System.out.println("-------------------------------------------------------------------------");
            cartListDAO.printCartList(); // cartlist 테이블에서 가져와서 장바구니 전체 리스트 출력해줌


            System.out.println("--------------------------------------------------------------------------------------------------");
            System.out.println("메뉴 : [1.전체상품구매(구매결정)] [2.장바구니에서 삭제] [9.뒤로가기]");
            System.out.print("메뉴 선택 :");
            String menuNo = scanner.nextLine();

            switch (menuNo) {
                case "1":   //전체상품구매(구매결정)
                    System.out.println("**구매결정**");
                    stat = purchaseListService.purchaseBefore(); // 구매 결정하기 전에 장바구니 금액합계 보여주고 결제시키는 것까지 들어있음
                    break;
                case "2":
                    stat = cartListDAO.deleteFromCartlist();
                    break;
                case "9":
                    return false; // 반복문 탈출하여 userLoginPassMenu로 복귀

            }
        }while(stat); // menuNo로 선택된 메뉴 실행 후 해당 내용들이 성공하면 true반환함. 9를 입력하기 전까지 장바구니는 계속 반복해서 출력해줄것으로 조건을 (stat)으로 지정

        return false;
    }

}
