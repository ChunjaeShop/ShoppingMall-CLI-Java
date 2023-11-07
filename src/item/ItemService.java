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
    PurchaseListDAO purchaseListDAO;
    CartListDAO cartListDAO;

    public ItemService(Connection conn) {
        memberDAO = new MemberDAO(conn);
        itemDAO = new ItemDAO(conn);
        purchaseListDAO = new PurchaseListDAO(conn);
        cartListDAO = new CartListDAO(conn);
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
        // 장바구니 -- no, 상품명, 가격 출력
        System.out.println();
        System.out.println("［장바구니］");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-20s%-20s%-20s\n", "no", "상품명","가격");
        System.out.println("-------------------------------------------------------------------------");
        cartListDAO.printCartList(); // cartlist 테이블에서 가져와서 출력해줌


        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.println("메뉴 : [1.전체상품구매(구매결정)] [2.장바구니에서 삭제] [9.뒤로가기]");
        System.out.print("메뉴 선택 :");
        String menuNo = scanner.nextLine();

        switch(menuNo){
            case "1" :   //전체상품구매(구매결정)
                System.out.println("**구매결정**");
                //Purchase_before();->지혜new
                break;
            case "2" :
                //Cartlist_delete();->지혜new
                break;
            case "9" :
                //LoginPassMenu();->지혜new
                break;

        }

        return false;
    }

}
