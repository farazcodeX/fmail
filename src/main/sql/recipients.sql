USE pedantic_taussig;
CREATE TABLE recipients (
    id INT AUTO_INCREMENT PRIMARY KEY ,
    recipients_id INT NOT NULL ,
    fmail_id INT NOT NULL ,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,

    FOREIGN KEY (recipients_id) REFERENCES users(id),
    FOREIGN KEY  (fmail_id) REFERENCES fmails(id)
)