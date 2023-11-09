import Util.ConsoleTextControl;
import Util.ScannerUtil;
import item.*;
import user.MemberDTO;
import user.Purchase_listDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminView {
    Scanner scanner = new Scanner(System.in);
    static ScannerUtil cs = new ScannerUtil();
    private Connection conn;
    private PurchaseListService purchaseListService;
    private PurchaseListDAO purchaseListDAO;
    private ItemDAO itemDAO;


    public AdminView(Connection conn) {
        this.conn = conn;
        purchaseListService = new PurchaseListService(conn);
        purchaseListDAO = new PurchaseListDAO(conn);
        itemDAO = new ItemDAO(conn);
    }

    public void adminLoginPassMenu() { // 3. 관리자메뉴(관리자 로그인 성공화면)
        boolean stat = true;
        do {
            ConsoleTextControl.printColorln("┌------------------------------------------------------┐", "green");
            ConsoleTextControl.printColorln("│\t\t\t\t [천재쇼핑몰 Main Menu]\t\t\t\t   │", "green");
            ConsoleTextControl.printColorln("└------------------------------------------------------┘", "green");

            System.out.println("1.상품전체보기\t\t2.주문내역조회\t\t0.종료");
            System.out.println("-------------------------------------------------------");

            switch (cs.scanMenu()) {
                case "1":
                    stat = listSubmenu(); // 3-1 상품전체보기
                    break;
                case "2":
                    stat = purchaseListMenuAdmin(); // 3-2 주문내역조회
                    break;
                case "0":
                    stat = false;
                    break;
                default:
                    System.out.println("유효하지 않은 메뉴입니다.");

            }
        }while(stat);
    }

    public boolean listSubmenu() { // 3-1 submenu
        String menuNo;
        boolean stat = true;
        do {
            itemDAO.allItemListAdmin();
            System.out.println("-------------------------------------------------------");
            ConsoleTextControl.printColorln("1.상품등록 | 2.상품정보수정 | 3.상품삭제 | 9.뒤로가기","purple");
            switch (cs.scanMenu()) {
                case "1":
                    stat = enrollItem(); // 3-1-1
                    break;
                case "2":
                    stat = editItemMenu(); // 3-1-2
                    break;
                case "3":
                    stat = removeItem();   // 3-1-3
                    break;
                case "9":
                    stat = false;
            }
        } while (stat);
        return true;
    }

    public boolean enrollItem(){ // 3-1-1 상품등록
        Item item = new Item();
        System.out.println("-----------------------[ 상품 등록 ]----------------------");
        System.out.print("상품명: ");
        item.setItemName(scanner.nextLine());
        System.out.print("카테고리: ");
        item.setCategoryId(scanner.nextLine());
        System.out.print("가격: ");
        item.setPrice(scanner.nextInt());
        System.out.print("재고: ");
        item.setRemain(scanner.nextInt());
        scanner.nextLine();
        System.out.print("상품설명: ");
        item.setContent(scanner.nextLine());

        System.out.println("-------------------------------------------------------");
        ConsoleTextControl.printColorln("1.상품등록완료 | 9.뒤로가기","purple");
        ConsoleTextControl.printColor("메뉴 선택> ","purple");
        String subMenuNo = scanner.nextLine();
        if (subMenuNo.equals("1")) {
            try {
                ItemDAO itemDAO = new ItemDAO();
                itemDAO.setConnection(conn);
                if(itemDAO.insertItem(item))
                    return true;
                else System.out.println("상품등록실패");
            }catch (Exception e){

            }
        }

        return false;
    }

    public boolean editItemMenu() { // 3-1-2 상품정보수정 메뉴 // 작업중
        ItemDAO itemDAO = new ItemDAO(conn);
        System.out.println("-------------------------------------------------------");
        System.out.print("수정할 상품 ID : ");
        int itemId = scanner.nextInt();
        scanner.nextLine();
        ConsoleTextControl.printColorln("1.재고수정 | 2.정보전체수정 | 9.뒤로가기","purple");
        ConsoleTextControl.printColor("메뉴 선택> ","purple");
        String subMenuNo = scanner.nextLine();
        if (subMenuNo.equals("1")) {    // 3-1-2-1 재고수정
            System.out.print("["+itemDAO.getItemNameUsingItemId(itemId)+"] 수정할 재고 : ");
            int newRemain = scanner.nextInt();
            try {
                if(itemDAO.updateItemRemain(itemId, newRemain))
                    return true;
                else
                    System.out.println("상품 등록을 실패했습니다. 다시 시도해주세요.");
            }catch (Exception e){

            }
        } else if (subMenuNo.equals("2")) {    // 3-1-2-2 정보전체수정
            editItemAll(itemId);
            return true;

        } else if (subMenuNo.equals("9")) {    // 3-1-2-9 뒤로가기
            return false;

        }

        return false;
    }

    public void editItemAll(int itemId){ // 3-1-2-2 상품정보수정(정보전체수정)
        Item item = new Item();
        item.setItemId(itemId);
        System.out.println("-----------------------[ 상품 정보 수정 ]----------------------");
        System.out.print("상품명: ");
        item.setItemName(scanner.nextLine());
        System.out.print("가격: ");
        item.setPrice(scanner.nextInt());
        System.out.print("재고: ");
        item.setRemain(scanner.nextInt()); scanner.nextLine();
        System.out.print("상품설명: ");
        item.setContent(scanner.nextLine());

        System.out.println("-------------------------------------------------------");
        System.out.println("1.수정 | 9.뒤로가기");
        System.out.print("메뉴 선택 >");
        String subMenuNo = scanner.nextLine();
        if (subMenuNo.equals("1")) {
            try {
                ItemDAO itemDAO = new ItemDAO();
                itemDAO.setConnection(conn);
                if(itemDAO.updateItemInfo(item)) {
                    System.out.println("상품이 정상적으로 수정되었습니다. 상품 리스트를 확인해주세요.");
                }
                else
                    System.out.println("상품 수정을 실패했습니다. 다시 시도해주세요.");
            }catch (Exception e){

            }
        }
    }

    public boolean removeItem(){ // 3-1-2-3 상품삭제
        System.out.println("-------------------------------------------------------");
        System.out.print("삭제할 상품 ID : ");
        int itemId = scanner.nextInt();
        scanner.nextLine();
        ItemDAO itemDAO = new ItemDAO();
        itemDAO.setConnection(conn);
        String itemName = itemDAO.getItemNameUsingItemId(itemId);
        if (itemName != null) {
            System.out.println("[" + itemName + "]을 삭제하시겠습니까?  1.확인 | 9.뒤로가기");
            System.out.print("메뉴 선택 >");
            String deleteMenuNo = scanner.nextLine();
            if (deleteMenuNo.equals("1")) {
                try {
                    if (itemDAO.deleteItemInfo(itemId)) {
                        return true;
                    } else
                        System.out.println("상품삭제실패AdminMain");
                } catch (Exception e) {

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

    public boolean purchaseListMenuAdmin() { // 3-2-1
        String menuNo;
        boolean stat = true;
        do {
            purchaseListDAO.printAllPurchaseList(); // 주문 리스트 전체 출력
            System.out.println("-------------------------------------------------------");
            ConsoleTextControl.printColorln("1.주문확인 | 2.주문삭제 | 9.뒤로가기","purple");
            ConsoleTextControl.printColor("메뉴 선택> ","purple");
            menuNo = scanner.nextLine();

            switch (menuNo) {
                case "1":
                    stat = purchaseListDAO.printAllPurchaseList(); // 3-1-1
                    break;
                case "2":
                    stat = purchaseListService.removePurchaseList();   // 3-1-3
                    break;
                case "9":
                    stat = false;
            }
        } while (stat);
        return true;
    }



}
