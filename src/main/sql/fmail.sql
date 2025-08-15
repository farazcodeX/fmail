
USE pedantic_taussig;
CREATE TABLE fmails (
    id INT AUTO_INCREMENT PRIMARY KEY,
    origin_id INT NOT NULL,
    subject NVARCHAR(255) NOT NULL ,
    body NVARCHAR(255) NOT NULL ,
    date DATETIME,
    code NVARCHAR(6) NOT NULL UNIQUE ,

    FOREIGN KEY (origin_id) REFERENCES users(id)
);