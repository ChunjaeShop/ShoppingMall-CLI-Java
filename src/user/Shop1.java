package user;
import admin.*;

import java.sql.*;
import java.util.Scanner;

public class Shop1 {
    private Scanner scanner = new Scanner(System.in);
    private Connection conn;
    private String loggedInUserID;
    MemberDAO memberDAO;


    public Shop1() {
        try {
            // JDBC Driver 등록
            Class.forName("org.mariadb.jdbc.Driver");

            // 연결하기
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://127.0.0.1:3306/team_prj",
                    "root", "12345");
            memberDAO = new MemberDAO(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

//    public void list() { => 합치면서 삭제함
//        // 타이틀 및 컬럼명 출력
//        System.out.println();
//        System.out.println("［쇼핑몰 주문  프로그램］");
//    }



//    public void startMenu() { => 메뉴 큰 틀은 MenuView에, 내부 동작은 UserService이나 AdminService에서 처리
//        Scanner scanner = new Scanner(System.in);
//        String menuNo;
//
//        do {
//            System.out.println();
//            System.out.println("-----------------------------------------------------------------------------");
//            System.out.println("1. 회원 로그인 | 2. 회원가입 | 3. 관리자 로그인");
//            System.out.print("메뉴 선택: ");
//            menuNo = scanner.nextLine();
//
//            switch (menuNo) {
//                case "1":
//                    String userID;
//                    boolean passwordMatched = false;
//
//
//                    while (!passwordMatched) {
//                        do {
//                            System.out.println("=========회원로그인========");
//                            System.out.print("아이디 입력: ");
//                            userID = scanner.nextLine();
//
//                            if (userID.isEmpty()) {
//                                System.out.println("아이디를 입력하세요.");
//                            }
//                        } while (userID.isEmpty());
//                        System.out.print("비밀번호 입력: ");
//                        String userPassword = scanner.nextLine();
//                        System.out.print("1. 로그인 9. 뒤로가기 : ");
//                        String startLogin = scanner.nextLine();
//                        if (startLogin.equals("1")) {
//
//                            int loginResult = memberDAO.loginConfirm(userID, userPassword);
//
//                            if (loginResult == 1) {
//                                System.out.println(userID + "님 환영합니다!");
//                                LoginPassMenu();
//                                break;
//                            } else if (loginResult == 0) {
//                                System.out.println("비밀번호 불일치 id랑 비밀번호를 다시 입력하세요");
//
//                            } else if (loginResult == -1) {
//                                System.out.println("아이디가 없습니다. 회원가입을 하시겠습니까?(Y/N)");
//                                String retry = scanner.nextLine();
//                                if (retry.equalsIgnoreCase("Y")) {
//                                    create();
//                                    break;
//                                } else {
//                                    startMenu();
//                                    break;
//                                }
//                            } else {
//                                System.out.println("데이터베이스 오류");
//                            }
//                        }else if(startLogin.equals("9")){
//                            startMenu();
//
//                        }
//
//                    }break;
//
//
//                case "2":
//                    create();
//                    break;
//
//                case "3":
//                    String adminID;
//                    String adminPassword;
//                    do {
//                        System.out.print("관리자 아이디 입력: ");
//                        adminID = scanner.nextLine();
//                        if (adminID.isEmpty()) {
//                            System.out.println("아이디를 입력하세요");
//                        }
//                    } while (adminID.isEmpty());
//
//                    do {
//                        System.out.print("관리자 비밀번호 입력: ");
//                        adminPassword = scanner.nextLine();
//                        if (adminPassword.isEmpty()) {
//                            System.out.println("비밀번호를 입력하세요");
//                        }
//                    } while (adminID.isEmpty());
//
//
//                    if (adminID.equals("host") && adminPassword.equals("1234")) {
//                        System.out.println("관리자로 로그인하셨습니다!");
//                    } else {
//                        System.out.println("관리자 로그인 실패");
//                        startMenu();
//                    }
//                    break;
//
//                default:
//                    System.out.println("유효한 메뉴를 선택하세요. (1 ,2 ,3 중 선택하세요)");
//
//            }
//        } while (!menuNo.equals("1") && !menuNo.equals("2") && !menuNo.equals("3"));
//
//
//        scanner.close();
//    }


//    // 회원가입 메서드 => => 메뉴 큰 틀은 MenuView에, 내부 동작은 UserService
//    public void create() {
//        // 입력 받기
//        MemberDTO memberDto = new MemberDTO();
//        System.out.println("----------------------- 회원가입 ----------------------");
//        System.out.println("［개인정보 입력］");
//        System.out.print("이름: ");
//        memberDto.setName(scanner.nextLine());
//        System.out.print("아이디: ");
//        memberDto.setUserId(scanner.nextLine());
//        System.out.print("비밀번호: ");
//        memberDto.setUserPw(scanner.nextLine());
//        System.out.print("주소: ");
//        memberDto.setAddress(scanner.nextLine());
//        System.out.print("성별: ");
//        memberDto.setGender(scanner.nextLine());
//        System.out.print("키: ");
//        memberDto.setHeight(Integer.parseInt(scanner.nextLine()));
//        System.out.print("번호: ");
//        memberDto.setPhone(scanner.nextLine());
//        System.out.print("생년월일: ");
//        memberDto.setBirth(scanner.nextLine());
//
//        System.out.println("-------------------------------------------------------------------------");
//        System.out.println("보조 메뉴: 1.회원가입 | 2.취소");
//        System.out.print("메뉴 선택: ");
//        String menuNo = scanner.nextLine();
//        if (menuNo.equals("1")) {
//            try {
//                String sql =
//                        "INSERT INTO member (name, user_id, user_pw, address, gender, height_cm, phone, birth) " +
//                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//                PreparedStatement pstmt = conn.prepareStatement(sql);
//                pstmt.setString(1, memberDto.getName());
//                pstmt.setString(2, memberDto.getUserId());
//                pstmt.setString(3, memberDto.getUserPw());
//                pstmt.setString(4, memberDto.getAddress());
//                pstmt.setString(5, memberDto.getGender());
//                pstmt.setInt(6, memberDto.getHeight());
//                pstmt.setString(7, memberDto.getPhone());
//                pstmt.setString(8, memberDto.getBirth());
//                pstmt.executeUpdate();
//                pstmt.close();
//                System.out.println("----------------회원가입 완료------------------");
//                System.out.println("----------------로그인하세요------------------");
//                startMenu();
//            } catch (Exception e) {
//                System.out.println("회원가입 중 오류가 발생했습니다. 다시 시도하시겠습니까? (Y/N)");
//                String retry = scanner.nextLine();
//                if (retry.equalsIgnoreCase("Y")) {
//                    create(); // 다시 회원가입 메소드를 호출하여 재시도
//                } else {
//                    System.out.println("회원가입 취소하셨습니다.");
//                    startMenu();
//                }
//            }
//        } else {
//            System.out.println("취소하셨습니다.");
//            startMenu();
//        }
//    }


//    public void LoginPassMenu() {
//        System.out.println();
//        String menuNo;
//
//        do {
//            System.out.println("-----------------------------------------------------------------------------------");
//            System.out.println("[1.상품전체보기] [2.상품상세조회] [3.주문/배송조회] [4.Top10상품보기]   [5.장바구니]   [9.내정보확인]");
//            System.out.println("-----------------------------------------------------------------------------------");
//            System.out.print("메뉴 선택 :");
//            menuNo = scanner.nextLine();
//
//            switch (menuNo) {
//                case "1":
//                    AllItemList();
//                    System.out.println("-----------------------------------------------------------------------------------");
//                    LoginPassMenu();
//                    break;
//                case "2":
//                    DetailItemSearch();
//                    break;
//
//                case "3":
//                    Delivery();
//                    break;
//                case "4":
//                    itemRank();
//                    break;
//
//                case "5" :
//                    Cart();
//
//                    break;
//
//                case "9":
//                    MyInfo();
//                    break;
//                default:
//                    System.out.println("유효하지 않은 메뉴입니다.");
//
//            }
//        }while(!menuNo.equals("1")  &&!menuNo.equals("2") &&!menuNo.equals("3") &&!menuNo.equals("4") &&!menuNo.equals("4")&&!menuNo.equals("9"));
//    }



//    public void AllItemList() {
//
//        // 타이틀 및 컬럼명 출력
//        System.out.println();
//        System.out.println("［상품 전체 보기］");
//        System.out.println("-------------------------------------------------------------------------");
//        System.out.printf("%-20s%-20s%-20s%-20s%n", "category_id", "item_id", "item_name", "price");
//        System.out.println("-------------------------------------------------------------------------");
//
//        // boards 테이블에서 게시물 정보를 가져와서 출력하기
//        try {
//            String sql =
//                    "SELECT category_id, item_id, item_name, price " +
//                            "FROM  item";
//            // SELECT bno, btitle, bcontent, bwriter, bdate FROM boards ORDER BY bno DESC
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                ItemDTO itemdao = new ItemDTO();
//                itemdao.setCategoryId(rs.getString("category_id"));
//                itemdao.setItemId(rs.getInt("item_id"));
//                itemdao.setItemName(rs.getString("item_name"));
//                itemdao.setPrice(rs.getInt("price"));
//                System.out.printf("%-20s%-20s%-20s%-20s%n",
//                        itemdao.getCategoryId(),
//                        itemdao.getItemId(),
//                        itemdao.getItemName(),
//                        itemdao.getPrice());
//
//            }
//            rs.close();
//            pstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//
//        }
//    }

//    public void DetailItemSearch() {
//        int itemId = -1; // 아이템 ID를 -1로 초기화
//
//        while (itemId == -1) { // 아이템 ID가 유효하지 않은 경우 반복
//            System.out.println();
//            System.out.print("조회할 상품 ID : ");
//            try {
//                itemId = Integer.parseInt(scanner.nextLine());
//            } catch (Exception e) {
//            }
//        }
//
//
//        try {
//            String sql = "SELECT item_name, price, remain, purchase_cnt, item_contents " +
//                    "FROM item " +
//                    "WHERE item_id=?";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, itemId);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                ItemDTO itemdao = new ItemDTO();
//                itemdao.setItemName(rs.getString("item_name"));
//                itemdao.setPrice(rs.getInt("price"));
//                itemdao.setRemain(rs.getInt("remain"));
//                itemdao.setPurchaseCnt(rs.getInt("purchase_cnt"));
//                itemdao.setContent(rs.getString("item_contents"));
//                System.out.println("------------------------------------------------------------------------------------------------------------------");
//                System.out.printf("%-20s%-20s%-20s%-20s%-18s\n", "상품명", "가격", "재고량", "누적판매량", "상품설명");
//                System.out.println("------------------------------------------------------------------------------------------------------------------");
//                System.out.printf("%-20s%-20s%-20s%-20s%-20s\n",
//                        itemdao.getItemName(),
//                        itemdao.getPrice(),
//                        itemdao.getRemain(),
//                        itemdao.getPurchaseCnt(),
//                        itemdao.getContent());
//            } else {
//                System.out.println("해당 상품이 없습니다.");
//                System.out.println("다시 상품을 확인해주세요");
//                System.out.println();
//                AllItemList();
//                DetailItemSearch(); // 상품이 없는 경우 메서드를 재호출하여 다시 상품 ID를 입력받음
//                return;
//            }
//
//            rs.close();
//            pstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        String menuNo;
//        System.out.println();
//
//        do {
//            System.out.println("--------------------------------------------------------------------------------------------------");
//            System.out.println("메뉴 : [1.장바구니담기] [9.뒤로가기]");
//            System.out.print("메뉴 선택 :");
//            menuNo = scanner.nextLine();
//
//            switch (menuNo) {
//                case "1":
//                    //장바구니 넣는 메서드
//                    System.out.println("상품이 장바구니에 담겼습니다");
//                    LoginPassMenu();
//                    break;
//
//
//
//                case "9":
//                    LoginPassMenu();
//                    break;
//                default:
//                    System.out.println("유효하지 않은 메뉴입니다.");
//
//            }
//        }while(!menuNo.equals("1")&&!menuNo.equals("9"));
//    }
//
//    public void Delivery() {
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
//            System.out.println();
//            System.out.println("［주문/배송 조회］");
//            System.out.println("------------------------------------------------------------------");
//            System.out.printf("%-10s%-20s%-10s%-20s%-10s\n", "Order ID", "Item Name", "Price", "Order Date", "Status");
//            System.out.println("------------------------------------------------------------------");
//
//            while (rs.next()) {
//                int orderID = rs.getInt("order_id");
//                String itemName = rs.getString("item_name");
//                int price = rs.getInt("price");
//                Timestamp orderDate = rs.getTimestamp("order_date");
//                String status = rs.getString("status");
//
//                System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", orderID, itemName, price, orderDate, status);
//            }
//            String menuNo;
//            do {
//                System.out.println();
//                System.out.println("메뉴 : [1.주문 수정] [2.주문 취소] [9.뒤로 가기]");
//                System.out.print("메뉴 선택 :");
//                menuNo = scanner.nextLine();
//
//
//                switch (menuNo) {
//                    case "1":
//                        System.out.print("수정할 주문의 Order ID를 입력하세요: ");
//                        int selectedOrderID = Integer.parseInt(scanner.nextLine());
//                        modifyOrder(selectedOrderID);
//                        break;
//
//                    case "2":
//                        // 주문 취소 화면 호출
//                        System.out.println("주문 취소 기능 호출");
//                        System.out.print("주문 ID를 입력하세요: ");
//                        int orderID = Integer.parseInt(scanner.nextLine());
//                        CancelOrderScreen(orderID);
//                        Delivery();
//                        break;
//
//                    case "9":
//                        LoginPassMenu();
//                        break;
//
//                    default:
//                        System.out.println("유효하지 않은 메뉴입니다.");
//                }
//
//            } while (!menuNo.equals("1") && !menuNo.equals("2") && !menuNo.equals("9"));
//
//
//
//            rs.close();
//            pstmt.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void modifyOrder(int orderID) {
//
//
//        try {
//            // 주문 내역 조회
//            String sql = "SELECT item_order.order_id, item.item_name, item_order.status, item_order.address " +
//                    "FROM item_order " +
//                    "JOIN item ON item_order.item_id = item.item_id " +
//                    "WHERE item_order.user_id = ? AND item_order.order_id = ?";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, loggedInUserID);
//            pstmt.setInt(2, orderID);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                String itemName = rs.getString("item_name");
//                String address = rs.getString("address");
//
//                System.out.println();
//                System.out.println("［주문 수정］");
//                System.out.println("------------------------------------------------------------------");
//                System.out.println("Order ID: " + orderID);
//                System.out.println("Item Name: " + itemName);
//                System.out.println("Address: " + address);
//                System.out.println("------------------------------------------------------------------");
//
//                String menuNo;
//
//                do {
//                    System.out.println();
//                    System.out.println("메뉴 : [2.배송지 수정] [3.통합 메뉴] [9.뒤로 가기]");
//                    System.out.print("메뉴 선택 :");
//                    menuNo = scanner.nextLine();
//
//                    switch (menuNo) {
//
//                        case "2":
//                            System.out.print("새로운 배송지 입력:");
//                            String newAddress = scanner.nextLine();
//                            updateAddress(orderID, newAddress);
//                            modifyOrder(orderID);
//                            break;
//
//
//                        case "3":
//                            System.out.println();
//                            System.out.println("통합 메뉴");
//                            LoginPassMenu();
//                            break;
//
//                        case "9":
//                            Delivery();
//                            break;
//
//                        default:
//                            System.out.println("유효하지 않은 메뉴입니다.");
//                    }
//                } while (!menuNo.equals("2")&&!menuNo.equals("3")&&!menuNo.equals("9"));
//
//            } else {
//                System.out.println("해당 주문이 없습니다.");
//                Delivery();
//            }
//
//            rs.close();
//            pstmt.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String getItemNameByOrderID(int orderID, String loggedInUserID) {
//        String itemName = null;
//
//        try {
//            String sql = "SELECT item.item_name " +
//                    "FROM item_order " +
//                    "JOIN item ON item_order.item_id = item.item_id " +
//                    "WHERE item_order.order_id = ? AND item_order.user_id = ?";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, orderID);
//            pstmt.setString(2, loggedInUserID);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                itemName = rs.getString("item_name");
//            }
//
//            rs.close();
//            pstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return itemName;
//    }
//    private void CancelOrderScreen(int orderID) {
//        System.out.println("[주문 취소]");
//        System.out.println("------------------------------------------");
//
//        try {
//            String itemName = getItemNameByOrderID(orderID,loggedInUserID); // 주문 ID를 사용하여 상품명 가져오기
//
//            if (itemName != null) {
//                System.out.println("상품명: " + itemName);
//                System.out.println("주문 ID: " + orderID);
//                System.out.println("[" + orderID + "] 주문을 취소하시겠습니까? 1.확인 9.뒤로 가기");
//                System.out.print("메뉴 선택: ");
//
//                String menuNo = scanner.nextLine();
//                switch (menuNo) {
//                    case "1":
//                        try {
//                            String sql = "DELETE FROM item_order WHERE order_id = ? AND user_id = ?";
//                            PreparedStatement pstmt = conn.prepareStatement(sql);
//                            pstmt.setInt(1, orderID);
//                            pstmt.setString(2, loggedInUserID);
//
//                            int affectedRows = pstmt.executeUpdate();
//                            pstmt.close();
//
//                            if (affectedRows > 0) {
//                                System.out.println("주문이 취소되었습니다.");
//                            } else {
//                                System.out.println("주문 취소에 실패했습니다. 주문 ID를 확인해주세요.");
//                            }
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                        break;
//                    case "9":
//                        Delivery(); // 뒤로 가기
//                        break;
//                    default:
//                        System.out.println("유효하지 않은 메뉴입니다.");
//                        break;
//                }
//            } else {
//                System.out.println("주문 정보를 찾을 수 없습니다. 주문 ID를 확인해주세요.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void updateAddress(int orderID, String newAddress) {
//        try {
//            String sql = "UPDATE item_order SET address = ? WHERE order_id = ?";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, newAddress);
//            pstmt.setInt(2, orderID);
//            int rowsUpdated = pstmt.executeUpdate();
//
//            if (rowsUpdated > 0) {
//                System.out.println("배송지가 업데이트되었습니다.");
//            } else {
//                System.out.println("배송지 업데이트 실패");
//            }
//
//            pstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//
//
//
//    // TOP10 확인
//    public void itemRank() {
//        System.out.println();
//        System.out.println("-----------------------------［상품순위］----------------------------------");
//        System.out.println("-------------------------------------------------------------------------");
//        System.out.printf("%-20s%-20s%-15s%-20s\n", "순위", "상품이름", "누적판매량", "가격");
//
//        try {
//            String sql = "SELECT item_id, item_name, purchase_cnt, price " +
//                    "FROM item " +
//                    "ORDER BY purchase_cnt DESC " +
//                    "limit 10";// 누적판매량 순으로 내림차순 정렬
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//            int rank = 1; // 순위 변수 초기화
//            while (rs.next()) {
//                ItemDTO itemdao = new ItemDTO();
//                itemdao.setItemId(rs.getInt("item_id"));
//                itemdao.setItemName(rs.getString("item_name"));
//                itemdao.setPurchaseCnt(rs.getInt("purchase_cnt"));
//                itemdao.setPrice(rs.getInt("price"));
//                System.out.printf("%-20s%-20s%-20s%-20s\n",
//                        rank,
//                        itemdao.getItemName(),
//                        itemdao.getPurchaseCnt(),
//                        itemdao.getPrice());
//                rank++; // 다음 상품의 순위 증가
//            }
//            rs.close();
//            pstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        String menuNo;
//
//        do {
//            System.out.println("메뉴: 1. 상품상세조회 9. 뒤로가기");
//            System.out.print("메뉴 선택 :");
//            menuNo = scanner.nextLine();
//
//            switch (menuNo) {
//                case "1":
//                    //장바구니 넣는 메서드
//                    System.out.println("상품상세조회로 이동합니다.");
//                    AllItemList();
//                    DetailItemSearch();
//                    break;
//
//                case "9":
//                    LoginPassMenu();
//                    break;
//
//                default:
//                    System.out.println("유효하지 않은 메뉴입니다.");
//                    itemRank();
//
//
//            }
//        }while(!menuNo.equals("1")&&!menuNo.equals("9"));
//    }
//
//
//
//    // 내정보 확인 메서드
//    public void MyInfo() {
//        if (loggedInUserID != null) {
//            System.out.println();
//            System.out.println("［내 정보 확인］");
//            System.out.println("현재 정보");
//            System.out.println("-------------------------------------------------------------------------");
//
//            try {
//                String sql =
//                        "SELECT name, user_id, user_pw, address, phone " +
//                                "FROM  member " +
//                                "WHERE user_id=?";
//
//                PreparedStatement pstmt = conn.prepareStatement(sql);
//                pstmt.setString(1, loggedInUserID); // loggedInUserID를 사용하여 쿼리에 사용자 ID 설정
//                ResultSet rs = pstmt.executeQuery();
//                while (rs.next()) {
//                    MemberDTO memberDTO = new MemberDTO();
//                    memberDTO.setName(rs.getString("name"));
//                    memberDTO.setUserId(rs.getString("user_id"));
//                    memberDTO.setUserPw(rs.getString("user_pw"));
//                    memberDTO.setAddress(rs.getString("address"));
//                    memberDTO.setPhone(rs.getString("phone"));
//                    System.out.println("이름: " + memberDTO.getName());
//                    System.out.println("아이디: " + memberDTO.getUserId());
//                    System.out.println("비밀번호: " + memberDTO.getUserPw());
//                    System.out.println("주소: " + memberDTO.getAddress());
//                    System.out.println("휴대폰 번호: " + memberDTO.getPhone());
//                }
//                rs.close();
//                pstmt.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            String menuNo;
//            do{
//                System.out.println();
//                System.out.println("--------------------------------------------------------------------------------------------------");
//                System.out.println("메뉴 : [1.수정] [9.뒤로가기]");
//                System.out.print("메뉴 선택 :");
//                menuNo = scanner.nextLine();
//
//                switch (menuNo) {
//                    case "1":
//                        System.out.println("수정하기");
//                        MemberDTO memberDTO = new MemberDTO();
//                        MyInfoUpdate(memberDTO, menuNo);
//                        break;
//
//                    case "9":
//                        LoginPassMenu();
//                        break;
//
//                    default:
//                        System.out.println("유효하지 않은 메뉴입니다.");
//                        System.out.println("원래 메뉴로 돌아갑니다.");
//                        MyInfo();
//
//                }
//            }while(!menuNo.equals("1")  &&!menuNo.equals("9"));
//        }
//
//    }
//
//    public void MyInfoUpdate(MemberDTO memberDTO, String menuNo) {
//        System.out.print("변경할 비밀번호 입력 (변경하지 않으려면 엔터):");
//        String newPassword = scanner.nextLine();
//        if (!newPassword.isEmpty()) {
//            memberDTO.setUserPw(newPassword);
//        }
//
//        System.out.print("변경할 주소 입력 (변경하지 않으려면 엔터):");
//        String newAddress = scanner.nextLine();
//        if (!newAddress.isEmpty()) {
//            memberDTO.setAddress(newAddress);
//        }
//
//        System.out.print("변경할 휴대폰 번호 입력 (변경하지 않으려면 엔터):");
//        String newPhone = scanner.nextLine();
//        if (!newPhone.isEmpty()) {
//            memberDTO.setPhone(newPhone);
//        }
//
//        if (menuNo.equals("1")) {
//            try {
//                String sql =
//                        "Update member set user_pw = COALESCE(?, user_pw), " +
//                                "address = COALESCE(?, address), phone = COALESCE(?, phone) " +
//                                "WHERE user_id=?";
//                PreparedStatement pstmt = conn.prepareStatement(sql);
//                if (!newPassword.isEmpty()) {
//                    pstmt.setString(1, memberDTO.getUserPw());
//                } else {
//                    pstmt.setNull(1, Types.VARCHAR);
//                }
//                if (!newAddress.isEmpty()) {
//                    pstmt.setString(2, memberDTO.getAddress());
//                } else {
//                    pstmt.setNull(2, Types.VARCHAR);
//                }
//                if (!newPhone.isEmpty()) {
//                    pstmt.setString(3, memberDTO.getPhone());
//                } else {
//                    pstmt.setNull(3, Types.VARCHAR);
//                }
//                pstmt.setString(4, loggedInUserID);
//                pstmt.executeUpdate();
//                pstmt.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        System.out.println("수정이 완료되었습니다.");
//        System.out.println();
//        String choice;
//        do {
//            System.out.println("메뉴 : [2. 메뉴로 돌아가기] [9.내정보 확인]");
//            System.out.print("메뉴 선택 :");
//            choice = scanner.nextLine();
//
//            switch (choice) {
//                case "2":
//                    LoginPassMenu();
//                    break;
//                case "9":
//                    MyInfo();
//                    break;
//                default:
//                    System.out.println("유효하지 않은 메뉴입니다.");
//            }
//        }while(!menuNo.equals("2")&&!menuNo.equals("9"));
//    }
//
//    //장바구니--------------------------------------------------------------------------------------------------
//    //2-5. 장바구니
//    public void Cart() {
//
//        // 장바구니 -- no, 상품명, 가격 출력
//        System.out.println();
//        System.out.println("［장바구니］");
//        System.out.println("-------------------------------------------------------------------------");
//        System.out.printf("%-20s%-20s%-20s\n", "no", "상품명","가격");
//        System.out.println("-------------------------------------------------------------------------");
//
//        // cartlist 테이블에서 가져와서 출력해줌
//        try {
//            String sql =
//                    "SELECT cart_id, item_name, price FROM cartlist a, member m Where a.user_id=m.user_id";
//
//            // SELECT cart_id, item_name, price From cartlist;
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                CartlistDTO cartlistDTO = new CartlistDTO();
//
//                cartlistDTO.setCartId(rs.getInt("cart_id"));
//                cartlistDTO.setItemName(rs.getString("item_name"));
//                cartlistDTO.setPrice(rs.getInt("price"));
//
//                System.out.printf("%-20s%-20s%-20s\n",
//                        cartlistDTO.getCartId(),
//                        cartlistDTO.getItemName(),
//                        cartlistDTO.getPrice());
//            }
//            rs.close();
//            pstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//
//        }
//
//        System.out.println("--------------------------------------------------------------------------------------------------");
//        System.out.println("메뉴 : [1.전체상품구매(구매결정)] [2.장바구니에서 삭제] [9.뒤로가기]");
//        System.out.print("메뉴 선택 :");
//        String menuNo = scanner.nextLine();
//
//        switch(menuNo){
//            case "1" :
//                //전체상품구매(구매결정)
//                System.out.println("**구매결정**");
//                Purchase_before();
//                break;
//            case "2" :
//                Cartlist_delete();
//                break;
//            case "9" :
//                LoginPassMenu();
//                break;
//
//        }
//
//
//    }
//
//    //2-5-1.구매결정(결제테이블)
//    public void Purchase_before(){
//        System.out.println();
//        System.out.println("［구매결정］");
//        System.out.println("-------------------------------------------------------------------------");
//        System.out.printf("%-20s\n", "총금액");
//        // 구매결정 테이블에 보여줄 목록 [총금액]
//        System.out.println("-------------------------------------------------------------------------");
//
//        // cartlist 테이블에서 가져와서 출력해줌
//        try {
//            String sql =
//                    "SELECT SUM(price) AS `총금액` FROM cartlist a, member m Where a.user_id=m.user_id";
//            // `총금액` 변경 시 cartlistDTO.setPrice(rs.getInt("총금액") columnLabel 도 같이 변경(필수)
//            // cartlist, member테이블에서 아이디가 동일한걸 찾고 장바구니에 담겨있는 물품 가격 총금액을 출력;
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                CartlistDTO cartlistDTO = new CartlistDTO();
//
//                cartlistDTO.setPrice(rs.getInt("총금액"));
//
//                System.out.printf("%s\n",
//                        cartlistDTO.getPrice());
//            }
//            rs.close();
//            pstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//
//        }
//
//        System.out.println("--------------------------------------------------------------------------------------------------");
//        System.out.println("구매하시겠습니까? [1. 구매(결제)] [9.뒤로가기]");
//        System.out.println("메뉴 선택 :");
//        String menuNo = scanner.nextLine();
//
//        switch (menuNo) {
//            case "1":
//                //2-5-1-1.결제 이동
//                Purchase();
//                break;
//
//            case "9":
//                Cart();
//                break;
//        }
//
//    }
//
//    //2-5-1-1. 결제(인적사항 재확인)
//    public void Purchase(){
//        System.out.println();
//        System.out.println("주문 전 주소와 전화번호를 확인해주세요");
//        System.out.println("-------------------------------------------------------------------------");
//        System.out.printf("%-20s%-20s\n", "주소", "전화번호");
//        // 주문 전 주소, 전화번호 확인용으로 인적사항 재확인
//        System.out.println("-------------------------------------------------------------------------");
//
//        // cartlist 테이블에서 가져와서 출력해줌
//        try {
//            String sql =
//                    "SELECT DISTINCT m.address, m.phone FROM cartlist a, member m WHERE a.user_id=m.user_id;";
//            // `총금액` 변경 시 cartlistDTO.setPrice(rs.getInt("총금액") columnLabel 도 같이 변경(필수)
//            // cartlist, member테이블에서 아이디가 동일한걸 찾고 장바구니에 담겨있는 물품 가격 총금액을 출력;
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                MemberDTO memberDTO = new MemberDTO();
//
//                memberDTO.setAddress(rs.getString("address"));
//                memberDTO.setPhone(rs.getString("phone"));
//                System.out.printf("%-20s%-20s\n",
//                        memberDTO.getAddress(),
//                        memberDTO.getPhone());
//            }
//            rs.close();
//            pstmt.close();
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//
//        }
//
//        System.out.println();
//        System.out.println("--------------------------------------------------------------------------------------------------");
//        System.out.println("메뉴 : [1.확인] [2.주소/전화번호 변경] [9.뒤로 가기]");
//        System.out.print("메뉴 선택 :");
//        String menuNo = scanner.nextLine();
//
//        switch (menuNo) {
//
//            case "1":
//
//                //2-5-1-1-1. 확인 이동
//                try {
//                    String sql =
//                            "INSERT INTO purchase_list(user_id, item_name, price, order_date, phone) SELECT user_id, item_name, price, now(), phone FROM cartlist WHERE user_id = 'lmcomputer'";
//
//                    PreparedStatement pstmt = conn.prepareStatement(sql);
//                    //pstmt.setString(1, loggedInUserID);
//
//                    pstmt.executeUpdate();
//                    pstmt.close();
//
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//                Purchase_successful();
//                break;
//
//            case "2":
//                //2-5-1-1-2. 주소/전화번호 변경
//                Alter_PurchaseInfo();
//                break;
//            case "9":
//                //뒤로가기
//                Purchase_before();
//                break;
//        }
//
//
//
//    }
//
//
//    //2-5-1-1-1. 확인
//    public void Purchase_successful(){
//        System.out.println();
//        System.out.println("［주문이 완료되었습니다］");
//        System.out.println(" ############## ");
//        System.out.println(" 주문 리스트 ");
//        System.out.println(" ############## ");
//
//        System.out.println("-------------------------------------------------------------------------");
//        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s\n", "no", "상품명", "가격", "주문날짜", "주소", "전화번호");
//        System.out.println("-------------------------------------------------------------------------");
//
//        // 주문 리스트 출력
//        try {
//            String sql =
//                    "SELECT p.purchase_no, p.item_name, p.price, p.order_date, m.address, p.phone\n" +
//                            "FROM purchase_list p, member m where p.user_id='?'";
//
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1,loggedInUserID);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//
//                Purchase_listDTO purchaseListDTO = new Purchase_listDTO();
//                MemberDTO memberDTO = new MemberDTO();
//
//                purchaseListDTO.setPurchaseNo(rs.getInt("purchase_no"));
//                purchaseListDTO.setItemName(rs.getString("item_name"));
//                purchaseListDTO.setPrice(rs.getInt("price"));
//                purchaseListDTO.setPurchaseDate(rs.getString("order_date"));
//                memberDTO.setAddress(rs.getString("address"));
//                purchaseListDTO.setPhone(rs.getString("phone"));
//                System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s\n",
//                        purchaseListDTO.getPurchaseNo(),
//                        purchaseListDTO.getItemName(),
//                        purchaseListDTO.getPrice(),
//                        purchaseListDTO.getPurchaseDate(),
//                        memberDTO.getAddress(),
//                        purchaseListDTO.getPhone());
//            }
//
//            rs.close();
//            pstmt.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//
//        }
//        System.out.println("회원메뉴로 이동합니다");
//        LoginPassMenu();
//    }
//
//    //2-5-1-1-2. 주소/전화번호 변경
//    public void Alter_PurchaseInfo(){
//        MemberDTO memberDto = new MemberDTO();
//        System.out.println();
//        System.out.println("주소/전화번호 변경");
//        System.out.println("-----------------------------------------------------------------------------------");
//        System.out.print("[주소 수정] :");
//        memberDto.setAddress(scanner.nextLine());
//        System.out.print("[전화번호 수정 ] : ");
//        memberDto.setPhone(scanner.nextLine());
//        System.out.println("-----------------------------------------------------------------------------------");
//
//        try {
//            String sql =
//                    " UPDATE member\n" +
//                            "SET address=? , phone = ? ";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, memberDto.getAddress());
//            pstmt.setString(2, memberDto.getPhone());
//
//            pstmt.executeUpdate();
//            pstmt.close();
//            System.out.println("수정 완료 되었습니다");
//            Purchase();
//        }
//        catch(SQLException e){
//            e.printStackTrace();
//        }
//
//    }
//
//    //2-5-2. 장바구니에서 삭제
//    public void Cartlist_delete (){
//
//        int cartId=-1;
//        while(cartId==-1){
//
//            System.out.println();
//            System.out.print("삭제할 상품번호 : ");
//            try {
//                cartId = Integer.parseInt(scanner.nextLine());
//            }
//            catch (Exception e) {
//            }
//        }
//        System.out.println("[item_name]을 삭제하시겠습니까?");
//        System.out.println("[1.확인] [9.뒤로가기]");
//        System.out.print("메뉴 선택 >");
//        String menuNo = scanner.nextLine();
//
//        switch (menuNo) {
//            //1. 확인 클릭 시
//            case "1":
//
//                try {
//                    String sql = "DELETE FROM cartlist " +
//                            "WHERE cart_id= ? ";
//
//                    PreparedStatement pstmt = conn.prepareStatement(sql);
//                    pstmt.setInt(1, cartId);
//                    ResultSet rs = pstmt.executeQuery();
//                    rs.close();
//                    pstmt.close();
//
//
//                }
//
//                catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("---------------------------------------------");
//                System.out.println("[상품이 삭제되었습니다]");
//                System.out.println("---------------------------------------------");
//                Cart();
//                break;
//
//            case "9":
//                Cart();
//                break;
//        }
//
//
//
//
//
//
//
//
//
//    }
//    //장바구니 완료
//
//
//
//
//    public static void main(String[] args) {
//        Shop1 shop1 = new Shop1(); // Connection 연결: ShopMain옮김
//        // shop1.list();  // 우선 삭제함
//        // shop1.startMenu();  // "1. 회원 로그인 | 2. 회원가입 | 3. 관리자 로그인" 메뉴였는데 MenuView로 옯김
//    }
//}
