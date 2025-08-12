package aut.ap.entity;

import jakarta.persistence.*;

@Entity
@Table (name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "fmail", nullable = false, length = 50, unique = true)
    private String fmail;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    public User() {
    }

    public User(String name, String fmail, String password) {
        this.name = name;
        this.fmail = fmail;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFmail() {
        return fmail;
    }

    public void setFmail(String fmail) {
        this.fmail = fmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fmail='" + fmail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
