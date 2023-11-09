import Util.*;

import java.io.IOException;

public class ShopMain { // main 메소드 실행하는 클래스
    static MenuView menuView;
    static UserView userView;
    static AdminView adminView;

    public static void main(String [] args) throws IOException {
        DBUtil dbUtil = new DBUtil();
        int viewMenu = 0;
        menuView = new MenuView(dbUtil.init());
        do {
            menuView.printDisabledMainMenu(); // 프로그램 시작했을 때 비활성화 메인 메뉴 출력
            viewMenu = menuView.printLoginMenu(); // 이어서 로그인/회원가입 메뉴 보이기 -> 입력받기

            switch (viewMenu) {
                case 1: // user logged in
                    userView = new UserView(dbUtil.init());
                    userView.setLoggedInUserId(menuView.getLoggedInUserId());
                    userView.userLoginPassMenu();
                    break;
                case 2: // join successful
                    break;
                case 3: // admin logged in
                    adminView = new AdminView(dbUtil.init());
                    adminView.adminLoginPassMenu();
                    break;
            }
        }while(viewMenu == 2); // 회원가입후 메인메뉴(비활)와 로그인 메뉴 다시 보이기
        ConsoleTextControl.printColorln("---------- 천재쇼핑몰을 이용해 주셔서 감사합니다:) ----------","green");
    }
}