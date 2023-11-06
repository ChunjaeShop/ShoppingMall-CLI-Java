package admin;

import item.ItemDTO;

import java.sql.*;
import java.util.Scanner;

public class Shop1 {
    private Scanner scanner = new Scanner(System.in);
    private Connection conn;
    private String loggedInUserID;
    ConsoleTextControl consoleTextControl = new ConsoleTextControl();


    public Shop1() {
        try {
            // JDBC Driver 등록
            Class.forName("org.mariadb.jdbc.Driver");

            // 연결하기
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://127.0.0.1:3306/team_prj",
                    "root", "12345");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printDisabledMainMenu(){
        System.out.println("--------------------------------------------");
        consoleTextControl.logMassge("\t\t\t[천재쇼핑몰 Main Menu]", "purple");
        System.out.println("- 로그인 후 메뉴를 이용할 수 있습니다.");
        System.out.println("0.상품전체보기\t\t0.상품상세조회\t\t0.주문/배송조회");
        System.out.println("0.Top10상품보기\t\t0.장바구니\t\t\t0.내정보확인");
        System.out.println("--------------------------------------------");
    }



    public int login(String userID, String userPassword) {
        String sql = "SELECT user_pw FROM member WHERE user_id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString(1);
                if (storedPassword.equals(userPassword)) {
                    loggedInUserID = userID; // Set the logged-in user ID
                    return 1; // 로그인 성공
                } else {
                    return 0; // 비밀번호 불일치
                }
            } else {
                return -1; // 아이디 없음
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -2;
        }
    }

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        String menuNo;

        do {
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------");
            System.out.println();

            System.out.println("1. 회원 로그인 | 2. 회원가입 | 3. 관리자 로그인");
            System.out.print("메뉴 선택: ");
            menuNo = scanner.nextLine();

            switch (menuNo) {
                case "1":
                    String userID;
                    boolean passwordMatched = false;


                    while (!passwordMatched) {
                        do {
                            System.out.println("=========회원로그인========");
                            System.out.print("아이디 입력: ");
                            userID = scanner.nextLine();

                            if (userID.isEmpty()) {
                                System.out.println("아이디를 입력하세요.");
                            }
                        } while (userID.isEmpty());
                        System.out.print("비밀번호 입력: ");
                        String userPassword = scanner.nextLine();
                        System.out.print("1. 로그인 9. 뒤로가기 : ");
                        String startLogin = scanner.nextLine();
                        if (startLogin.equals("1")) {
                            int loginResult = login(userID, userPassword);

                            if (loginResult == 1) {
                                System.out.println(userID + "님 환영합니다!");
                                LoginPassMenu();
                                break;
                            } else if (loginResult == 0) {
                                System.out.println("비밀번호 불일치 id랑 비밀번호를 다시 입력하세요");

                            } else if (loginResult == -1) {
                                System.out.println("아이디가 없습니다. 회원가입을 하시겠습니까?(Y/N)");
                                String retry = scanner.nextLine();
                                if (retry.equalsIgnoreCase("Y")) {
                                    create();
                                    break;
                                } else {
                                    startMenu();
                                    break;
                                }
                            } else {
                                System.out.println("데이터베이스 오류");
                            }
                        }else if(startLogin.equals("9")){
                            startMenu();

                        }

                    }break;


                case "2":
                    create();
                    break;

                case "3":
                    String adminID;
                    String adminPassword;
                    do {
                        System.out.print("관리자 아이디 입력: ");
                        adminID = scanner.nextLine();
                        if (adminID.isEmpty()) {
                            System.out.println("아이디를 입력하세요");
                        }
                    } while (adminID.isEmpty());

                    do {
                        System.out.print("관리자 비밀번호 입력: ");
                        adminPassword = scanner.nextLine();
                        if (adminPassword.isEmpty()) {
                            System.out.println("비밀번호를 입력하세요");
                        }
                    } while (adminID.isEmpty());


                    if (adminID.equals("host") && adminPassword.equals("1234")) {
                        System.out.println("관리자로 로그인하셨습니다!");
                    } else {
                        System.out.println("관리자 로그인 실패");
                        startMenu();
                    }
                    break;

                default:
                    System.out.println("유효한 메뉴를 선택하세요. (1 ,2 ,3 중 선택하세요)");

            }
        } while (!menuNo.equals("1") && !menuNo.equals("2") && !menuNo.equals("3"));


