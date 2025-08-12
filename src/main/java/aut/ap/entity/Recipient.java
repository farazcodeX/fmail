package aut.ap.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recipients")
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipients_id")
    private User recipient;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "fmail_id")
    private Fmail fmail;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    public Recipient() {
    }

    public Recipient(User recipientUser, Fmail fmail) {
        this.recipient = recipientUser;
        this.fmail = fmail;
        this.isRead = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getRecipientUser() {
        return recipient;
    }

    public void setRecipientUser(User recipientUser) {
        this.recipient = recipientUser;
    }

    public Fmail getFmail() {
        return fmail;
    }

    public void setFmail(Fmail fmail) {
        this.fmail = fmail;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "Recipient{" +
                "id=" + id +
                ", recipientUser=" + (recipient != null ? recipient.getId() : null) +
                ", fmail=" + (fmail != null ? fmail.getId() : null) +
                ", isRead=" + isRead +
                '}';
    }
}
