package aut.ap;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            System.out.printf("---------------\n");
            System.out.printf("[L]ogin / [S]ign_in / [E]xit");
            choice = scanner.nextLine().trim().toLowerCase();
            switch (choice) {
                case "l" : /**/ break;
                case "s" : /**/ break;
                case "e" :
                    System.out.printf("-----Thanks for choosing Fmail ------- \n  " +
                            "   we are trying to provide safe and comfort platform for you");
                    break;
                default :
                    System.out.printf("---- unknown option ---");
            }


        } while (!choice.equals("e"));
    }
}