package Util;

import java.util.Scanner;

public class ScannerUtil { // 사용자가 입력받도록 하는 것
    private Scanner scanner = new Scanner(System.in);
    public String scanMenu(){
        ConsoleTextControl.printColor("메뉴 선택> ","purple");
        String menuNo =  scanner.nextLine();
        return menuNo;
    }

    public void scanString(){
        //scanner.next

    }
    public void scanInt(){

    }
}
