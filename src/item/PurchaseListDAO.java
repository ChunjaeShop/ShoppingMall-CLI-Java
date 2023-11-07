package item;

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
        System.out.printf("%-10s%-20s%-10s%-20s%-10s\n", "Order ID", "Item Name", "Price", "Order Date", "Status");
        System.out.println("------------------------------------------------------------------");
        try {
            String sql = "SELECT item_order.order_id, item.item_name, item.price, " +
                    "item_order.order_date, item_order.status " +
                    "FROM item_order " +
                    "JOIN item ON item_order.item_id = item.item_id " +
                    "WHERE item_order.user_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loggedInUserID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int orderID = rs.getInt("order_id");
                String itemName = rs.getString("item_name");
                int price = rs.getInt("price");
                Timestamp orderDate = rs.getTimestamp("order_date");
                String status = rs.getString("status");
                System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", orderID, itemName, price, orderDate, status);
            }
            rs.close();
            pstmt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean modifyPurchsaseList(int orderID, String loggedInUserID) { // SQL문 purchase_list로 수정하기
        // 수정하기 위해서 구매 이력 하나 조회하고 배송지 수정 가능하도록 안내
        try {
            // 주문 내역 조회
            String sql = "SELECT item_order.order_id, item.item_name, item_order.status, item_order.address " +
                    "FROM item_order " +
                    "JOIN item ON item_order.item_id = item.item_id " +
                    "WHERE item_order.user_id = ? AND item_order.order_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loggedInUserID);
            pstmt.setInt(2, orderID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String itemName = rs.getString("item_name");
                String address = rs.getString("address");

                System.out.println();
                System.out.println("［수정하려는 주문 정보］");
                System.out.println("------------------------------------------------------------------");
                System.out.println("Order ID: " + orderID);
                System.out.println("Item Name: " + itemName);
                System.out.println("Address: " + address);
                System.out.println("------------------------------------------------------------------");

                String menuNo;

                do {
                    System.out.println();
                    System.out.println("메뉴 : [1.배송지 수정] [9.뒤로 가기-구현확인] [0.처음으로]");
                    System.out.print("메뉴 선택 :");
                    menuNo = scanner.nextLine();

                    switch (menuNo) {

                        case "1":
                            System.out.print("새로운 배송지 입력:");
                            String newAddress = scanner.nextLine();
                            updatePurchaseListAddress(orderID, newAddress);
                            return true;
                        case "0":
                            return false;

                        default:
                            System.out.println("유효하지 않은 메뉴입니다.");
                    }
                } while (!menuNo.equals("2")&&!menuNo.equals("3")&&!menuNo.equals("9"));

            } else {
                System.out.println("해당 주문이 없습니다.");
                return false;
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void updatePurchaseListAddress(int orderID, String newAddress) {
        try {
            String sql = "UPDATE item_order SET address = ? WHERE order_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newAddress);
            pstmt.setInt(2, orderID);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("배송지가 업데이트되었습니다.");
            } else {
                System.out.println("배송지 업데이트 실패");
            }

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean cancelPurchase(int orderID, String loggedInUserID) {
        System.out.println("[주문 취소]");
        System.out.println("------------------------------------------");

        try {
            String itemName = getItemNameByOrderID(orderID,loggedInUserID); // 주문 ID를 사용하여 상품명 가져오기

            if (itemName != null) {
                System.out.println("상품명: " + itemName);
                System.out.println("주문 ID: " + orderID);
                System.out.println("[" + orderID + "] 주문을 취소하시겠습니까? 1.확인 9.뒤로가기");
                System.out.print("메뉴 선택: ");

                String menuNo = scanner.nextLine();
                switch (menuNo) {
                    case "1":
                        try {
                            String sql = "DELETE FROM item_order WHERE order_id = ? AND user_id = ?";
                            PreparedStatement pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1, orderID);
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
                System.out.println("주문 정보를 찾을 수 없습니다. 주문 ID를 확인해주세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public String getItemNameByOrderID(int orderID, String loggedInUserID) {
        String itemName = null;

        try {
        String sql = "SELECT item.item_name " +
                "FROM item_order " +
                "JOIN item ON item_order.item_id = item.item_id " +
                "WHERE item_order.order_id = ? AND item_order.user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, orderID);
        pstmt.setString(2, loggedInUserID);
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

}