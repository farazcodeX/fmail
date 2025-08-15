# Fmail – Personal Email System with Hibernate

Fmail is a lightweight personal email system built with **Java**, **Hibernate**, and **MySQL**. It provides a simple, secure, and fully functional email experience directly from the command line.

---

## 🔹 Features

* Send and receive emails with unique codes.
* Track email read/unread status per recipient.
* Full **Hibernate ORM** support for database interaction.
* Clean, modular code with service, model, and util layers.
* Command-line interface (CLI) for user-friendly interaction.

---

## 🔹 Project Structure

```
fmail/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/farazcodeX/fmail/
│   │   │       ├── model/       # Entity classes: User, Fmail, Recipient
│   │   │       ├── service/     # Business logic, email handling
│   │   │       ├── util/        # SingletonSessionFactory, helpers
│   │   │       └── App.java     # Main CLI entry point
│   │   └── resources/
│   │       ├── hibernate.cfg.xml
│   │       └── log4j2.xml
├── pom.xml
└── README.md
```

---

## 🔹 Prerequisites

* **Java 11+**
* **MySQL 5.7+**
* **Maven** for dependency management

---

## 🔹 Database Setup

```sql
CREATE DATABASE fmail;
USE fmail;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fmail VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE fmails (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) UNIQUE NOT NULL,
    origin_id INT NOT NULL,
    subject VARCHAR(255),
    body TEXT,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (origin_id) REFERENCES users(id)
);

CREATE TABLE recipients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    recipients_id INT NOT NULL,
    fmail_id INT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (recipients_id) REFERENCES users(id),
    FOREIGN KEY (fmail_id) REFERENCES fmails(id)
);
```

---

## 🔹 Running the Project

1. **Clone the repository**

```bash
git clone https://github.com/farazcodeX/fmail.git
cd fmail
```

2. **Configure Hibernate**

* Edit `hibernate.cfg.xml` in `src/main/resources/` to match your MySQL credentials and database URL.

3. **Build and Run**

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.farazcodeX.fmail.App"
```

4. **Using the CLI**

* Register a new user or log in.
* Send emails by entering recipient emails and message body.
* View received emails and mark them as read automatically.
* Navigate easily with `[S]ee Fmail / [B]ack` commands.

---

## 🔹 Example Workflow

```
[Fmail CLI]
[S]ee Fmail / [B]ack
s
Enter the Fmails code:
abcd1234
--- Fmail ---
Code: abcd1234
Recipient(s): user2@example.com
Subject: Project Update
Date: 2025-08-15

Hi team,
Please review the latest updates on the project.

[B]ack
```

> Emails you open will automatically mark recipients as read in the database.

---

## 🔹 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature-name`)
3. Commit changes (`git commit -m 'Add feature'`)
4. Push branch (`git push origin feature-name`)
5. Open a pull request

---

## 🔹 License

This project is licensed under the **MIT License** – see the LICENSE file for details.