        scanner.close();
    }


    // 회원가입 메서드
    public void create() {
        // 입력 받기
        MemberDTO memberDto = new MemberDTO();
        System.out.println("----------------------- 회원가입 ----------------------");
        System.out.println("［개인정보 입력］");
        System.out.print("이름: ");
        memberDto.setName(scanner.nextLine());
        System.out.print("아이디: ");
        memberDto.setUserId(scanner.nextLine());
        System.out.print("비밀번호: ");
        memberDto.setUserPw(scanner.nextLine());
        System.out.print("주소: ");
        memberDto.setAddress(scanner.nextLine());
        System.out.print("성별: ");
        memberDto.setGender(scanner.nextLine());
        System.out.print("키: ");
        memberDto.setHeight(Integer.parseInt(scanner.nextLine()));
        System.out.print("번호: ");
        memberDto.setPhone(scanner.nextLine());
        System.out.print("생년월일: ");
        memberDto.setBirth(scanner.nextLine());

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("보조 메뉴: 1.회원가입 | 2.취소");
        System.out.print("메뉴 선택: ");
        String menuNo = scanner.nextLine();
        if (menuNo.equals("1")) {
            try {
                String sql =
                        "INSERT INTO member (name, user_id, user_pw, address, gender, height_cm, phone, birth) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, memberDto.getName());
                pstmt.setString(2, memberDto.getUserId());
                pstmt.setString(3, memberDto.getUserPw());
                pstmt.setString(4, memberDto.getAddress());
                pstmt.setString(5, memberDto.getGender());
                pstmt.setInt(6, memberDto.getHeight());
                pstmt.setString(7, memberDto.getPhone());
                pstmt.setString(8, memberDto.getBirth());
                pstmt.executeUpdate();
                pstmt.close();
                System.out.println("----------------회원가입 완료------------------");
                System.out.println("----------------로그인하세요------------------");
                startMenu();
            } catch (Exception e) {
                System.out.println("회원가입 중 오류가 발생했습니다. 다시 시도하시겠습니까? (Y/N)");
                String retry = scanner.nextLine();
                if (retry.equalsIgnoreCase("Y")) {
                    create(); // 다시 회원가입 메소드를 호출하여 재시도
                } else {
                    System.out.println("회원가입 취소하셨습니다.");
                    startMenu();
                }
            }
        } else {
            System.out.println("취소하셨습니다.");
            startMenu();
        }
    }




    public void LoginPassMenu() {
        System.out.println();
        String menuNo;

        do {
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("[1.상품전체보기] [2.상품상세조회] [3.주문/배송조회] [4.Top10상품보기]   [5.장바구니]   [9.내정보확인]");
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.print("메뉴 선택 :");
            menuNo = scanner.nextLine();

            switch (menuNo) {
                case "1":
                    AllItemList();
                    System.out.println("-----------------------------------------------------------------------------------");
                    LoginPassMenu();
                    break;
                case "2":
                    DetailItemSearch();
                    break;

                case "3":
                    Delivery();
                    break;
                case "4":
                    itemRank();
                    break;

                case "5" :
                    Basket();

                    break;

                case "9":
                    MyInfo();
                    break;
                default:
                    System.out.println("유효하지 않은 메뉴입니다.");

            }
        }while(!menuNo.equals("1")  &&!menuNo.equals("2") &&!menuNo.equals("3") &&!menuNo.equals("4") &&!menuNo.equals("4")&&!menuNo.equals("9"));
    }


    public void Basket(){
        System.out.println("장바구니");
    }
    public void AllItemList() {

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

    public void DetailItemSearch() {
        int itemId = -1; // 아이템 ID를 -1로 초기화

        while (itemId == -1) { // 아이템 ID가 유효하지 않은 경우 반복
            System.out.println();
            System.out.print("조회할 상품 ID : ");
            try {
                itemId = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }


        try {
            String sql = "SELECT item_name, size, price, remain, purchase_cnt, item_contents " +
                    "FROM item " +
                    "WHERE item_id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ItemDTO itemdao = new ItemDTO();
                itemdao.setItemName(rs.getString("item_name"));
                itemdao.setSize(rs.getString("size"));
                itemdao.setPrice(rs.getInt("price"));
                itemdao.setRemain(rs.getInt("remain"));
                itemdao.setPurchaseCnt(rs.getInt("purchase_cnt"));
                itemdao.setContent(rs.getString("item_contents"));
                System.out.println("------------------------------------------------------------------------------------------------------------------");
                System.out.printf("%-20s%-20s%-15s%-20s%-20s%-18s\n", "상품명", "사이즈", "가격", "재고량", "누적판매량", "상품설명");
                System.out.println("------------------------------------------------------------------------------------------------------------------");
                System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s\n",
                        itemdao.getItemName(),
                        itemdao.getSize(),
                        itemdao.getPrice(),
                        itemdao.getRemain(),
                        itemdao.getPurchaseCnt(),
                        itemdao.getContent());
            } else {
                System.out.println("해당 상품이 없습니다.");
                System.out.println("다시 상품을 확인해주세요");
                System.out.println();
                AllItemList();
                DetailItemSearch(); // 상품이 없는 경우 메서드를 재호출하여 다시 상품 ID를 입력받음
                return;
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String menuNo;
        System.out.println();

        do {
            System.out.println("--------------------------------------------------------------------------------------------------");
            System.out.println("메뉴 : [1.장바구니담기] [9.뒤로가기]");
            System.out.print("메뉴 선택 :");
            menuNo = scanner.nextLine();

            switch (menuNo) {
                case "1":
                    //장바구니 넣는 메서드
                    System.out.println("상품이 장바구니에 담겼습니다");
                    LoginPassMenu();
                    break;



                case "9":
                    LoginPassMenu();
                    break;
                default:
                    System.out.println("유효하지 않은 메뉴입니다.");

            }
        }while(!menuNo.equals("1")  &&!menuNo.equals("9"));
    }

    public void Delivery() {
        try {
            String sql = "SELECT item_order.order_id, item.item_name, item_order.size, item.price, " +
                    "item_order.order_date, item_order.status " +
                    "FROM item_order " +
                    "JOIN item ON item_order.item_id = item.item_id " +
                    "WHERE item_order.user_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loggedInUserID);
            ResultSet rs = pstmt.executeQuery();

            System.out.println();
            System.out.println("［주문/배송 조회］");
            System.out.println("------------------------------------------------------------------");
            System.out.printf("%-10s%-20s%-15s%-10s%-20s%-10s\n", "Order ID", "Item Name", "Size", "Price", "Order Date", "Status");
            System.out.println("------------------------------------------------------------------");

            while (rs.next()) {
                int orderID = rs.getInt("order_id");
                String itemName = rs.getString("item_name");
                String size = rs.getString("size");
                int price = rs.getInt("price");
                Timestamp orderDate = rs.getTimestamp("order_date");
                String status = rs.getString("status");

                System.out.printf("%-20s%-20s%-25s%-20s%-20s%-20s\n", orderID, itemName, size, price, orderDate, status);
            }
            String menuNo;
            do {
                System.out.println();
                System.out.println("메뉴 : [1.주문 수정] [2.주문 취소] [9.뒤로 가기]");
                System.out.print("메뉴 선택 :");
                menuNo = scanner.nextLine();


                switch (menuNo) {
                    case "1":
                        System.out.print("수정할 주문의 Order ID를 입력하세요: ");
                        int selectedOrderID = Integer.parseInt(scanner.nextLine());
                        modifyOrder(selectedOrderID);
                        break;

                    case "2":
                        // 주문 취소 화면 호출
                        System.out.println("주문 취소 기능 호출");
                        System.out.print("주문 ID를 입력하세요: ");
                        int orderID = Integer.parseInt(scanner.nextLine());
                        CancelOrderScreen(orderID);
                        Delivery();
                        break;

                    case "9":
                        LoginPassMenu();
                        break;

                    default:
                        System.out.println("유효하지 않은 메뉴입니다.");
                }

            } while (!menuNo.equals("1") && !menuNo.equals("2") && !menuNo.equals("9"));



            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyOrder(int orderID) {


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
                System.out.println("［주문 수정］");
                System.out.println("------------------------------------------------------------------");
                System.out.println("Order ID: " + orderID);
                System.out.println("Item Name: " + itemName);
                System.out.println("Address: " + address);
                System.out.println("------------------------------------------------------------------");

                String menuNo;

                do {
                    System.out.println();
                    System.out.println("메뉴 : [2.배송지 수정] [3.통합 메뉴] [9.뒤로 가기]");
                    System.out.print("메뉴 선택 :");
                    menuNo = scanner.nextLine();

                    switch (menuNo) {

                        case "2":
                            System.out.print("새로운 배송지 입력:");
                            String newAddress = scanner.nextLine();
                            updateAddress(orderID, newAddress);
                            modifyOrder(orderID);
                            break;


                        case "3":
                            System.out.println();
                            System.out.println("통합 메뉴");
                            LoginPassMenu();
                            break;

                        case "9":
                            Delivery();
                            break;

                        default:
                            System.out.println("유효하지 않은 메뉴입니다.");
                    }
                } while (!menuNo.equals("2")&&!menuNo.equals("3")&&!menuNo.equals("9"));

            } else {
                System.out.println("해당 주문이 없습니다.");
                Delivery();
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    private void CancelOrderScreen(int orderID) {
        System.out.println("[주문 취소]");
        System.out.println("------------------------------------------");

        try {
            String itemName = getItemNameByOrderID(orderID,loggedInUserID); // 주문 ID를 사용하여 상품명 가져오기

            if (itemName != null) {
                System.out.println("상품명: " + itemName);
                System.out.println("주문 ID: " + orderID);
                System.out.println("[" + orderID + "] 주문을 취소하시겠습니까? 1.확인 9.뒤로 가기");
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
                            } else {
                                System.out.println("주문 취소에 실패했습니다. 주문 ID를 확인해주세요.");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "9":
                        Delivery(); // 뒤로 가기
                        break;
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
    }

    public void updateSize(int orderID, String newSize) {
        try {
            String sql = "UPDATE item_order SET size = ? WHERE order_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newSize);
            pstmt.setInt(2, orderID);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("사이즈가 업데이트되었습니다.");
            } else {
                System.out.println("사이즈 업데이트 실패");
            }

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateAddress(int orderID, String newAddress) {
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






    // TOP10 확인
    public void itemRank() {
        System.out.println();
        System.out.println("-----------------------------［상품순위］----------------------------------");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-20s%-20s%-15s%-20s\n", "순위", "상품이름", "누적판매량", "가격");

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
                System.out.printf("%-20s%-20s%-20s%-20s\n",
                        rank,
                        itemdao.getItemName(),
                        itemdao.getPurchaseCnt(),
                        itemdao.getPrice());
                rank++; // 다음 상품의 순위 증가
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String menuNo;

        do {
            System.out.println("메뉴: 1. 상품상세조회 9. 뒤로가기");
            menuNo = scanner.nextLine();

            switch (menuNo) {
                case "1":
                    //장바구니 넣는 메서드
                    System.out.println("상품상세조회로 이동합니다.");
                    AllItemList();
                    DetailItemSearch();
                    break;

                case "9":
                    LoginPassMenu();
                    break;

                default:
                    System.out.println("유효하지 않은 메뉴입니다.");
                    itemRank();


            }
        }while(!menuNo.equals("1")  &&!menuNo.equals("9"));
    }



    // 내정보 확인 메서드
    public void MyInfo() {
        if (loggedInUserID != null) {
            System.out.println();
            System.out.println("［내 정보 확인］");
            System.out.println("현재 정보");
            System.out.println("-------------------------------------------------------------------------");

            try {
                String sql =
                        "SELECT name, user_id, user_pw, address, phone " +
                                "FROM  member " +
                                "WHERE user_id=?";

                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, loggedInUserID); // loggedInUserID를 사용하여 쿼리에 사용자 ID 설정
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    MemberDTO memberDTO = new MemberDTO();
                    memberDTO.setName(rs.getString("name"));
                    memberDTO.setUserId(rs.getString("user_id"));
                    memberDTO.setUserPw(rs.getString("user_pw"));
                    memberDTO.setAddress(rs.getString("address"));
                    memberDTO.setPhone(rs.getString("phone"));
                    System.out.println("이름: " + memberDTO.getName());
                    System.out.println("아이디: " + memberDTO.getUserId());
                    System.out.println("비밀번호: " + memberDTO.getUserPw());
                    System.out.println("주소: " + memberDTO.getAddress());
                    System.out.println("휴대폰 번호: " + memberDTO.getPhone());
                }
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String menuNo;
            do{
                System.out.println();
                System.out.println("--------------------------------------------------------------------------------------------------");
                System.out.println("메뉴 : [1.수정] [9.뒤로가기]");
                System.out.println("메뉴 선택 :");
                menuNo = scanner.nextLine();

                switch (menuNo) {
                    case "1":
                        System.out.println("수정하기");
                        MemberDTO memberDTO = new MemberDTO();
                        MyInfoUpdate(memberDTO, menuNo);
                        break;

                    case "9":
                        LoginPassMenu();
                        break;

                    default:
                        System.out.println("유효하지 않은 메뉴입니다.");
                        System.out.println("원래 메뉴로 돌아갑니다.");
                        MyInfo();

                }
            }while(!menuNo.equals("1")  &&!menuNo.equals("9"));
        }

    }

    public void MyInfoUpdate(MemberDTO memberDTO, String menuNo) {
        System.out.println("변경할 비밀번호 입력 (변경하지 않으려면 엔터):");
        String newPassword = scanner.nextLine();
        if (!newPassword.isEmpty()) {
            memberDTO.setUserPw(newPassword);
        }

        System.out.println("변경할 주소 입력 (변경하지 않으려면 엔터):");
        String newAddress = scanner.nextLine();
        if (!newAddress.isEmpty()) {
            memberDTO.setAddress(newAddress);
        }

        System.out.println("변경할 휴대폰 번호 입력 (변경하지 않으려면 엔터):");
        String newPhone = scanner.nextLine();
        if (!newPhone.isEmpty()) {
            memberDTO.setPhone(newPhone);
        }

        if (menuNo.equals("1")) {
            try {
                String sql =
                        "Update member set user_pw = COALESCE(?, user_pw), " +
                                "address = COALESCE(?, address), phone = COALESCE(?, phone) " +
                                "WHERE user_id=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                if (!newPassword.isEmpty()) {
                    pstmt.setString(1, memberDTO.getUserPw());
                } else {
                    pstmt.setNull(1, Types.VARCHAR);
                }
                if (!newAddress.isEmpty()) {
                    pstmt.setString(2, memberDTO.getAddress());
                } else {
                    pstmt.setNull(2, Types.VARCHAR);
                }
                if (!newPhone.isEmpty()) {
                    pstmt.setString(3, memberDTO.getPhone());
                } else {
                    pstmt.setNull(3, Types.VARCHAR);
                }
                pstmt.setString(4, loggedInUserID);
                pstmt.executeUpdate();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println("수정이 완료되었습니다.");
        System.out.println();
        String choice;
        do {
            System.out.println("메뉴 : [2. 메뉴로 돌아가기] [9.내정보 확인]");
            System.out.println("메뉴 선택 :");
            choice = scanner.nextLine();

            switch (choice) {
                case "2":
                    LoginPassMenu();
                    break;
                case "9":
                    MyInfo();
                    break;
                default:
                    System.out.println("유효하지 않은 메뉴입니다.");
            }
        }while(!menuNo.equals("2")  &&!menuNo.equals("9"));
    }




    public static void main(String[] args) {
        Shop1 shop1 = new Shop1();
        shop1.printDisabledMainMenu();
        shop1.startMenu();
    }
}
