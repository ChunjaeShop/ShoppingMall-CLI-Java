import Util.*;

import java.io.IOException;

public class ShopMain { // main 메소드 실행하는 클래스
    static MenuView menuView;
    static UserView userView;
    static AdminView adminView;

    public static void main(String [] args) throws IOException {
        DBUtil dbUtil = new DBUtil();
        menuView = new MenuView(dbUtil.init());
        menuView.printDisabledMainMenu(); // 프로그램 시작했을 때 비활성화 메인 메뉴 출력
        int viewMenu = menuView.printLoginMenu(); // 이어서 로그인/회원가입 메뉴 보이기 -> 입력받기

        switch (viewMenu){
            case 1: // user logged in
                userView = new UserView(dbUtil.init());
                userView.setLoggedInUserId(menuView.getLoggedInUserId());
                userView.userLoginPassMenu();
                break;
            case 3: // admin logged in
                adminView = new AdminView(dbUtil.init());
                adminView.adminLoginPassMenu();
                break;
            default:
        }
        // ----------------------------------------
    }
}