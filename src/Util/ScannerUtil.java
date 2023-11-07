package Util;

import java.util.Scanner;

public class ScannerUtil { // 메
    private Scanner scanner = new Scanner(System.in);
    public String scanMenu(){
        System.out.print("메뉴 선택> ");System.out.print("\u001B[0m");

        String menuNo =  scanner.nextLine();
        return menuNo;
    }

    public void scanString(){
        //scanner.next

    }
    public void scanInt(){

    }
}
