package user;

import java.sql.*;

public class MemberDAO {
    static Connection conn;
    public MemberDAO(Connection conn){
        this.conn = conn;
    }
    public static int loginConfirm(String userID, String userPassword) {
        String sql = "SELECT user_pw FROM member WHERE user_id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString(1);
                if (storedPassword.equals(userPassword)) {
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
    public static boolean joinConfirm(MemberDTO memberDTO) {
        String sql =
                "INSERT INTO member (name, user_id, user_pw, address, gender, height_cm, phone, birth) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberDTO.getName());
            pstmt.setString(2, memberDTO.getUserId());
            pstmt.setString(3, memberDTO.getUserPw());
            pstmt.setString(4, memberDTO.getAddress());
            pstmt.setString(5, memberDTO.getGender());
            pstmt.setInt(6, memberDTO.getHeight());
            pstmt.setString(7, memberDTO.getPhone());
            pstmt.setString(8, memberDTO.getBirth());
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("----회원가입이 완료되었습니다. 로그인 해주세요.----");
            return true;
        } catch (Exception e) {
            System.out.println("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
            return false;
        }
    }
    public boolean printMyInfo(String loggedInUserID){
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
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateMemberInfo(String loggedInUserId, MemberDTO newMemberinfo){
        try {
            String sql =
                    "Update member set user_pw = COALESCE(?, user_pw), " +
                    "address = COALESCE(?, address), phone = COALESCE(?, phone) " +
                    "WHERE user_id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (!newMemberinfo.getUserPw().isEmpty()) {
                pstmt.setString(1, newMemberinfo.getUserPw());
            } else {
                pstmt.setNull(1, Types.VARCHAR);
            }
            if (!newMemberinfo.getAddress().isEmpty()) {
                pstmt.setString(2, newMemberinfo.getAddress());
            } else {
                pstmt.setNull(2, Types.VARCHAR);
            }
            if (!newMemberinfo.getPhone().isEmpty()) {
                pstmt.setString(3, newMemberinfo.getPhone());
            } else {
                pstmt.setNull(3, Types.VARCHAR);
            }
            pstmt.setString(4, loggedInUserId);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
