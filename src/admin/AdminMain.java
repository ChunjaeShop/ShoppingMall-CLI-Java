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
                    listSubmenu(); // 3-1 submenu
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


    public void AllItemList() { // 3-1. 상품전체보기 화면
        // 타이틀 및 컬럼명 출력
        System.out.println();
        System.out.println("［상품 전체 보기］");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "category_id", "item_id", "item_name", "size", "purchase_cnt", "remain", "price", "item_contents");
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
                System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n",
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
    }

    public void listSubmenu() { // 3-1 submenu
        String menuNo;
        do {
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("[1.상품등록]   [2.상품정보수정]   [3.상품삭제]   [9.뒤로가기]");
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("메뉴 선택 :");
            menuNo = scanner.nextLine();

            switch (menuNo) {
                case "1":
            }
        } while (!menuNo.equals("1") && !menuNo.equals("2") && !menuNo.equals("3"));
    }




}
