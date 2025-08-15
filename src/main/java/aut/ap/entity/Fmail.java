package aut.ap.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Table(name = "fmails")
public class Fmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id")
    private User origin;

    @Column(name = "subject", nullable = false, length = 255)
    private String subject;

    @Column(name = "body", nullable = false, length = 255)
    private String body;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "code", nullable = false, length = 6, unique = true)
    private String code;

    public Fmail() {
    }

    public Fmail(User origin, String subject, String body, LocalDateTime date) {
        this.origin = origin;
        this.subject = subject;
        this.body = body;
        this.date = date;
        code = generateFmailCode();
    }

    private String generateFmailCode() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    public Integer getId() {return id;}
    public User getOrigin() {return origin;}
    public String getSubject() {return subject;}
    public String getBody() {return body;}
    public LocalDateTime getDate() {return date;}
    public String getCode() {return code;}

    public void setOrigin(User origin) {
        this.origin = origin;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {

        String sender = "unknown user";
        try {
            if (origin != null && origin.getFmail() != null) {
                sender = origin.getFmail();
            }
        } catch (org.hibernate.LazyInitializationException e) {

        }
        String safeSubject = subject != null ? subject : "(no subject)";
        String safeCode = code != null ? code : "(no code)";

        String safeDate = date != null ? date.toString() : "(no date)";

        return String.format("[%s] %s -- %s (%s)", safeCode, sender, safeSubject, safeDate);
    }

}
