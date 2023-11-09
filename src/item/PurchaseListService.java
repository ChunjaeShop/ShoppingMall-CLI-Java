package item;

import Util.ConsoleTextControl;
import Util.ScannerUtil;
import user.MemberDAO;
import user.MemberDTO;
import user.Purchase_listDTO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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



    public boolean UserPurchaseList (String loggedInUserID) {  // 주문배송조회
        boolean stat = true;
        int selectedOrderID;
        String menuNo;
        do {
            boolean result = purchaseListDAO.printUsersPurchaseList(loggedInUserID); // boards 테이블에서 게시물 정보를 가져와서 출력하기
            if(result) {
                System.out.println();
                System.out.println("1.주문 수정 | 2.주문 취소 | 9.뒤로 가기");
                System.out.print("메뉴 선택 >");
                menuNo = scanner.nextLine();

                switch (menuNo) {
                    case "1": // 주문 수정
                        System.out.print("수정할 주문의 Order ID를 입력하세요: ");
                        selectedOrderID = Integer.parseInt(scanner.nextLine());
                        stat = purchaseListDAO.modifyPurchsaseList(selectedOrderID, loggedInUserID); // 주소 수정 성공하면 true, 아니면 false
                        if (stat)
                            return true;
//                        else
//                            return false; // 주문 수정 성공하면 바로 true반환하여 userLoginPassMenu로 돌아가기
                    case "2": // 주문 취소
                        System.out.print("취소할 주문의 Order ID를 입력하세요: ");
                        selectedOrderID = Integer.parseInt(scanner.nextLine());
                        stat = purchaseListDAO.cancelPurchase(selectedOrderID, loggedInUserID); // 주소 수정 성공하면 true, 아니면 false
                        if(stat)
                            return true;
//                        else
//                            return false; // 주문 취소 성공하면 바로 true반환하여 userLoginPassMenu로 돌아가기

                    case "9":
                        return true;

                    default:
                        System.out.println("유효하지 않은 메뉴입니다.");
                }
            }else {
                return false;
            }

        } while (stat); //
        return true;
    }

    //2-5-1.구매결정(결제테이블)
    public boolean purchaseBefore(String loggedInUserID){
        System.out.println();
        System.out.println("［구매결정］");
        System.out.println("----------------------------------------------");
        System.out.printf("%-20s\n", "총금액");
        // 구매결정 테이블에 보여줄 목록 [총금액]
        System.out.println("----------------------------------------------");

        purchaseListDAO.printCartPriceSum(loggedInUserID);// cartlist 테이블에서 가져와서 총금액 출력해줌

        while(true) { // 무한반복을 잘못 사용한 것은 아닐지 확인 필요 - merger
        //부메뉴 출력
        System.out.println("----------------------------------------------");
        System.out.println("구매하시겠습니까?");
        System.out.println("1.구매(결제) | 9.뒤로가기");
        System.out.print("메뉴 선택 >");
        String menuNo = scanner.nextLine();

            switch (menuNo) {
                case "1":
                    //2-5-1-1.결제 이동
                    purchaseListDAO.insertIntoPurchaseList(loggedInUserID); // DB purchase_list에 insert하는게 메인 코드 앞뒤로 select써서 내용 출력시킴
                    return true;
                case "9":
                    //2-5. 장바구니 이동
                    return true;
                default:
                    System.out.println("유효하지 않은 메뉴입니다.");
            }
        }
    }

    public boolean removePurchaseList(){
        System.out.println("-------------------------------------------------------");
        System.out.print("삭제할 주문 ID : ");
        int purchaseNo = scanner.nextInt();
        scanner.nextLine();
        boolean isExistInfo = purchaseListDAO.getPurchaseInfoByPurchaseNo(purchaseNo);
        if (isExistInfo) {
            ConsoleTextControl.printColorln( "주문 ID[" + purchaseNo + "]을/를 삭제하시겠습니까?  1.확인 | 9.뒤로가기","purple");
            ConsoleTextControl.printColor("메뉴 선택 >","purple");
            String deleteMenuNo = scanner.nextLine();
            if (deleteMenuNo.equals("1")) {
                try {
                    if (purchaseListDAO.deletePurchaseInfo(purchaseNo)) {
                        System.out.println("주문이 정상적으로 삭제되었습니다. --로 돌아갑니다.");
                        return true;
                    } else
                        System.out.println("주문 삭제 중 오류가 발생했습니다. --로 돌아갑니다.");
                } catch (Exception e) {
                    System.out.println("주문 삭제 중 오류가 발생했습니다. --로 돌아갑니다.");
                }
            } else if (deleteMenuNo.equals("9")) {
                System.out.println("뒤로가기 입력확인");
                return true;
            }
        } else { // itemName이 없어서 null인 경우
            System.out.println("해당 상품이 없습니다. 상품ID를 다시 확인해주세요.");
            return true;
        }
        return false;
    }
}
