package aut.ap.ui;

import aut.ap.entity.User;
import aut.ap.exception.UserExistException;
import aut.ap.service.FmailService;
import aut.ap.service.UserService;

import java.util.Scanner;

public class userPanel {
    static Scanner scanner = new Scanner(System.in);
    public static void loginPage() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        while (true){
            System.out.println("--- Fmail --- (*) --- Fmail --- ");
            System.out.println("[L]ogin / [S]ign_in / [E]xit");
            choice = scanner.nextLine().toLowerCase().trim();
            if(choice.equals("s")) {
                UserService.signIn();
            }
            if(choice.equals("l")) {
                try {
                    UserService.login();
                } catch (UserExistException e) {
                    System.out.println(e.getMessage());
                }
            }
            if(choice.equals("e")) {
                System.out.println("---- Thanks for using Fmail -----");
                System.exit(69);
            }
        }

    }
    public static void mainPage(User user) {

        while (true) {
            System.out.println("--- Fmail --- (*) --- Fmail --- ");
            System.out.println("[S]end / [V]iew / [R]eply / [F]orward / [L]og out / [E]xit ");
            String choice = scanner.nextLine().trim().toLowerCase();
            switch (choice) {
                case "s", "send" -> UI.sendFmailUi(user);
                case "v", "view" -> viewFmails(user);
                case "r", "reply" -> UI.replayFmailUi(user);
                case "f", "forward" -> UI.forwardFmailUi(user);
                case "l", "logout" -> {
                    System.out.println("--- Log out from account : " + user.getName() + " ---");
                    loginPage();
                }
                case "e", "exit" -> {
                    System.out.println("--- Thakns for using Fmail --- ");
                    System.exit(69);
                }
                default -> System.out.println("unknown option . try again");
            }
        }
    }
    public static void viewFmails(User user) {
        System.out.println("--- Fmail --- (*) --- Fmail --- ");
        boolean back = false;
        do {
            System.out.println("[A]ll Fmails / [U]nread Fmails / [S]ent / Read by [C]ode / [B]ack ");
            String view = scanner.nextLine().trim().toLowerCase();
            switch (view) {
                case "a", "all" -> FmailService.showAllFmails(user);
                case "u", "unread" -> FmailService.showUnreadFmails(user);
                case "s", "sent" -> FmailService.showSentFmails(user);
                case "c", "code" -> {
                    System.out.println("Enter code: ");
                    String code = scanner.nextLine();
                    FmailService.showFmailByCode(code, user);
                }
                case "b", "back" -> back = true;
                default -> System.out.println("--- unknown option ---");
            }
        } while (!back);

    }


}
