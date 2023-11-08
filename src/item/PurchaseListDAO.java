package item;

import Util.ConsoleTextControl;
import user.CartlistDTO;
import user.MemberDTO;
import user.Purchase_listDTO;

import java.sql.*;
import java.util.Scanner;

public class PurchaseListDAO { // purchase_list와 item_order가 같은 역할인 테이블로 판단됨 수정 예정
    Scanner scanner = new Scanner(System.in);
    private static PurchaseListDAO itemDAO;
    private Connection conn;

    public PurchaseListDAO(Connection conn){
        this.conn = conn;
    }
    public PurchaseListDAO(){
    }
    public void setConnection(Connection con) {
        this.conn = con;
    }


    public boolean printUsersPurchaseList(String loggedInUserID) { // SQL문 purchase_list로 수정하기
        System.out.println("［주문/배송 조회］");
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-10s| %-10s\t\t| %-10s\t| %-20s\n", "Order ID", "Item Name", "Price", "Order Date");
        System.out.println("------------------------------------------------------------------");
        try {
            //결제테이블의 number, 상품명, 가격, 주문날짜, member테이블의 주소, 결제테이블의 전화번호 출력
            String sql =
                    "SELECT p.purchase_no, p.item_name, p.price, p.order_date " +
                    "FROM purchase_list p, member m WHERE p.user_id=m.user_id AND p.user_id=?;";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loggedInUserID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Purchase_listDTO purchaseListDTO = new Purchase_listDTO();
                MemberDTO memberDTO = new MemberDTO();

                purchaseListDTO.setPurchaseNo(rs.getInt("purchase_no"));
                purchaseListDTO.setItemName(rs.getString("item_name"));
                purchaseListDTO.setPrice(rs.getInt("price"));
                purchaseListDTO.setPurchaseDate(rs.getString("order_date"));
                System.out.printf("%-10s| %-10s\t| %-10s\t| %-20s\n",
                        purchaseListDTO.getPurchaseNo(),
                        purchaseListDTO.getItemName(),
                        purchaseListDTO.getPrice(),
                        purchaseListDTO.getPurchaseDate());
            }
            rs.close();
            pstmt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();

        }
//
//        try {
//            String sql = "SELECT item_order.order_id, item.item_name, item.price, " +
//                    "item_order.order_date, item_order.status " +
//                    "FROM item_order " +
//                    "JOIN item ON item_order.item_id = item.item_id " +
//                    "WHERE item_order.user_id = ?";
//
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, loggedInUserID);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                int orderID = rs.getInt("order_id");
//                String itemName = rs.getString("item_name");
//                int price = rs.getInt("price");
//                Timestamp orderDate = rs.getTimestamp("order_date");
//                String status = rs.getString("status");
//                System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", orderID, itemName, price, orderDate, status);
//            }
//            rs.close();
//            pstmt.close();
//            return true;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return false;
    }

    public boolean modifyPurchsaseList(int orderID, String loggedInUserID) { // SQL문 purchase_list로 수정하기
        // 수정하기 위해서 구매 이력 하나 조회하고 배송지 수정 가능하도록 안내
        try {
            // 주문 내역 조회
            String sql = "SELECT p.purchase_no, p.item_name, m.address " +
                    "FROM purchase_list p, member m " +
                    "where m.user_id = ? AND p.purchase_no = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loggedInUserID);
            pstmt.setInt(2, orderID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String itemName = rs.getString("item_name");
                String address = rs.getString("address");

                System.out.println();
                System.out.println("［수정하려는 주문 정보］");
                System.out.println("-------------------------------------------------------");
                System.out.println("주문번호: " + orderID);
                System.out.println("상품명 : " + itemName);
                System.out.println("배송지 : " + address);
                System.out.println("-------------------------------------------------------");

                String menuNo;

                do {
                    System.out.println();
                    ConsoleTextControl.printColorln("1.배송지 수정 | 0.메인메뉴","purple");
                    ConsoleTextControl.printColor("메뉴 선택> ","purple");
                    menuNo = scanner.nextLine();
                    switch (menuNo) {

                        case "1":
                            System.out.print("새로운 배송지 입력: ");
                            String newAddress = scanner.nextLine();
                            updatePurchaseListAddress(loggedInUserID, newAddress);
                            return true;
                        case "0":
                            return true;

                        default:
                            System.out.println("유효하지 않은 메뉴입니다.");
                    }
                } while (!menuNo.equals("1") && !menuNo.equals("0"));

            } else {
                System.out.println("주문ID를 다시 확인해주세요. Main Menu로 돌아갑니다.");
                return true;
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void updatePurchaseListAddress(String userId, String newAddress) {
        try {
            String sql = "UPDATE member " +
                            "SET address = ? " +
                            "WHERE user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newAddress);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("배송지가 수정되었습니다. 메인화면으로 돌아갑니다.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("잘못된 입력입니다. 메인화면으로 돌아갑니다.");
        }
    }

    public boolean cancelPurchase(int purchaseNo, String loggedInUserID) {
        System.out.println("[주문 취소]");
        System.out.println("-------------------------------------------------------");

        try {
            String itemName = getItemNameByPurchaseId(purchaseNo,loggedInUserID); // 주문 ID를 사용하여 상품명 가져오기

            if (itemName != null) {
                System.out.println("[" + itemName + "] 상품 주문을 취소하시겠습니까? 1.확인 | 9.뒤로가기");
                System.out.print("메뉴 선택 >");

                String menuNo = scanner.nextLine();
                switch (menuNo) {
                    case "1":
                        try {
                            String sql = "DELETE FROM purchase_list WHERE purchase_no = ? AND user_id = ?";
                            PreparedStatement pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1, purchaseNo);
                            pstmt.setString(2, loggedInUserID);

                            int affectedRows = pstmt.executeUpdate();
                            pstmt.close();

                            if (affectedRows > 0) {
                                System.out.println("주문이 취소되었습니다.");
                                return true;
                            } else {
                                System.out.println("주문 취소에 실패했습니다. 주문 ID를 확인해주세요.");
                                return false;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "9":
                        return false;
                    default:
                        System.out.println("유효하지 않은 메뉴입니다.");
                        break;
                }
            } else {
                System.out.println("주문ID를 다시 확인해주세요. Main Menu로 돌아갑니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public String getItemNameByPurchaseId(int purchaseId, String loggedInUserID) {
        String itemName = null;
        try {
        String sql = "SELECT item_name FROM purchase_list WHERE purchase_no = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, purchaseId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            itemName = rs.getString("item_name");
        }

        rs.close();
        pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemName;
    }
    public void printCartPriceSum(String loggedInUserID){ // cartlist 테이블에서 가져와서 출력해줌
        try {
            // cartlist, member테이블에서 아이디가 동일한걸 찾고(조건) 장바구니에 담겨있는 물품 가격 총금액을 출력;
            String sql =
                    "SELECT SUM(price) AS `총금액` "+
                    "FROM cartlist a, member m " +
                    "WHERE a.user_id=m.user_id AND a.user_id = ?" ;
            // `총금액` 변경 시 cartlistDTO.setPrice(rs.getInt("총금액") columnLabel 도 같이 변경(필수)

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loggedInUserID);
            ResultSet rs = pstmt.executeQuery();


            while (rs.next()) {
                CartlistDTO cartlistDTO = new CartlistDTO();

                cartlistDTO.setPrice(rs.getInt("총금액"));

                System.out.printf("%s\n",
                        cartlistDTO.getPrice());
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ----insertIntoPurchaseList() 메서드 merger 메모----
    // DB purchase_list에 insert하는게 메인 코드 앞뒤로 select써서 내용 출력시킴
    // 파일 병합하면서 주문전 인적사항 확인하는 select문과 purchase_list에 insert하는 SQL문이 합쳐있는 상태-나중에 분리 필요
    public boolean insertIntoPurchaseList(String loggedInUserID){

        // 주문 전 주소, 전화번호 확인용으로 인적사항 재확인
        System.out.println();
        System.out.println("주문 전 주소와 전화번호를 확인해주세요");
        System.out.println("-------------------------------------------------------");
        System.out.printf("%-10s\t|%-10s\n", "주소", "전화번호");
        System.out.println("-------------------------------------------------------");

        // cartlist 테이블에서 가져와서 출력해줌
        try {
            // cartlist, member테이블에서 아이디가 동일한걸 찾고 장바구니에 담겨있는 물품 가격 총금액을 출력;
            String sql =
                    "SELECT DISTINCT m.address, m.phone FROM cartlist a, member m "+
                    "WHERE a.user_id=m.user_id AND a.user_id=?";
            // `총금액` 변경 시 cartlistDTO.setPrice(rs.getInt("총금액") columnLabel 도 같이 변경(필수)
            //executeQuery()호출하여 ResultSet 검색
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loggedInUserID);
            //ResultSet=데이터베이스 쿼리를 실행하여 생성된 데이터 테이블
            ResultSet rs = pstmt.executeQuery();

            //next()사용하여 레코드 반복
            //다음행이 있을경우 true 반환 없을경우 false 반환
            while (rs.next()) {
                MemberDTO memberDTO = new MemberDTO();
                //데이터베이스 열에서 값을 가져옴 getX()메서드
                memberDTO.setAddress(rs.getString("address"));
                memberDTO.setPhone(rs.getString("phone"));
                System.out.printf("%-10s\t|%-20s\n",
                        memberDTO.getAddress(),
                        memberDTO.getPhone());
            }
            rs.close();
            pstmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("-------------------------------------------------------");
        System.out.println("1.확인 | 2.주소/전화번호 변경 | 9.뒤로 가기");
        System.out.print("메뉴 선택 >");
        String menuNo = scanner.nextLine();

        switch (menuNo) {
            case "1":
                try {
                    String sql =
                            "INSERT INTO purchase_list(user_id, item_name, price, order_date, phone) " +
                            "SELECT c.user_id, c.item_name, c.price, now(), c.phone " +
                            "FROM cartlist c, member m WHERE c.user_id = m.user_id AND c.user_id = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, loggedInUserID);
                    pstmt.executeUpdate();
                    pstmt.close(); // cartlist에서 삭제

                    String deleteSql = "DELETE FROM cartlist WHERE user_id = ?";
                    pstmt = conn.prepareStatement(deleteSql);
                    pstmt.setString(1, loggedInUserID);
                    pstmt.executeUpdate();
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Purchase_successful(loggedInUserID);
                return true;

            case "2":
                //2-5-1-1-2. 주소/전화번호 변경
                Alter_PurchaseInfo(loggedInUserID);
                break;
            case "9":
                break;
        }
        return true;
    }
    //2-5-1-1-1. 확인
    //주문 최종 결제 후 화면
    public void Purchase_successful(String loggedInUserID){
        System.out.println();
        System.out.println("［주문이 완료되었습니다］");
        System.out.println(" ############## ");
        System.out.println(" 주문 리스트 ");
        System.out.println(" ############## ");

        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-10s|%-10s\t|%-10s\t|%-20s\t|%-10s\t|%-20s\n", "no", "상품명", "가격", "주문날짜", "주소", "전화번호");
        System.out.println("-------------------------------------------------------------------------");

        // 주문 리스트 출력
        try {
            //결제테이블의 number, 상품명, 가격, 주문날짜, member테이블의 주소, 결제테이블의 전화번호 출력
            String sql =
                    "SELECT p.purchase_no, p.item_name, p.price, p.order_date, m.address, p.phone\n" +
                    "FROM purchase_list p, member m WHERE p.user_id=m.user_id AND p.user_id=?;";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loggedInUserID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Purchase_listDTO purchaseListDTO = new Purchase_listDTO();
                MemberDTO memberDTO = new MemberDTO();

                purchaseListDTO.setPurchaseNo(rs.getInt("purchase_no"));
                purchaseListDTO.setItemName(rs.getString("item_name"));
                purchaseListDTO.setPrice(rs.getInt("price"));
                purchaseListDTO.setPurchaseDate(rs.getString("order_date"));
                memberDTO.setAddress(rs.getString("address"));
                purchaseListDTO.setPhone(rs.getString("phone"));
                System.out.printf("%-10s|%-10s\t|%-10s\t|%-20s\t|%-10s\t|%-20s\n",
                        purchaseListDTO.getPurchaseNo(),
                        purchaseListDTO.getItemName(),
                        purchaseListDTO.getPrice(),
                        purchaseListDTO.getPurchaseDate(),
                        memberDTO.getAddress(),
                        purchaseListDTO.getPhone());
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        //결제 전체 진행 후 회원 메인 메뉴로 이동
        System.out.println("주문이 완료되었습니다. 장바구니로 돌아갑니다.");
    }

    //2-5-1-1-2. 주소/전화번호 변경
    public void Alter_PurchaseInfo(String loggedInUserID){
        MemberDTO memberDto = new MemberDTO();
        System.out.println();
        System.out.println("[주소/전화번호 변경]");
        System.out.println("-------------------------------------------------------");
        System.out.print("주소:");
        memberDto.setAddress(scanner.nextLine());
        System.out.print("전화번호 수정: ");
        memberDto.setPhone(scanner.nextLine());
        System.out.println("-------------------------------------------------------");

        try {
            String sql =
                     "UPDATE member " +
                             "SET address=? , phone=? " +
                             "WHERE user_id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberDto.getAddress());
            pstmt.setString(2, memberDto.getPhone());
            pstmt.setString(3, loggedInUserID);

            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("수정 완료 되었습니다");
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

}