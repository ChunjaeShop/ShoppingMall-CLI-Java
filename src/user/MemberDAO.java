package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
            System.out.println("----------------회원가입 완료------------------");
            System.out.println("----------------로그인하세요------------------");
            return true;
        } catch (Exception e) {
            System.out.println("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
            return false;
        }
    }
}
