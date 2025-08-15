package aut.ap.ui;

import aut.ap.entity.Fmail;
import aut.ap.entity.User;
import aut.ap.service.FmailService;
import aut.ap.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {
    public static Scanner scanner = new Scanner(System.in);

    public static void sendFmailUi(User origin) {
        List<User> recipients = new ArrayList<>();
        recipients.clear();
        String rawFmail;
        String choice;
        boolean enough = false;
        while (true) {
            System.out.println("enter recipient fmail : (you must enter Fmails one by one)");
            rawFmail = scanner.nextLine().trim().toLowerCase();
            rawFmail = UserService.makeFmailsCorrect(rawFmail);
            User tmepRecipients = UserService.findByFmail(rawFmail);
            if (tmepRecipients == null) {
                System.out.println(" -- Fmail : " + rawFmail + " not found -- ");
                System.out.println("[T]ry again / [B]ack to menu");
                choice = scanner.nextLine().toLowerCase();
                switch (choice) {
                    case "t":
                        continue;
                    case "b":
                        return;
                    default:
                        System.out.println("unknown option");
                }
            } else {
                recipients.add(tmepRecipients);
            }
            System.out.println("[A]dd one more Fmail / [E]nough (continue sending) / [B]ack to menu");
            choice = scanner.nextLine().toLowerCase();
            switch (choice) {
                case "a":
                    continue;
                case "e":
                    enough = true;
                    break;
                case "b":
                    System.out.println("--- cancel sending Fmail ---");
                    return;
                default:
                    System.out.println("unknown option . try again");
            }
            if (enough) {
                break;
            }
        }

        System.out.println("Enter subject : ");
        String subject = scanner.nextLine();
        System.out.println("Enter body : ");
        String body = scanner.nextLine();
        FmailService.sendFmailBackEnd(origin, recipients, subject, body);


    }
    public static void forwardFmailUi(User user) {

        System.out.println("Enter the forward fmails code :");
        String code = scanner.nextLine().trim();
        Fmail forwardFmail = FmailService.getFmailByCode(code, user);
        if(forwardFmail == null) {
            System.out.println("Fmail with code : " + code + " is not sent to you");
            return;
        }

        List<User> recipients = new ArrayList<>();
        recipients.clear();
        String rawFmail;
        String choice;
        boolean enough = false;
        while (true) {
            System.out.println("enter recipient fmail : (you must enter Fmails one by one)");
            rawFmail = scanner.nextLine().trim().toLowerCase();
            User tmepRecipients = UserService.findByFmail(UserService.makeFmailsCorrect(rawFmail));
            if (tmepRecipients == null) {
                System.out.println(" -- Fmail : " + rawFmail + " not found -- ");
                System.out.println("[T]ry again / [B]ack to menu");
                choice = scanner.nextLine().toLowerCase();
                switch (choice) {
                    case "t":
                        continue;
                    case "b":
                        return;
                    default:
                        System.out.println("unknown option");
                }
            } else {
                recipients.add(tmepRecipients);
            }
            System.out.println("[A]dd one more Fmail / [E]nough (continue sending) / [B]ack to menu");
            choice = scanner.nextLine().toLowerCase();
            switch (choice) {
                case "a":
                    continue;
                case "e":
                    enough = true;
                    break;
                case "b":
                    System.out.println("--- cancel sending Fmail ---");
                    return;
                default:
                    System.out.println("unknown option . try again");
            }
            if (enough) {
                break;
            }
        }
        String forwardFmailSub;
        if(forwardFmail.getSubject().startsWith("[FW]")) {
            forwardFmailSub = forwardFmail.getSubject();
        } else {
            forwardFmailSub = "[FW] : " + forwardFmail.getSubject();
        }
        FmailService.sendFmailBackEnd(user, recipients, forwardFmailSub, forwardFmail.getBody());
        System.out.println("Fmail has bees forwarded successfully");


    }




    public static void replayFmailUi(User user) {
        System.out.println("--- Fmail ----");
        System.out.println("Enter the Fmails Code : ");
        String code = scanner.nextLine().trim();

        Fmail fmail = FmailService.getFmailByCode(code, user);
        if (fmail == null) {
            System.out.println("Fmail with code : " + code + " doent found in your Fmails");
            return;
        }
        System.out.println("Fmail code : " + code + " \n subject : " + fmail.getSubject() + " from : " + fmail.getOrigin().getFmail());
        System.out.println("Enter replay body");
        String repalyBody = scanner.nextLine();
        String replaySubject;
        if(fmail.getSubject().startsWith("RE:")) {
            replaySubject = fmail.getSubject();
        } else {
            replaySubject = "RE:" + fmail.getSubject();
        }

        List<User> recipient = List.of(fmail.getOrigin());

        FmailService.sendFmailBackEnd(user, recipient, replaySubject, repalyBody);
        System.out.println("replay sent successfully");

    }
}
