package Util;

import java.util.Scanner;

public class ScannerUtil { // 메
    private Scanner scanner = new Scanner(System.in);
    public String scanMenu(){
        System.out.print("메뉴 선택: ");
        String menuNo =  scanner.nextLine();
        return menuNo;
    }
    public void scanString(){
        //scanner.next

    }
    public void scanInt(){

    }
}
