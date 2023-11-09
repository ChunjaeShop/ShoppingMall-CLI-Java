package item;

import java.sql.*;

public class ItemDAO {
    private static ItemDAO itemDAO;
    private Connection conn;

    public ItemDAO(Connection conn){
        this.conn = conn;
    }
    public ItemDAO(){
    }
    public void setConnection(Connection con) {
        this.conn = con;
    }

    public boolean printAllItemList(){ // item 테이블 전체 조회 SQL 및 화면 출력
        try {
            String sql =
                    "SELECT category_id, item_id, item_name, price " +
                    "FROM  item";
            // SELECT bno, btitle, bcontent, bwriter, bdate FROM boards ORDER BY bno DESC
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setCategoryId(rs.getString("category_id"));
                itemDTO.setItemId(rs.getInt("item_id"));
                itemDTO.setItemName(rs.getString("item_name"));
                itemDTO.setPrice(rs.getInt("price"));
                System.out.printf("%-10s| %-10s\t| %-10s\t| %-10s\n",
                        itemDTO.getCategoryId(),
                        itemDTO.getItemId(),
                        itemDTO.getItemName(),
                        itemDTO.getPrice()); // 출력하는 메서드 분리 예정

            }
            rs.close();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }
    public boolean printItemDetail(int itemId){
        try {
            String sql = "SELECT item_name, price, remain, purchase_cnt, item_contents " +
                         "FROM item " +
                         "WHERE item_id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ItemDTO itemdao = new ItemDTO();
                itemdao.setItemName(rs.getString("item_name"));
                itemdao.setPrice(rs.getInt("price"));
                itemdao.setRemain(rs.getInt("remain"));
                itemdao.setPurchaseCnt(rs.getInt("purchase_cnt"));
                itemdao.setContent(rs.getString("item_contents"));
                System.out.println("------------------------------------------------------------------------------------------------------------------");
                System.out.printf("%-20s%-20s%-20s%-20s%-18s\n", "상품명", "가격", "재고량", "누적판매량", "상품설명");
                System.out.println("------------------------------------------------------------------------------------------------------------------");
                System.out.printf("%-20s%-20s%-20s%-20s%-20s\n",
                        itemdao.getItemName(),
                        itemdao.getPrice(),
                        itemdao.getRemain(),
                        itemdao.getPurchaseCnt(),
                        itemdao.getContent());
            } else {
                System.out.println("상품 ID를 다시 확인해주세요. Main Menu로 돌아갑니다.");
                System.out.println();
                return false;
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean insertItem(Item item) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql =
                    "INSERT INTO item (category_id, item_name, purchase_cnt, remain, price, item_contents) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item.getCategoryId());
            pstmt.setString(2, item.getItemName());
            pstmt.setInt(3, 0);
            pstmt.setInt(4, item.getRemain());
            pstmt.setInt(5, item.getPrice());
            pstmt.setString(6, item.getContent());
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("----------------상품등록 완료------------------");

            return true;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateItemRemain(int itemId, int newRemain){
        try {
            String sql =
                    "UPDATE item set remain = COALESCE(?, remain) " +
                    "WHERE item_id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newRemain);
            pstmt.setInt(2, itemId);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("상품 재고가 정상적으로 수정되었습니다. 상품 리스트를 확인해주세요.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("상품 재고 수정을 실패했습니다. 다시 시도해주세요.");
        return false;
    }

    public boolean updateItemInfo(Item item){
        try {

            String sql =
                    "UPDATE item SET item_name = ?, " +
                            "remain = ?, " +
                            "price = ?, item_contents = ? " +
                            "WHERE item_id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, item.getItemName());
            pstmt.setInt(2, item.getRemain());
            pstmt.setInt(3, item.getPrice());
            pstmt.setString(4, item.getContent());
            pstmt.setInt(5, item.getItemId());
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("상품전체수정완료");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("상품전체수정실패");
        return false;
    }

    public String getItemNameUsingItemId(int itemId){
        String itemName = null;
        try {
            String sql =
                    "SELECT item_name FROM  item WHERE item_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                itemName = rs.getString("item_name");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
        e.printStackTrace();
        }
        return itemName;
    }

    public boolean deleteItemInfo(int itemId){
        try {
            String sql =
                    "DELETE FROM item WHERE item_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemId);
            pstmt.executeQuery();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public void printItemRanking(){
        try {
            String sql = "SELECT item_id, item_name, purchase_cnt, price " +
                         "FROM item " +
                         "ORDER BY purchase_cnt DESC " +
                         "limit 10";// 누적판매량 순으로 내림차순 정렬
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int rank = 1; // 순위 변수 초기화
            while (rs.next()) {
                ItemDTO itemdao = new ItemDTO();
                itemdao.setItemId(rs.getInt("item_id"));
                itemdao.setItemName(rs.getString("item_name"));
                itemdao.setPurchaseCnt(rs.getInt("purchase_cnt"));
                itemdao.setPrice(rs.getInt("price"));
                if(itemdao.getItemName().length()<4){
                    System.out.printf("%-5s| %-13s\t| %-10s\t| %-10s\n",
                            rank,
                            itemdao.getItemName(),
                            itemdao.getPurchaseCnt(),
                            itemdao.getPrice());
                } else {
                    System.out.printf("%-5s| %-10s\t| %-10s\t| %-10s\n",
                            rank,
                            itemdao.getItemName(),
                            itemdao.getPurchaseCnt(),
                            itemdao.getPrice());
                }
                rank++; // 다음 상품의 순위 증가
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void allItemListAdmin() { // 3-1. 상품전체보기 화면
        // 타이틀 및 컬럼명 출력
        System.out.println();
        System.out.println("［상품 전체 보기］");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-5s|%-5s\t|%-10s\t|%-6s\t|%-6s\t|%-6s\t|%-20s\n", "카테고리", "상품ID", "상품명", "누적구매수", "재고", "가격", "세부정보");
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
                System.out.printf("%-7s|%-5s\t|%-10s\t|%-10s\t|%-8s\t|%-10s\t|%-20s\n",
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
}