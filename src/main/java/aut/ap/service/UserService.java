package aut.ap.service;

import aut.ap.entity.User;
import aut.ap.exception.UserExistException;
import aut.ap.frameworks.SingletonSessionFactory;
import aut.ap.ui.userPanel;

import java.util.Scanner;

public class UserService {
    static Scanner scanner = new Scanner(System.in);
    static String fmailEnd = "@Fmail.com";

    public static void signIn() {
        System.out.println("--- Fmail --- ");
        System.out.println("Enter name :");
        String name = scanner.nextLine();
        String fmail;
        while (true) {
            System.out.println("enter Fmail :");
            fmail = scanner.nextLine().toLowerCase().trim();
            fmail = makeFmailsCorrect(fmail);
            User tempUser = findByFmail(fmail);
            if (tempUser != null) {
                System.out.println("famil is already exist ");
            } else {
                break;
            }
        }


        String password;
        while (true) {
            System.out.println("enter Password (password must be 8 characters)");
            password = scanner.nextLine().trim();
            if (password.length() < 8) {
                System.out.println("Password is too short");
            } else {
                break;
            }
        }

        User user = new User(name, fmail, password);
        allUserPersists(user);
        System.out.println("--- Account created . for access to your account plaese login --- ");

    }

    public static void login() {
        System.out.println("--- Fmail ---");
        System.out.println("enter Fmail");
        String fmail = scanner.nextLine().toLowerCase().trim();
        fmail = makeFmailsCorrect(fmail);
        System.out.println("enter your password");
        String password = scanner.nextLine().trim();

        User user = findByFmail(fmail);
        if (user == null) {
            throw new UserExistException("Fmail not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new UserExistException("password is incorrect please try again");
        }
        System.out.println("----- welcome " + user.getName() + " -----");
        userPanel.mainPage(user);

    }

    public static User findByFmail(String fmail) {
        return SingletonSessionFactory.get()
                .fromTransaction(session ->
                        session.createNativeQuery(
                                        "SELECT * FROM users WHERE fmail = :fmail",
                                        User.class
                                )
                                .setParameter("fmail", fmail)
                                .uniqueResult()
                );
    }

    public static void allUserPersists(User user) {
        SingletonSessionFactory.get().inTransaction(session -> {
            session.persist(user);
        });
    }

    public static String makeFmailsCorrect(String fmail) {
        if (!fmail.endsWith(fmailEnd)) {
            fmail += fmailEnd;
        }
        return fmail;
    }


}
