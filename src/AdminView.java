import item.Item;
import item.ItemDAO;
import item.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminView {
    Scanner scanner = new Scanner(System.in);
    private Connection conn;


    public AdminView(Connection conn) {
        this.conn = conn;

    }

    public void adminLoginPassMenu() { // 3. 관리자메뉴(관리자 로그인 성공화면)
        String menuNo;
        boolean stat = true;

        do {
            System.out.println("-------------------------------------------------------");
            System.out.println("* 관리자님 환영합니다! **");
            System.out.println("[1.상품전체보기]  [2.주문내역조회] ");
            System.out.println("-------------------------------------------------------");
            System.out.print("메뉴 선택 :");
            menuNo = scanner.nextLine();

            switch (menuNo) {
                case "1":
                    stat = listSubmenu(); // 3-1 상품전체보기
                    break;
                case "2":
                    stat = orderListMenu(); // 3-2 주문내역조회(작업중)
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
        }while(stat);
        System.out.println("mainMenu종료");
    }


    public void allItemList() { // 3-1. 상품전체보기 화면
        // 타이틀 및 컬럼명 출력
        System.out.println();
        System.out.println("［상품 전체 보기］");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-10s|%-10s\t|%-10s\t|%-10s\t|%-10s\t|%-10s\t|%-20s\n", "category_id", "item_id", "item_name", "purchase_cnt", "remain", "price", "item_contents");
        System.out.println("-------------------------------------------------------------------------");

        // boards 테이블에서 게시물 정보를 가져와서 출력하기
        try {
            String sql =
                    "SELECT category_id, item_id, item_name, purchase_cnt, remain, price, item_contents " +
                     "FROM  item ";
            // SELECT bno, btitle, bcontent, bwriter, bdate FROM boards ORDER BY bno DESC
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ItemDTO itemdao = new ItemDTO();
                itemdao.setCategoryId(rs.getString("category_id"));
                itemdao.setItemId(rs.getInt("item_id"));
                itemdao.setItemName(rs.getString("item_name"));
                itemdao.setPurchaseCnt(rs.getInt("purchase_cnt"));
                itemdao.setRemain(rs.getInt("remain"));
                itemdao.setPrice(rs.getInt("price"));
                itemdao.setContent(rs.getString("item_contents"));
                System.out.printf("%-10s|%-10s\t|%-10s\t|%-10s\t|%-10s\t|%-10s\t|%-20s\n",
                        itemdao.getCategoryId(),
                        itemdao.getItemId(),
                        itemdao.getItemName(),
                        itemdao.getPurchaseCnt(),
                        itemdao.getRemain(),
                        itemdao.getPrice(),
                        itemdao.getContent());
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean listSubmenu() { // 3-1 submenu
        String menuNo;
        boolean stat = true;
        do {
            allItemList();
            System.out.println("-------------------------------------------------------");
            System.out.println("1.상품등록 | 2.상품정보수정 | 3.상품삭제 | 9.뒤로가기");
            System.out.println("-------------------------------------------------------");
            System.out.print("메뉴 선택 >");
            menuNo = scanner.nextLine();

            switch (menuNo) {
                case "1":
                    stat = enrollItem(); // 3-1-1
                    break;
                case "2":
                    stat = editItemMenu(); // 3-1-2
                    break;
                case "3":
                    stat = removeItem();   // 3-1-3
                    System.out.println("3번 확인"); // test code
                    break;
                case "9":

            }
        } while (stat);
        System.out.println("listSubmenu종료"); // test code
        return false;
    }

    public boolean enrollItem(){ // 3-1-1 상품등록
        Item item = new Item();
        System.out.println("-----------------------[ 상품 등록 ]----------------------");
        System.out.print("상품명: ");
        item.setItemName(scanner.nextLine());
        System.out.print("카테고리: ");
        item.setCategoryId(scanner.nextLine());
        System.out.print("사이즈: ");
        item.setSize(scanner.nextLine());
        System.out.print("가격: ");
        item.setPrice(scanner.nextInt());
        System.out.print("재고: ");
        item.setRemain(scanner.nextInt());
        scanner.nextLine();

        System.out.println("-------------------------------------------------------");
        System.out.println("1.상품등록완료 | 9.뒤로가기");
        System.out.print("메뉴 선택 >");
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
        System.out.println("-------------------------------------------------------");
        System.out.print("수정할 상품 ID : ");
        int itemId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("1.재고수정 | 2.정보전체수정 | 9.뒤로가기");
        System.out.print("메뉴 선택: ");
        String subMenuNo = scanner.nextLine();
        if (subMenuNo.equals("1")) {    // 3-1-2-1 재고수정
            System.out.print("["+itemId+"] 수정할 재고 : ");
            int newRemain = scanner.nextInt();
            try {
                ItemDAO itemDAO = new ItemDAO();
                itemDAO.setConnection(conn);
                if(itemDAO.updateItemRemain(itemId, newRemain))
                    return true;
                else
                    System.out.println("상품등록실패");
            }catch (Exception e){

            }
        } else if (subMenuNo.equals("2")) {    // 3-1-2-2 정보전체수정
            editItemAll(itemId);
            return true;

        } else if (subMenuNo.equals("9")) {    // 3-1-2-9 뒤로가기
            return true;

        }

        return false;
    }

    public void editItemAll(int itemId){ // 3-1-2-2 상품정보수정(정보전체수정)
        Item item = new Item();
        item.setItemId(itemId);
        System.out.println("-----------------------[ 상품 정보 수정 ]----------------------");
        System.out.print("상품명: ");
        item.setItemName(scanner.nextLine());
        System.out.print("사이즈: ");
        item.setSize(scanner.nextLine());
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
                    System.out.println("상품전체수정성공AdminMain");
                }
                else System.out.println("상품전체수정실패AdminMain");
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
        System.out.println("["+itemName+"]을 삭제하시겠습니까?  1.확인 | 9.뒤로가기");
        System.out.print("메뉴 선택 >");
        String deleteMenuNo = scanner.nextLine();
        if (deleteMenuNo.equals("1")) {
            try {
                if(itemDAO.deleteItemInfo(itemId)) {
                    allItemList();
                    return true;
                }
                else
                    System.out.println("상품삭제실패AdminMain");
            }catch (Exception e){

            }
        } else if (deleteMenuNo.equals("9")) {
            System.out.println("뒤로가기 입력확인");
            return true;
        }
        return false;
    }

    public boolean orderListMenu() { // 3-2-1
        // 타이틀 및 컬럼명 출력
        System.out.println();
        System.out.println("［상품 전체 보기］");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-10s|%-10s\t|%-10s\t|%-10s\t|%-10s\t|%-10s\t|%-10s\t|%-20s\n", "category_id", "item_id", "item_name", "size", "purchase_cnt", "remain", "price", "item_contents");
        System.out.println("-------------------------------------------------------------------------");

        // boards 테이블에서 게시물 정보를 가져와서 출력하기
        try {
            String sql =
                    "SELECT category_id, item_id, item_name, size, purchase_cnt, remain, price, item_contents " +
                    "FROM  item ";
            // SELECT bno, btitle, bcontent, bwriter, bdate FROM boards ORDER BY bno DESC
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ItemDTO itemdao = new ItemDTO();
                itemdao.setCategoryId(rs.getString("category_id"));
                itemdao.setItemId(rs.getInt("item_id"));
                itemdao.setItemName(rs.getString("item_name"));
                itemdao.setSize(rs.getString("size"));
                itemdao.setPurchaseCnt(rs.getInt("purchase_cnt"));
                itemdao.setRemain(rs.getInt("remain"));
                itemdao.setPrice(rs.getInt("price"));
                itemdao.setContent(rs.getString("item_contents"));
                System.out.printf("%-10s|%-10s\t|%-10s\t|%-10s\t|%-10s\t|%-10s\t|%-10s\t|%-20s\n",
                        itemdao.getCategoryId(),
                        itemdao.getItemId(),
                        itemdao.getItemName(),
                        itemdao.getSize(),
                        itemdao.getPurchaseCnt(),
                        itemdao.getRemain(),
                        itemdao.getPrice(),
                        itemdao.getContent());
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return false;
    }

}
