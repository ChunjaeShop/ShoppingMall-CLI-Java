package user;

import java.sql.*;
import java.util.Scanner;

public class CartListDAO { // purchase_list와 item_order가 같은 역할인 테이블로 판단됨 수정 예정
    Scanner scanner = new Scanner(System.in);
    private static CartListDAO itemDAO;
    private Connection conn;

    public CartListDAO(Connection conn){
        this.conn = conn;
    }
    public CartListDAO(){
    }
    public void setConnection(Connection con) {
        this.conn = con;
    }

    public void printCartList(String loggedInUserId){
        // cartlist 테이블에서 가져와서 출력해줌
        try {
            String sql =
                    "SELECT cart_id, item_name, price FROM cartlist a, member m " +
                    "WHERE a.user_id=m.user_id and a.user_id=?";

            // SELECT cart_id, item_name, price From cartlist;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loggedInUserId);
            ResultSet rs = pstmt.executeQuery();
            if(!rs.next()) {
                System.out.println("* 장바구니에 담긴 상품이 없습니다.");
                return;
            }
            while (rs.next()) {
                CartlistDTO cartlistDTO = new CartlistDTO();

                cartlistDTO.setCartId(rs.getInt("cart_id"));
                cartlistDTO.setItemName(rs.getString("item_name"));
                cartlistDTO.setPrice(rs.getInt("price"));

                System.out.printf("%-10s| %-10s\t| %-10s\n",
                        cartlistDTO.getCartId(),
                        cartlistDTO.getItemName(),
                        cartlistDTO.getPrice());
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    //2-5-2. 장바구니에서 삭제
    public boolean deleteFromCartlist () {

        int cartId = -1;
        while (cartId == -1) {

            System.out.println();
            System.out.print("삭제할 상품번호 : ");
            try {
                cartId = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }
        System.out.println("선택한 상품을 삭제하시겠습니까?");
        System.out.println("1.확인 | 9.뒤로가기");
        System.out.print("메뉴 선택 >");
        String menuNo = scanner.nextLine();

        switch (menuNo) {
            //1. 확인 클릭 시
            case "1":

                try {
                    String sql = "DELETE FROM cartlist " +
                                 "WHERE cart_id= ? ";

                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, cartId);
                    ResultSet rs = pstmt.executeQuery();
                    rs.close();
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false; // SQL실패하면 flase반환
                }

                System.out.println("---------------------------------------------");
                System.out.println("상품이 삭제되었습니다");
                System.out.println("---------------------------------------------");
                return true; // 삭제 성공하면 true반환

            case "9":
                return true; // 아무 동작없이 복귀하나 장바구니 cartList()를 실행하기 위해 true반환
            default:
                System.out.println("유효하지 않은 메뉴입니다.");
        }
        return true; // SQL문이나 입력 실패하더라도 cartList()로 돌아가기 위해 true반환
    }

    // 장바구니에 담기
    public boolean insertIntoCartList(String user_id, int item_id) {
        try {
            String sql = "INSERT INTO cartlist(user_id, item_name, price, phone) " +
                         "VALUES (?, (select item_name from item where item_id = ?), " +
                         "(select price from item where item_id = ?)," +
                         "(select phone from member where user_id = ?))";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user_id);
            pstmt.setInt(2, item_id);
            pstmt.setInt(3, item_id);
            pstmt.setString(4, user_id);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("장바구니 담기에 실패했습니다. 다시 시도해주세요.");
            return true;
        }
    }
}