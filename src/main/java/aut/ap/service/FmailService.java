package aut.ap.service;

import aut.ap.entity.Fmail;
import aut.ap.entity.Recipient;
import aut.ap.entity.User;
import aut.ap.frameworks.SingletonSessionFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class FmailService {
    static Scanner scanner = new Scanner(System.in);

    public static void showUnreadFmails(User user) {
        boolean noFmails = false;
        SingletonSessionFactory.get().inTransaction(session -> {
            List<Fmail> list = session.createNativeQuery(
                            "SELECT f.* " +
                                    "FROM recipients r " +
                                    "JOIN fmails f ON r.fmail_id = f.id " +
                                    "WHERE r.recipients_id = :userId " +
                                    "AND r.is_read = false " +
                                    "ORDER BY f.date DESC",
                            Fmail.class
                    )
                    .setParameter("userId", user.getId())
                    .getResultList();

            if (list.isEmpty()) {
                System.out.println("no Fmails");
                return;
            }

            System.out.println("Unread fmails: ");
            for (Fmail fmail : list) {
                System.out.println(" |(*) " + fmail.toString());
                System.out.println(" --- --- --- --- --- --- ---");
            }


        });
        while (true) {
            System.out.println("[S]ee Fmail / [B]ack");
            String choice = scanner.nextLine().toLowerCase();
            if (choice.equals("b")) {
                System.out.println("--- Fmail --- ");
                return;
            }
            if(choice.equals("s")) {
                System.out.println("Enter the Fmails code : ");
                choice = scanner.nextLine().toLowerCase().trim();
                showFmailByCode(choice, user);

            }
        }


    }



    public static void showAllFmails(User user) {
        boolean noFmails = false;
        SingletonSessionFactory.get().inTransaction(session -> {
            List<Fmail> list = session.createNativeQuery(
                            "SELECT f.* " +
                                    "FROM recipients r " +
                                    "JOIN fmails f ON r.fmail_id = f.id " +
                                    "WHERE r.recipients_id = :userId " +
                                    "ORDER BY f.date DESC",
                            Fmail.class
                    )
                    .setParameter("userId", user.getId())
                    .getResultList();

            if (list.isEmpty()) {
                System.out.println("no Fmails");
                return;
            }

            System.out.println("All fmails: ");
            for (Fmail fmail : list) {
                System.out.println(" | " + fmail.toString());
                System.out.println(" --- --- --- --- --- --- ---");
            }


        });
        while (true) {
            System.out.println("[S]ee Fmail / [B]ack");
            String choice = scanner.nextLine().toLowerCase();
            if (choice.equals("b")) {
                System.out.println("--- Fmail --- ");
                return;
            }
            if(choice.equals("s")) {
                System.out.println("Enter the Fmails code : ");
                choice = scanner.nextLine().toLowerCase().trim();
                showFmailByCode(choice, user);

            }
        }


    }





    public static void showSentFmails(User user) {
        List<Fmail> list = SingletonSessionFactory.get()
                .fromTransaction(session ->
                        session.createNativeQuery(
                                        "SELECT f.* " +
                                                "FROM fmails f " +
                                                "WHERE f.origin_id = :userId " +
                                                "ORDER BY f.date DESC", Fmail.class)
                                .setParameter("userId", user.getId())
                                .getResultList()
                );

        System.out.println("Sent Fmails:");
        if(list.isEmpty()) {
            System.out.println("no massages have sent yet . ");
            return;
        }
        for (Fmail fmail : list) {
            System.out.println(" | " + fmail.toString());
            System.out.println(" ---  ---  ---  ---");
        }

        System.out.println("[B]ack (when ever you done)");
        String choice = scanner.nextLine().toLowerCase();
        if (choice.equals("b")) {
            System.out.println("--- Fmail --- ");
            return;
        }
    }

    public static void showFmailByCode(String fmailCode, User user) {
        Fmail fmail = getFmailByCode(fmailCode, user);
        if(fmail == null) {
            System.out.println("Fmail code : " + fmailCode + " is not available for you");
            return;
        }
        System.out.println(" --- Fmail ---");
        System.out.println("Code: " + fmail.getCode());
        System.out.println("From: " + fmail.getOrigin().getFmail());
        System.out.println("Subject: " + fmail.getSubject());
        System.out.println("Date: " + fmail.getDate().toLocalDate());
        System.out.println();
        System.out.println(fmail.getBody());



        seenFmail(fmail, user);

        System.out.println("[B]ack (when ever you done)");
        String choice = scanner.nextLine().toLowerCase();
        if (choice.equals("b")) {

            System.out.println("--- Fmail --- ");
            return;
        }
    }

    public static void sendFmailBackEnd(User origin, List<User> recipients, String subject, String body) {
        SingletonSessionFactory.get().inTransaction(session -> {
            Fmail fmail = new Fmail(origin, subject, body, LocalDateTime.now());
            session.persist(fmail);
            System.out.println(" --- fmail sent successfully . code : [" + fmail.getCode() + "] ---");
            recipients.stream().forEach(user -> {
                Recipient recipient = new Recipient(user, fmail);
                session.persist(recipient);
                System.out.println(" | sent to  : " + user.getFmail() + " successfully ");
            });
        });
    }

    public static Fmail getFmailByCode(String code, User user) {
        return SingletonSessionFactory.get().fromTransaction(session ->
                session.createQuery("""
            SELECT DISTINCT f
            FROM Fmail f
            JOIN FETCH f.origin
            LEFT JOIN Recipient r ON r.fmail = f
            WHERE f.code = :code
              AND (r.recipient.id = :userId OR f.origin.id = :userId)
            """, Fmail.class)
                        .setParameter("code", code)
                        .setParameter("userId", user.getId())
                        .uniqueResult()
        );
    }

    public static void seenFmail(Fmail fmail, User user) {
        if (fmail == null || user == null) {
            System.out.println("Fmail or User is null. Cannot mark as seen.");
            return;
        }

        SingletonSessionFactory.get().fromTransaction(session -> {
            int updated = session.createNativeQuery("""
            UPDATE recipients
            SET is_read = TRUE
            WHERE fmail_id = :fmailId
            AND recipients_id = :userId
        """)
                    .setParameter("fmailId", fmail.getId())
                    .setParameter("userId", user.getId())
                    .executeUpdate();

            if (updated > 0) {
                System.out.println("Fmail marked as seen.");
            } else {

            }

            return null;
        });
    }

}
